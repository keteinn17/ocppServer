/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables;



import eu.chargetime.ocpp.jsonserverimplementation.db.Indexes;
import eu.chargetime.ocpp.jsonserverimplementation.db.Keys;
import eu.chargetime.ocpp.jsonserverimplementation.db.Stevedb;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.ConnectorStatusRecord;
import eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeConverter;

import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConnectorStatus extends TableImpl<ConnectorStatusRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>stevedb.connector_status</code>
     */
    public static final ConnectorStatus CONNECTOR_STATUS = new ConnectorStatus();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConnectorStatusRecord> getRecordType() {
        return ConnectorStatusRecord.class;
    }

    /**
     * The column <code>stevedb.connector_status.connector_pk</code>.
     */
    public final TableField<ConnectorStatusRecord, Integer> CONNECTOR_PK = createField(DSL.name("connector_pk"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>stevedb.connector_status.status_timestamp</code>.
     */
    public final TableField<ConnectorStatusRecord, DateTime> STATUS_TIMESTAMP = createField(DSL.name("status_timestamp"), SQLDataType.TIMESTAMP(6), this, "", new DateTimeConverter());

    /**
     * The column <code>stevedb.connector_status.status</code>.
     */
    public final TableField<ConnectorStatusRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stevedb.connector_status.error_code</code>.
     */
    public final TableField<ConnectorStatusRecord, String> ERROR_CODE = createField(DSL.name("error_code"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stevedb.connector_status.error_info</code>.
     */
    public final TableField<ConnectorStatusRecord, String> ERROR_INFO = createField(DSL.name("error_info"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stevedb.connector_status.vendor_id</code>.
     */
    public final TableField<ConnectorStatusRecord, String> VENDOR_ID = createField(DSL.name("vendor_id"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stevedb.connector_status.vendor_error_code</code>.
     */
    public final TableField<ConnectorStatusRecord, String> VENDOR_ERROR_CODE = createField(DSL.name("vendor_error_code"), SQLDataType.VARCHAR(255), this, "");

    private ConnectorStatus(Name alias, Table<ConnectorStatusRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConnectorStatus(Name alias, Table<ConnectorStatusRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>stevedb.connector_status</code> table reference
     */
    public ConnectorStatus(String alias) {
        this(DSL.name(alias), CONNECTOR_STATUS);
    }

    /**
     * Create an aliased <code>stevedb.connector_status</code> table reference
     */
    public ConnectorStatus(Name alias) {
        this(alias, CONNECTOR_STATUS);
    }

    /**
     * Create a <code>stevedb.connector_status</code> table reference
     */
    public ConnectorStatus() {
        this(DSL.name("connector_status"), null);
    }

    public <O extends Record> ConnectorStatus(Table<O> child, ForeignKey<O, ConnectorStatusRecord> key) {
        super(child, key, CONNECTOR_STATUS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Stevedb.STEVEDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.CONNECTOR_STATUS_CONNECTOR_STATUS_CPK_ST_IDX, Indexes.CONNECTOR_STATUS_FK_CS_PK_IDX);
    }

    @Override
    public List<ForeignKey<ConnectorStatusRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FK_CS_PK);
    }

    private transient Connector _connector;

    /**
     * Get the implicit join path to the <code>stevedb.connector</code> table.
     */
    public Connector connector() {
        if (_connector == null)
            _connector = new Connector(this, Keys.FK_CS_PK);

        return _connector;
    }

    @Override
    public ConnectorStatus as(String alias) {
        return new ConnectorStatus(DSL.name(alias), this);
    }

    @Override
    public ConnectorStatus as(Name alias) {
        return new ConnectorStatus(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConnectorStatus rename(String name) {
        return new ConnectorStatus(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ConnectorStatus rename(Name name) {
        return new ConnectorStatus(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, DateTime, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
