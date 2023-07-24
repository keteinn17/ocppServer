package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Striped;
import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopFailedEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.ConnectorMeterValueRecord;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.ReservationRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.*;
import eu.chargetime.ocpp.jsonserverimplementation.server.CentralSystemServiceImpl;
import eu.chargetime.ocpp.jsonserverimplementation.utils.ConnectionUtil;
import eu.chargetime.ocpp.model.core.ChargingProfilePurposeType;
import eu.chargetime.ocpp.model.core.MeterValue;
import eu.chargetime.ocpp.model.reservation.ReservationStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.*;
import static eu.chargetime.ocpp.jsonserverimplementation.db.tables.ChargeBox.CHARGE_BOX;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Service
@Component
@Slf4j
public class OcppServerRepositoryImpl implements OcppServerRepository {

    private DSLContext ctx=ConnectionUtil.getConnection();
    @Autowired private ReservationRepository reservationRepository;

    private final Striped<Lock> transactionTableLocks = Striped.lock(16);

    @Autowired
    public OcppServerRepositoryImpl(DSLContext dslContext) throws SQLException {
        ctx=dslContext;
    }
    @Override
    public void updateChargeboxHeartbeat(String chargeBoxIdentity, DateTime ts) throws SQLException {

        ctx.update(CHARGE_BOX)
                .set(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP, ts)
                .where(CHARGE_BOX.CHARGE_BOX_ID.equal(chargeBoxIdentity))
                .execute();
    }

    public Integer getChargeBoxId(String chargeBoxId) throws SQLException {
        Integer res = Integer.valueOf(String.valueOf(ctx.select(CHARGE_BOX.CHARGE_BOX_ID)
                .from(CHARGE_BOX)
                .where(CHARGE_BOX.CHARGE_BOX_ID.equal(chargeBoxId))
                .execute()));
        return res;
    }

    @Override
    public int insertTransaction(InsertTransactionParams p) {
        SelectConditionStep<Record1<Integer>> connectorPkQuery =
                DSL.select(CONNECTOR.CONNECTOR_PK)
                        .from(CONNECTOR)
                        .where(CONNECTOR.CHARGE_BOX_ID.equal(p.getChargeBoxId()))
                        .and(CONNECTOR.CONNECTOR_ID.equal(p.getConnectorId()));

        // -------------------------------------------------------------------------
        // Step 1: Insert connector and idTag, if they are new to us
        // -------------------------------------------------------------------------

        insertIgnoreConnector(ctx, p.getChargeBoxId(), p.getConnectorId());

        // it is important to insert idTag before transaction, since the transaction table references it
        boolean unknownTagInserted = insertIgnoreIdTag(ctx, p);

        // -------------------------------------------------------------------------
        // Step 2: Insert transaction if it does not exist already
        // -------------------------------------------------------------------------

        TransactionDataHolder data = insertIgnoreTransaction(p, connectorPkQuery);
        int transactionId = data.transactionId;

        if (data.existsAlready) {
            return transactionId;
        }

        if (unknownTagInserted) {
            log.warn("The transaction '{}' contains an unknown idTag '{}' which was inserted into DB "
                    + "to prevent information loss and has been blocked", transactionId, p.getIdTag());
        }

        // -------------------------------------------------------------------------
        // Step 3 for OCPP >= 1.5: A startTransaction may be related to a reservation
        // -------------------------------------------------------------------------

        if (p.isSetReservationId()) {
            reservationRepository.used(connectorPkQuery, p.getIdTag(), p.getReservationId(), transactionId);
        }

        // -------------------------------------------------------------------------
        // Step 4: Set connector status
        // -------------------------------------------------------------------------

        if (shouldInsertConnectorStatusAfterTransactionMsg(p.getChargeBoxId())) {
            insertConnectorStatus(ctx, connectorPkQuery, p.getStartTimestamp(), p.getStatusUpdate());
        }
        return transactionId;
    }

    @Override
    public void updateTransaction(UpdateTransactionParams p) {
        // -------------------------------------------------------------------------
        // Step 1: insert transaction stop data
        // -------------------------------------------------------------------------

        // JOOQ will throw an exception, if something goes wrong
        try {
            ctx.insertInto(TRANSACTION_STOP)
                    .set(TRANSACTION_STOP.TRANSACTION_PK, p.getTransactionId())
                    .set(TRANSACTION_STOP.EVENT_TIMESTAMP, p.getEventTimestamp())
                    .set(TRANSACTION_STOP.EVENT_ACTOR, p.getEventActor())
                    .set(TRANSACTION_STOP.STOP_TIMESTAMP, p.getStopTimestamp())
                    .set(TRANSACTION_STOP.STOP_VALUE, p.getStopMeterValue())
                    .set(TRANSACTION_STOP.STOP_REASON, p.getStopReason())
                    .execute();
        } catch (Exception e) {
            log.error("Exception occurred", e);
            tryInsertingFailed(p, e);
        }

        // -------------------------------------------------------------------------
        // Step 2: Set connector status back. We do this even in cases where step 1
        // fails. It probably and hopefully makes sense.
        // -------------------------------------------------------------------------

        if (shouldInsertConnectorStatusAfterTransactionMsg(p.getChargeBoxId())) {
            SelectConditionStep<Record1<Integer>> connectorPkQuery =
                    DSL.select(TRANSACTION_START.CONNECTOR_PK)
                            .from(TRANSACTION_START)
                            .where(TRANSACTION_START.TRANSACTION_PK.equal(p.getTransactionId()));

            insertConnectorStatus(ctx, connectorPkQuery, p.getStopTimestamp(), p.getStatusUpdate());
        }
    }

    @Override
    public void insertMeterValues(String chargeBoxIdentity, MeterValue[] list, int transactionId) {
        if (list== null||list.length==0) {
            return;
        }

        ctx.transaction(configuration -> {
            try {
                DSLContext ctx = DSL.using(configuration);

                // First, get connector primary key from transaction table
                int connectorPk = ctx.select(TRANSACTION_START.CONNECTOR_PK)
                        .from(TRANSACTION_START)
                        .where(TRANSACTION_START.TRANSACTION_PK.equal(transactionId))
                        .fetchOne()
                        .value1();

                batchInsertMeterValues(ctx, list, connectorPk, transactionId);
            } catch (Exception e) {
                log.error("Exception occurred", e);
            }
        });
    }

    @Override
    public void updateChargebox(UpdateChargeBoxParams p) {
        ctx.update(CHARGE_BOX)
                .set(CHARGE_BOX.OCPP_PROTOCOL, p.getOcppProtocol().getCompositeValue())
                .set(CHARGE_BOX.CHARGE_POINT_VENDOR, p.getVendor())
                .set(CHARGE_BOX.CHARGE_POINT_MODEL, p.getModel())
                .set(CHARGE_BOX.CHARGE_POINT_SERIAL_NUMBER, p.getPointSerial())
                .set(CHARGE_BOX.CHARGE_BOX_SERIAL_NUMBER, p.getBoxSerial())
                .set(CHARGE_BOX.FW_VERSION, p.getFwVersion())
                .set(CHARGE_BOX.ICCID, p.getIccid())
                .set(CHARGE_BOX.IMSI, p.getImsi())
                .set(CHARGE_BOX.METER_TYPE, p.getMeterType())
                .set(CHARGE_BOX.METER_SERIAL_NUMBER, p.getMeterSerial())
                .set(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP, p.getHeartbeatTimestamp())
                .where(CHARGE_BOX.CHARGE_BOX_ID.equal(p.getChargeBoxId()))
                .execute();
    }

    @Override
    public void insertConnectorStatus(InsertConnectorStatusParams p) {
        ctx.transaction(configuration -> {
            DSLContext ctx = DSL.using(configuration);

            // Step 1
            insertIgnoreConnector(ctx, p.getChargeBoxId(), p.getConnectorId());

            // -------------------------------------------------------------------------
            // Step 2: We store a log of connector statuses
            // -------------------------------------------------------------------------

            ctx.insertInto(CONNECTOR_STATUS)
                    .set(CONNECTOR_STATUS.CONNECTOR_PK, DSL.select(CONNECTOR.CONNECTOR_PK)
                            .from(CONNECTOR)
                            .where(CONNECTOR.CHARGE_BOX_ID.equal(p.getChargeBoxId()))
                            .and(CONNECTOR.CONNECTOR_ID.equal(p.getConnectorId()))
                    )
                    .set(CONNECTOR_STATUS.STATUS_TIMESTAMP, p.getTimestamp())
                    .set(CONNECTOR_STATUS.STATUS, p.getStatus())
                    .set(CONNECTOR_STATUS.ERROR_CODE, p.getErrorCode())
                    .set(CONNECTOR_STATUS.ERROR_INFO, p.getErrorInfo())
                    .set(CONNECTOR_STATUS.VENDOR_ID, p.getVendorId())
                    .set(CONNECTOR_STATUS.VENDOR_ERROR_CODE, p.getVendorErrorCode())
                    .execute();

            log.debug("Stored a new connector status for {}/{}.", p.getChargeBoxId(), p.getConnectorId());
        });
    }

    @Override
    public void insertMeterValues(String chargeBoxIdentity, List<MeterValue> list, int connectorId, Integer transactionId) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        ctx.transaction(configuration -> {
            try {
                DSLContext ctx = DSL.using(configuration);

                insertIgnoreConnector(ctx, chargeBoxIdentity, connectorId);
                int connectorPk = getConnectorPkFromConnector(ctx, chargeBoxIdentity, connectorId);
                MeterValue[] meterValues = list.toArray(new MeterValue[0]);
                batchInsertMeterValues(ctx, meterValues, connectorPk, transactionId);
            } catch (Exception e) {
                log.error("Exception occurred", e);
            }
        });
    }

    @Override
    public void insertReservation(Integer connectorId, ZonedDateTime expiryDate, String idTag, Integer reservationId,
                                  ReservationStatus status) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String expiryDateString = expiryDate.format(formatter);
        DateTime formattedExpiryDate = DateTime.parse(expiryDateString);


        ctx.insertInto(RESERVATION)
                .set(RESERVATION.CONNECTOR_PK, connectorId)
                .set(RESERVATION.EXPIRY_DATETIME, formattedExpiryDate)
                .set(RESERVATION.ID_TAG, idTag)
                .set(RESERVATION.RESERVATION_PK, reservationId)
                .set(RESERVATION.STATUS, status.toString())
                .execute();
        //Transaction PK
    }

    @Override
    public void deleteReservation(Integer reservationId) {
        ctx.delete(RESERVATION)
                .where(RESERVATION.RESERVATION_PK.equal(reservationId))
                .execute();
    }

    @Override
    public void clearChargingProfile(Integer connectorId, Integer chargingProfileId) {
        ctx.delete(CONNECTOR_CHARGING_PROFILE)
                .where(CONNECTOR_CHARGING_PROFILE.CHARGING_PROFILE_PK.equal(chargingProfileId))
                .and(CONNECTOR_CHARGING_PROFILE.CONNECTOR_PK.equal(connectorId))
                .execute();
    }

    private void insertConnectorStatus(DSLContext ctx,
                                       SelectConditionStep<Record1<Integer>> connectorPkQuery,
                                       DateTime timestamp,
                                       TransactionStatusUpdate statusUpdate) {
        try {
            ctx.insertInto(CONNECTOR_STATUS)
                    .set(CONNECTOR_STATUS.CONNECTOR_PK, connectorPkQuery)
                    .set(CONNECTOR_STATUS.STATUS_TIMESTAMP, timestamp)
                    .set(CONNECTOR_STATUS.STATUS, statusUpdate.getStatus())
                    .set(CONNECTOR_STATUS.ERROR_CODE, statusUpdate.getErrorCode())
                    .execute();
        } catch (Exception e) {
            log.error("Exception occurred", e);
        }
    }

    private boolean insertIgnoreIdTag(DSLContext ctx, InsertTransactionParams p) {
        String note = "This unknown idTag was used in a transaction that started @ " + p.getStartTimestamp()
                + ". It was reported @ " + DateTime.now() + ".";

        int count = ctx.insertInto(OCPP_TAG)
                .set(OCPP_TAG.ID_TAG, p.getIdTag())
                .set(OCPP_TAG.NOTE, note)
                .set(OCPP_TAG.MAX_ACTIVE_TRANSACTION_COUNT, 0)
                .onDuplicateKeyIgnore() // Important detail
                .execute();

        return count == 1;
    }

    private void insertIgnoreConnector(DSLContext ctx, String chargeBoxIdentity, int connectorId) {
        int count = ctx.insertInto(CONNECTOR,
                        CONNECTOR.CHARGE_BOX_ID, CONNECTOR.CONNECTOR_ID)
                .values(chargeBoxIdentity, connectorId)
                .onDuplicateKeyIgnore() // Important detail
                .execute();

        if (count == 1) {
            log.info("The connector {}/{} is NEW, and inserted into DB.", chargeBoxIdentity, connectorId);
        }
    }

    private boolean shouldInsertConnectorStatusAfterTransactionMsg(String chargeBoxId) {
        Record1<Integer> r = ctx.selectOne()
                .from(CHARGE_BOX)
                .where(CHARGE_BOX.CHARGE_BOX_ID.eq(chargeBoxId))
                .and(CHARGE_BOX.INSERT_CONNECTOR_STATUS_AFTER_TRANSACTION_MSG.isTrue())
                .fetchOne();

        return (r != null) && (r.value1() == 1);
    }

    private TransactionDataHolder insertIgnoreTransaction(InsertTransactionParams p,
                                                          SelectConditionStep<Record1<Integer>> connectorPkQuery) {
        Lock l = transactionTableLocks.get(p.getChargeBoxId());
        l.lock();
        try {
            Record1<Integer> r = ctx.select(TRANSACTION_START.TRANSACTION_PK)
                    .from(TRANSACTION_START)
                    .where(TRANSACTION_START.CONNECTOR_PK.eq(connectorPkQuery))
                    .and(TRANSACTION_START.ID_TAG.eq(p.getIdTag()))
                    .and(TRANSACTION_START.START_TIMESTAMP.eq(p.getStartTimestamp()))
                    .and(TRANSACTION_START.START_VALUE.eq(p.getStartMeterValue()))
                    .fetchOne();

            if (r != null) {
                return new TransactionDataHolder(true, r.value1());
            }

            Integer transactionId = ctx.insertInto(TRANSACTION_START)
                    .set(TRANSACTION_START.EVENT_TIMESTAMP, p.getEventTimestamp())
                    .set(TRANSACTION_START.CONNECTOR_PK, connectorPkQuery)
                    .set(TRANSACTION_START.ID_TAG, p.getIdTag())
                    .set(TRANSACTION_START.START_TIMESTAMP, p.getStartTimestamp())
                    .set(TRANSACTION_START.START_VALUE, p.getStartMeterValue())
                    .returning(TRANSACTION_START.TRANSACTION_PK)
                    .fetchOne()
                    .getTransactionPk();

            // Actually unnecessary, because JOOQ will throw an exception, if something goes wrong
            if (transactionId == null) {
                throw new Exception("Failed to INSERT transaction into database");
            }

            return new TransactionDataHolder(false, transactionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            l.unlock();
        }
    }

    private void tryInsertingFailed(UpdateTransactionParams p, Exception e) {
        try {
            ctx.insertInto(TRANSACTION_STOP_FAILED)
                    .set(TRANSACTION_STOP_FAILED.TRANSACTION_PK, p.getTransactionId())
                    .set(TRANSACTION_STOP_FAILED.EVENT_TIMESTAMP, p.getEventTimestamp())
                    .set(TRANSACTION_STOP_FAILED.EVENT_ACTOR, mapActor(p.getEventActor()))
                    .set(TRANSACTION_STOP_FAILED.STOP_TIMESTAMP, p.getStopTimestamp())
                    .set(TRANSACTION_STOP_FAILED.STOP_VALUE, p.getStopMeterValue())
                    .set(TRANSACTION_STOP_FAILED.STOP_REASON, p.getStopReason())
                    .set(TRANSACTION_STOP_FAILED.FAIL_REASON, Throwables.getStackTraceAsString(e))
                    .execute();
        } catch (Exception ex) {
            // This is where we give up and just log
            log.error("Exception occurred", e);
        }
    }

    private static TransactionStopFailedEventActor mapActor(TransactionStopEventActor a) {
        for (TransactionStopFailedEventActor b : TransactionStopFailedEventActor.values()) {
            if (b.getLiteral().equalsIgnoreCase(a.getLiteral())) {
                return b;
            }
        }
        // if unknown, do not throw exceptions. just insert manual.
        return TransactionStopFailedEventActor.manual;
    }

    private void batchInsertMeterValues(DSLContext ctx, MeterValue[] list, int connectorPk, Integer transactionId) {
        List<ConnectorMeterValueRecord> batch =
                Arrays.asList(list).stream()
                        .flatMap(t -> Arrays.stream(t.getSampledValue())
                                        .map(k -> ctx.newRecord(CONNECTOR_METER_VALUE)
                                                .setConnectorPk(connectorPk)
                                                .setTransactionPk(transactionId)
                                                .setValueTimestamp(CentralSystemServiceImpl.convert(t.getTimestamp()))
                                                .setValue(k.getValue()))
                                                ).collect(Collectors.toList());
        ctx.batchInsert(batch).execute();
    }

    private int getConnectorPkFromConnector(DSLContext ctx, String chargeBoxIdentity, int connectorId) {
        return ctx.select(CONNECTOR.CONNECTOR_PK)
                .from(CONNECTOR)
                .where(CONNECTOR.CHARGE_BOX_ID.equal(chargeBoxIdentity))
                .and(CONNECTOR.CONNECTOR_ID.equal(connectorId))
                .fetchOne()
                .value1();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class TransactionDataHolder {
        final boolean existsAlready;
        final int transactionId;
    }
}
