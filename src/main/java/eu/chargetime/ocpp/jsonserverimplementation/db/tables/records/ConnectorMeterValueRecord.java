/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;



import eu.chargetime.ocpp.jsonserverimplementation.db.tables.ConnectorMeterValue;
import org.joda.time.DateTime;
import org.jooq.Field;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConnectorMeterValueRecord extends TableRecordImpl<ConnectorMeterValueRecord> implements Record10<Integer, Integer, DateTime, String, String, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.connector_meter_value.connector_pk</code>.
     */
    public ConnectorMeterValueRecord setConnectorPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.connector_pk</code>.
     */
    public Integer getConnectorPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.transaction_pk</code>.
     */
    public ConnectorMeterValueRecord setTransactionPk(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.transaction_pk</code>.
     */
    public Integer getTransactionPk() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.value_timestamp</code>.
     */
    public ConnectorMeterValueRecord setValueTimestamp(DateTime value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.value_timestamp</code>.
     */
    public DateTime getValueTimestamp() {
        return (DateTime) get(2);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.value</code>.
     */
    public ConnectorMeterValueRecord setValue(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.value</code>.
     */
    public String getValue() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.reading_context</code>.
     */
    public ConnectorMeterValueRecord setReadingContext(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.reading_context</code>.
     */
    public String getReadingContext() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.format</code>.
     */
    public ConnectorMeterValueRecord setFormat(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.format</code>.
     */
    public String getFormat() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.measurand</code>.
     */
    public ConnectorMeterValueRecord setMeasurand(String value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.measurand</code>.
     */
    public String getMeasurand() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.location</code>.
     */
    public ConnectorMeterValueRecord setLocation(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.location</code>.
     */
    public String getLocation() {
        return (String) get(7);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.unit</code>.
     */
    public ConnectorMeterValueRecord setUnit(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.unit</code>.
     */
    public String getUnit() {
        return (String) get(8);
    }

    /**
     * Setter for <code>stevedb.connector_meter_value.phase</code>.
     */
    public ConnectorMeterValueRecord setPhase(String value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector_meter_value.phase</code>.
     */
    public String getPhase() {
        return (String) get(9);
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row10<Integer, Integer, DateTime, String, String, String, String, String, String, String> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    @Override
    public Row10<Integer, Integer, DateTime, String, String, String, String, String, String, String> valuesRow() {
        return (Row10) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.CONNECTOR_PK;
    }

    @Override
    public Field<Integer> field2() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.TRANSACTION_PK;
    }

    @Override
    public Field<DateTime> field3() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.VALUE_TIMESTAMP;
    }

    @Override
    public Field<String> field4() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.VALUE;
    }

    @Override
    public Field<String> field5() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.READING_CONTEXT;
    }

    @Override
    public Field<String> field6() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.FORMAT;
    }

    @Override
    public Field<String> field7() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.MEASURAND;
    }

    @Override
    public Field<String> field8() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.LOCATION;
    }

    @Override
    public Field<String> field9() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.UNIT;
    }

    @Override
    public Field<String> field10() {
        return ConnectorMeterValue.CONNECTOR_METER_VALUE.PHASE;
    }

    @Override
    public Integer component1() {
        return getConnectorPk();
    }

    @Override
    public Integer component2() {
        return getTransactionPk();
    }

    @Override
    public DateTime component3() {
        return getValueTimestamp();
    }

    @Override
    public String component4() {
        return getValue();
    }

    @Override
    public String component5() {
        return getReadingContext();
    }

    @Override
    public String component6() {
        return getFormat();
    }

    @Override
    public String component7() {
        return getMeasurand();
    }

    @Override
    public String component8() {
        return getLocation();
    }

    @Override
    public String component9() {
        return getUnit();
    }

    @Override
    public String component10() {
        return getPhase();
    }

    @Override
    public Integer value1() {
        return getConnectorPk();
    }

    @Override
    public Integer value2() {
        return getTransactionPk();
    }

    @Override
    public DateTime value3() {
        return getValueTimestamp();
    }

    @Override
    public String value4() {
        return getValue();
    }

    @Override
    public String value5() {
        return getReadingContext();
    }

    @Override
    public String value6() {
        return getFormat();
    }

    @Override
    public String value7() {
        return getMeasurand();
    }

    @Override
    public String value8() {
        return getLocation();
    }

    @Override
    public String value9() {
        return getUnit();
    }

    @Override
    public String value10() {
        return getPhase();
    }

    @Override
    public ConnectorMeterValueRecord value1(Integer value) {
        setConnectorPk(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value2(Integer value) {
        setTransactionPk(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value3(DateTime value) {
        setValueTimestamp(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value4(String value) {
        setValue(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value5(String value) {
        setReadingContext(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value6(String value) {
        setFormat(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value7(String value) {
        setMeasurand(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value8(String value) {
        setLocation(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value9(String value) {
        setUnit(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord value10(String value) {
        setPhase(value);
        return this;
    }

    @Override
    public ConnectorMeterValueRecord values(Integer value1, Integer value2, DateTime value3, String value4, String value5, String value6, String value7, String value8, String value9, String value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConnectorMeterValueRecord
     */
    public ConnectorMeterValueRecord() {
        super(ConnectorMeterValue.CONNECTOR_METER_VALUE);
    }

    /**
     * Create a detached, initialised ConnectorMeterValueRecord
     */
    public ConnectorMeterValueRecord(Integer connectorPk, Integer transactionPk, DateTime valueTimestamp, String value, String readingContext, String format, String measurand, String location, String unit, String phase) {
        super(ConnectorMeterValue.CONNECTOR_METER_VALUE);

        setConnectorPk(connectorPk);
        setTransactionPk(transactionPk);
        setValueTimestamp(valueTimestamp);
        setValue(value);
        setReadingContext(readingContext);
        setFormat(format);
        setMeasurand(measurand);
        setLocation(location);
        setUnit(unit);
        setPhase(phase);
    }
}
