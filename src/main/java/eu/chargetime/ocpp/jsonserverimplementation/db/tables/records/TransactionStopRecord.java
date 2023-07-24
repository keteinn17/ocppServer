/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;



import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.TransactionStop;
import org.joda.time.DateTime;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TransactionStopRecord extends UpdatableRecordImpl<TransactionStopRecord> implements Record6<Integer, DateTime, TransactionStopEventActor, DateTime, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.transaction_stop.transaction_pk</code>.
     */
    public TransactionStopRecord setTransactionPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.transaction_pk</code>.
     */
    public Integer getTransactionPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.transaction_stop.event_timestamp</code>.
     */
    public TransactionStopRecord setEventTimestamp(DateTime value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.event_timestamp</code>.
     */
    public DateTime getEventTimestamp() {
        return (DateTime) get(1);
    }

    /**
     * Setter for <code>stevedb.transaction_stop.event_actor</code>.
     */
    public TransactionStopRecord setEventActor(TransactionStopEventActor value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.event_actor</code>.
     */
    public TransactionStopEventActor getEventActor() {
        return (TransactionStopEventActor) get(2);
    }

    /**
     * Setter for <code>stevedb.transaction_stop.stop_timestamp</code>.
     */
    public TransactionStopRecord setStopTimestamp(DateTime value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.stop_timestamp</code>.
     */
    public DateTime getStopTimestamp() {
        return (DateTime) get(3);
    }

    /**
     * Setter for <code>stevedb.transaction_stop.stop_value</code>.
     */
    public TransactionStopRecord setStopValue(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.stop_value</code>.
     */
    public String getStopValue() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stevedb.transaction_stop.stop_reason</code>.
     */
    public TransactionStopRecord setStopReason(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.transaction_stop.stop_reason</code>.
     */
    public String getStopReason() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, DateTime> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, DateTime, TransactionStopEventActor, DateTime, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, DateTime, TransactionStopEventActor, DateTime, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return TransactionStop.TRANSACTION_STOP.TRANSACTION_PK;
    }

    @Override
    public Field<DateTime> field2() {
        return TransactionStop.TRANSACTION_STOP.EVENT_TIMESTAMP;
    }

    @Override
    public Field<TransactionStopEventActor> field3() {
        return TransactionStop.TRANSACTION_STOP.EVENT_ACTOR;
    }

    @Override
    public Field<DateTime> field4() {
        return TransactionStop.TRANSACTION_STOP.STOP_TIMESTAMP;
    }

    @Override
    public Field<String> field5() {
        return TransactionStop.TRANSACTION_STOP.STOP_VALUE;
    }

    @Override
    public Field<String> field6() {
        return TransactionStop.TRANSACTION_STOP.STOP_REASON;
    }

    @Override
    public Integer component1() {
        return getTransactionPk();
    }

    @Override
    public DateTime component2() {
        return getEventTimestamp();
    }

    @Override
    public TransactionStopEventActor component3() {
        return getEventActor();
    }

    @Override
    public DateTime component4() {
        return getStopTimestamp();
    }

    @Override
    public String component5() {
        return getStopValue();
    }

    @Override
    public String component6() {
        return getStopReason();
    }

    @Override
    public Integer value1() {
        return getTransactionPk();
    }

    @Override
    public DateTime value2() {
        return getEventTimestamp();
    }

    @Override
    public TransactionStopEventActor value3() {
        return getEventActor();
    }

    @Override
    public DateTime value4() {
        return getStopTimestamp();
    }

    @Override
    public String value5() {
        return getStopValue();
    }

    @Override
    public String value6() {
        return getStopReason();
    }

    @Override
    public TransactionStopRecord value1(Integer value) {
        setTransactionPk(value);
        return this;
    }

    @Override
    public TransactionStopRecord value2(DateTime value) {
        setEventTimestamp(value);
        return this;
    }

    @Override
    public TransactionStopRecord value3(TransactionStopEventActor value) {
        setEventActor(value);
        return this;
    }

    @Override
    public TransactionStopRecord value4(DateTime value) {
        setStopTimestamp(value);
        return this;
    }

    @Override
    public TransactionStopRecord value5(String value) {
        setStopValue(value);
        return this;
    }

    @Override
    public TransactionStopRecord value6(String value) {
        setStopReason(value);
        return this;
    }

    @Override
    public TransactionStopRecord values(Integer value1, DateTime value2, TransactionStopEventActor value3, DateTime value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TransactionStopRecord
     */
    public TransactionStopRecord() {
        super(TransactionStop.TRANSACTION_STOP);
    }

    /**
     * Create a detached, initialised TransactionStopRecord
     */
    public TransactionStopRecord(Integer transactionPk, DateTime eventTimestamp, TransactionStopEventActor eventActor, DateTime stopTimestamp, String stopValue, String stopReason) {
        super(TransactionStop.TRANSACTION_STOP);

        setTransactionPk(transactionPk);
        setEventTimestamp(eventTimestamp);
        setEventActor(eventActor);
        setStopTimestamp(stopTimestamp);
        setStopValue(stopValue);
        setStopReason(stopReason);
    }
}
