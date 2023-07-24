package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.repository.TransactionRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.Transaction;
import eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeUtils;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.TransactionQueryForm;
import org.joda.time.DateTime;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.*;
import static eu.chargetime.ocpp.jsonserverimplementation.utils.CustomDSL.date;


/**
 * @author ket_ein17
 */
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final DSLContext ctx;
    @Autowired
    public TransactionRepositoryImpl(DSLContext ctx) {
        this.ctx = ctx;
    }
    @Override
    public List<Transaction> getTransactions(TransactionQueryForm form) throws Exception {
        return getInternal(form).fetch()
                .map(new TransactionMapper());
    }

    @SuppressWarnings("unchecked")
    private
    SelectQuery<Record12<Integer, String, Integer, String, DateTime, String, DateTime, String, String, Integer, Integer, TransactionStopEventActor>>
    getInternal(TransactionQueryForm form) throws Exception {

        SelectQuery selectQuery = ctx.selectQuery();
        selectQuery.addFrom(TRANSACTION);
        selectQuery.addJoin(CONNECTOR, TRANSACTION.CONNECTOR_PK.eq(CONNECTOR.CONNECTOR_PK));
        selectQuery.addJoin(CHARGE_BOX, CHARGE_BOX.CHARGE_BOX_ID.eq(CONNECTOR.CHARGE_BOX_ID));
        selectQuery.addJoin(OCPP_TAG, OCPP_TAG.ID_TAG.eq(TRANSACTION.ID_TAG));
        selectQuery.addSelect(
                TRANSACTION.TRANSACTION_PK,
                CONNECTOR.CHARGE_BOX_ID,
                CONNECTOR.CONNECTOR_ID,
                TRANSACTION.ID_TAG,
                TRANSACTION.START_TIMESTAMP,
                TRANSACTION.START_VALUE,
                TRANSACTION.STOP_TIMESTAMP,
                TRANSACTION.STOP_VALUE,
                TRANSACTION.STOP_REASON,
                CHARGE_BOX.CHARGE_BOX_PK,
                OCPP_TAG.OCPP_TAG_PK,
                TRANSACTION.STOP_EVENT_ACTOR
        );

        return addConditions(selectQuery, form);
    }

    @SuppressWarnings("unchecked")
    private SelectQuery addConditions(SelectQuery selectQuery, TransactionQueryForm form) throws Exception {
        if (form.isTransactionPkSet()) {
            selectQuery.addConditions(TRANSACTION.TRANSACTION_PK.eq(form.getTransactionPk()));
        }

        if (form.isChargeBoxIdSet()) {
            selectQuery.addConditions(CONNECTOR.CHARGE_BOX_ID.eq(form.getChargeBoxId()));
        }

        if (form.isOcppIdTagSet()) {
            selectQuery.addConditions(TRANSACTION.ID_TAG.eq(form.getOcppIdTag()));
        }

        if (form.getType() == TransactionQueryForm.QueryType.ACTIVE) {
            selectQuery.addConditions(TRANSACTION.STOP_TIMESTAMP.isNull());
        }

        processType(selectQuery, form);

        // Default order
        selectQuery.addOrderBy(TRANSACTION.TRANSACTION_PK.desc());

        return selectQuery;
    }

    private void processType(SelectQuery selectQuery, TransactionQueryForm form) throws Exception {
        switch (form.getPeriodType()) {
            case TODAY:
                selectQuery.addConditions(
                        date(TRANSACTION.START_TIMESTAMP).eq(date(DateTime.now()))
                );
                break;

            case LAST_10:
            case LAST_30:
            case LAST_90:
                DateTime now = DateTime.now();
                selectQuery.addConditions(
                        date(TRANSACTION.START_TIMESTAMP).between(
                                date(now.minusDays(form.getPeriodType().getInterval())),
                                date(now)
                        )
                );
                break;

            case ALL:
                break;

            case FROM_TO:
                selectQuery.addConditions(
                        TRANSACTION.START_TIMESTAMP.between(form.getFrom().toDateTime(), form.getTo().toDateTime())
                );
                break;

            default:
                throw new Exception("Unknown enum type");
        }
    }


    private static class TransactionMapper implements RecordMapper<Record12<Integer, String, Integer, String, DateTime, String, DateTime, String, String, Integer, Integer, TransactionStopEventActor>, Transaction> {
        @Override
        public Transaction map(Record12<Integer, String, Integer, String, DateTime, String, DateTime, String, String, Integer, Integer, TransactionStopEventActor> r) {
            return Transaction.builder()
                    .id(r.value1())
                    .chargeBoxId(r.value2())
                    .connectorId(r.value3())
                    .ocppIdTag(r.value4())
                    .startTimestamp(DateTimeUtils.jodaToZoned(r.value5()).toString())
                    .startTimestampFormatted(DateTimeUtils.humanize(r.value5()))
                    .startValue(r.value6())
                    .stopTimestamp(r.value7())
                    .stopTimestampFormatted(DateTimeUtils.humanize(r.value7()))
                    .stopValue(r.value8())
                    .stopReason(r.value9())
                    .chargeBoxPk(r.value10())
                    .ocppTagPk(r.value11())
                    .stopEventActor(r.value12())
                    .build();
        }
    }
}
