package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppProtocol;
import eu.chargetime.ocpp.jsonserverimplementation.repository.ChargePointRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ChargePoint;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;
import eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeUtils;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ChargePointQueryForm;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ConnectorStatusForm;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.RegistrationStatus;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.*;
import static eu.chargetime.ocpp.jsonserverimplementation.utils.CustomDSL.date;
import static eu.chargetime.ocpp.jsonserverimplementation.utils.CustomDSL.includes;

/**
 * @author ket_ein17
 */
@Slf4j
@Repository
public class ChargePointRepositoryImpl implements ChargePointRepository {
    private final DSLContext ctx;

    @Autowired
    public ChargePointRepositoryImpl(DSLContext ctx) {
        this.ctx = ctx;
    }


    @Override
    public List<ChargePoint.Overview> getOverview(ChargePointQueryForm form) throws Exception {
        return getOverviewInternal(form)
                .map(r -> ChargePoint.Overview.builder()
                        .chargeBoxPk(r.value1())
                        .chargeBoxId(r.value2())
                        .description(r.value3())
                        .ocppProtocol(r.value4())
                        .lastHeartbeatTimestampDT(r.value5() == null ? null :r.value5().toString())
                        .lastHeartbeatTimestamp(DateTimeUtils.humanize(r.value5()))
                        .build()
                );
    }

    private Result<Record5<Integer, String, String, String, DateTime>> getOverviewInternal(ChargePointQueryForm form) throws Exception {
        SelectQuery selectQuery = ctx.selectQuery();
        selectQuery.addFrom(CHARGE_BOX);
        selectQuery.addSelect(
                CHARGE_BOX.CHARGE_BOX_PK,
                CHARGE_BOX.CHARGE_BOX_ID,
                CHARGE_BOX.DESCRIPTION,
                CHARGE_BOX.OCPP_PROTOCOL,
                CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP
        );

        if (form.isSetOcppVersion()) {

            // http://dev.mysql.com/doc/refman/5.7/en/pattern-matching.html
            selectQuery.addConditions(CHARGE_BOX.OCPP_PROTOCOL.like(form.getOcppVersion().getValue() + "_"));
        }

        if (form.isSetDescription()) {
            selectQuery.addConditions(includes(CHARGE_BOX.DESCRIPTION, form.getDescription()));
        }

        if (form.isSetChargeBoxId()) {
            selectQuery.addConditions(includes(CHARGE_BOX.CHARGE_BOX_ID, form.getChargeBoxId()));
        }

        switch (form.getHeartbeatPeriod()) {
            case ALL:
                break;

            case TODAY:
                selectQuery.addConditions(
                        date(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP).eq(date(DateTime.now()))
                );
                break;

            case YESTERDAY:
                selectQuery.addConditions(
                        date(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP).eq(date(DateTime.now().minusDays(1)))
                );
                break;

            case EARLIER:
                selectQuery.addConditions(
                        date(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP).lessThan(date(DateTime.now().minusDays(1)))
                );
                break;

            default:
                throw new Exception("Unknown enum type");
        }

        // Default order
        selectQuery.addOrderBy(CHARGE_BOX.CHARGE_BOX_PK.asc());

        return selectQuery.fetch();
    }

    @Override
    public Map<String, Integer> getChargeBoxIdPkPair(List<String> chargeBoxIdList) {
        return ctx.select(CHARGE_BOX.CHARGE_BOX_ID, CHARGE_BOX.CHARGE_BOX_PK)
                .from(CHARGE_BOX)
                .where(CHARGE_BOX.CHARGE_BOX_ID.in(chargeBoxIdList))
                .fetchMap(CHARGE_BOX.CHARGE_BOX_ID, CHARGE_BOX.CHARGE_BOX_PK);
    }
    @Override
    public List<String> getChargeBoxIds() {
        return ctx.select(CHARGE_BOX.CHARGE_BOX_ID)
                .from(CHARGE_BOX)
                .fetch(CHARGE_BOX.CHARGE_BOX_ID);
    }

    @Override
    public List<ConnectorStatus> getChargePointConnectorStatus(ConnectorStatusForm form) {
        // find out the latest timestamp for each connector
        Field<Integer> t1Pk = CONNECTOR_STATUS.CONNECTOR_PK.as("t1_pk");
        Field<DateTime> t1TsMax = DSL.max(CONNECTOR_STATUS.STATUS_TIMESTAMP).as("t1_ts_max");
        Table<?> t1 = ctx.select(t1Pk, t1TsMax)
                .from(CONNECTOR_STATUS)
                .groupBy(CONNECTOR_STATUS.CONNECTOR_PK)
                .asTable("t1");

        // get the status table with latest timestamps only
        Field<Integer> t2Pk = CONNECTOR_STATUS.CONNECTOR_PK.as("t2_pk");
        Field<DateTime> t2Ts = CONNECTOR_STATUS.STATUS_TIMESTAMP.as("t2_ts");
        Field<String> t2Status = CONNECTOR_STATUS.STATUS.as("t2_status");
        Field<String> t2Error = CONNECTOR_STATUS.ERROR_CODE.as("t2_error");
        Table<?> t2 = ctx.selectDistinct(t2Pk, t2Ts, t2Status, t2Error)
                .from(CONNECTOR_STATUS)
                .join(t1)
                .on(CONNECTOR_STATUS.CONNECTOR_PK.equal(t1.field(t1Pk)))
                .and(CONNECTOR_STATUS.STATUS_TIMESTAMP.equal(t1.field(t1TsMax)))
                .asTable("t2");

        // https://github.com/steve-community/steve/issues/691
        Condition chargeBoxCondition = CHARGE_BOX.REGISTRATION_STATUS.eq(RegistrationStatus.ACCEPTED.value());

        if (form != null && form.getChargeBoxId() != null) {
            chargeBoxCondition = chargeBoxCondition.and(CHARGE_BOX.CHARGE_BOX_ID.eq(form.getChargeBoxId()));
        }

        final Condition statusCondition;
        if (form == null || form.getStatus() == null) {
            statusCondition = DSL.noCondition();
        } else {
            statusCondition = t2.field(t2Status).eq(form.getStatus());
        }

        return ctx.select(
                        CHARGE_BOX.CHARGE_BOX_PK,
                        CONNECTOR.CHARGE_BOX_ID,
                        CONNECTOR.CONNECTOR_ID,
                        t2.field(t2Ts),
                        t2.field(t2Status),
                        t2.field(t2Error),
                        CHARGE_BOX.OCPP_PROTOCOL)
                .from(t2)
                .join(CONNECTOR)
                .on(CONNECTOR.CONNECTOR_PK.eq(t2.field(t2Pk)))
                .join(CHARGE_BOX)
                .on(CHARGE_BOX.CHARGE_BOX_ID.eq(CONNECTOR.CHARGE_BOX_ID))
                .where(chargeBoxCondition, statusCondition)
                .orderBy(t2.field(t2Ts).desc())
                .fetch()
                .map(r -> ConnectorStatus.builder()
                        .chargeBoxPk(r.value1())
                        .chargeBoxId(r.value2())
                        .connectorId(r.value3())
                        .timeStamp(DateTimeUtils.humanize(r.value4()))
                        .statusTimestamp(r.value4())
                        .status(r.value5())
                        .errorCode(r.value6())
                        .ocppProtocol(r.value7() == null ? null : OcppProtocol.fromCompositeValue(r.value7()))
                        .build()
                );
    }
}
