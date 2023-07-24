/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;



import eu.chargetime.ocpp.jsonserverimplementation.db.tables.Connector;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConnectorRecord extends UpdatableRecordImpl<ConnectorRecord> implements Record3<Integer, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.connector.connector_pk</code>.
     */
    public ConnectorRecord setConnectorPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector.connector_pk</code>.
     */
    public Integer getConnectorPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.connector.charge_box_id</code>.
     */
    public ConnectorRecord setChargeBoxId(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector.charge_box_id</code>.
     */
    public String getChargeBoxId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stevedb.connector.connector_id</code>.
     */
    public ConnectorRecord setConnectorId(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.connector.connector_id</code>.
     */
    public Integer getConnectorId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Integer> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, String, Integer> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Connector.CONNECTOR.CONNECTOR_PK;
    }

    @Override
    public Field<String> field2() {
        return Connector.CONNECTOR.CHARGE_BOX_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Connector.CONNECTOR.CONNECTOR_ID;
    }

    @Override
    public Integer component1() {
        return getConnectorPk();
    }

    @Override
    public String component2() {
        return getChargeBoxId();
    }

    @Override
    public Integer component3() {
        return getConnectorId();
    }

    @Override
    public Integer value1() {
        return getConnectorPk();
    }

    @Override
    public String value2() {
        return getChargeBoxId();
    }

    @Override
    public Integer value3() {
        return getConnectorId();
    }

    @Override
    public ConnectorRecord value1(Integer value) {
        setConnectorPk(value);
        return this;
    }

    @Override
    public ConnectorRecord value2(String value) {
        setChargeBoxId(value);
        return this;
    }

    @Override
    public ConnectorRecord value3(Integer value) {
        setConnectorId(value);
        return this;
    }

    @Override
    public ConnectorRecord values(Integer value1, String value2, Integer value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ConnectorRecord
     */
    public ConnectorRecord() {
        super(Connector.CONNECTOR);
    }

    /**
     * Create a detached, initialised ConnectorRecord
     */
    public ConnectorRecord(Integer connectorPk, String chargeBoxId, Integer connectorId) {
        super(Connector.CONNECTOR);

        setConnectorPk(connectorPk);
        setChargeBoxId(chargeBoxId);
        setConnectorId(connectorId);
    }
}
