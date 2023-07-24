/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables;



import eu.chargetime.ocpp.jsonserverimplementation.db.Stevedb;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.OcppTagActivityRecord;
import eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeConverter;

import org.joda.time.DateTime;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OcppTagActivity extends TableImpl<OcppTagActivityRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>stevedb.ocpp_tag_activity</code>
     */
    public static final OcppTagActivity OCPP_TAG_ACTIVITY = new OcppTagActivity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OcppTagActivityRecord> getRecordType() {
        return OcppTagActivityRecord.class;
    }

    /**
     * The column <code>stevedb.ocpp_tag_activity.ocpp_tag_pk</code>.
     */
    public final TableField<OcppTagActivityRecord, Integer> OCPP_TAG_PK = createField(DSL.name("ocpp_tag_pk"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("0", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.id_tag</code>.
     */
    public final TableField<OcppTagActivityRecord, String> ID_TAG = createField(DSL.name("id_tag"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.parent_id_tag</code>.
     */
    public final TableField<OcppTagActivityRecord, String> PARENT_ID_TAG = createField(DSL.name("parent_id_tag"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.expiry_date</code>.
     */
    public final TableField<OcppTagActivityRecord, DateTime> EXPIRY_DATE = createField(DSL.name("expiry_date"), SQLDataType.TIMESTAMP(6), this, "", new DateTimeConverter());

    /**
     * The column
     * <code>stevedb.ocpp_tag_activity.max_active_transaction_count</code>.
     */
    public final TableField<OcppTagActivityRecord, Integer> MAX_ACTIVE_TRANSACTION_COUNT = createField(DSL.name("max_active_transaction_count"), SQLDataType.INTEGER.nullable(false).defaultValue(DSL.inline("1", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.note</code>.
     */
    public final TableField<OcppTagActivityRecord, String> NOTE = createField(DSL.name("note"), SQLDataType.CLOB, this, "");

    /**
     * The column
     * <code>stevedb.ocpp_tag_activity.active_transaction_count</code>.
     */
    public final TableField<OcppTagActivityRecord, Long> ACTIVE_TRANSACTION_COUNT = createField(DSL.name("active_transaction_count"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.in_transaction</code>.
     */
    public final TableField<OcppTagActivityRecord, Boolean> IN_TRANSACTION = createField(DSL.name("in_transaction"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>stevedb.ocpp_tag_activity.blocked</code>.
     */
    public final TableField<OcppTagActivityRecord, Boolean> BLOCKED = createField(DSL.name("blocked"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.inline("0", SQLDataType.BOOLEAN)), this, "");

    private OcppTagActivity(Name alias, Table<OcppTagActivityRecord> aliased) {
        this(alias, aliased, null);
    }

    private OcppTagActivity(Name alias, Table<OcppTagActivityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("VIEW"), TableOptions.view("create view `ocpp_tag_activity` as select `stevedb`.`ocpp_tag`.`ocpp_tag_pk` AS `ocpp_tag_pk`,`stevedb`.`ocpp_tag`.`id_tag` AS `id_tag`,`stevedb`.`ocpp_tag`.`parent_id_tag` AS `parent_id_tag`,`stevedb`.`ocpp_tag`.`expiry_date` AS `expiry_date`,`stevedb`.`ocpp_tag`.`max_active_transaction_count` AS `max_active_transaction_count`,`stevedb`.`ocpp_tag`.`note` AS `note`,coalesce(`tx_activity`.`active_transaction_count`,0) AS `active_transaction_count`,(case when (`tx_activity`.`active_transaction_count` > 0) then true else false end) AS `in_transaction`,(case when (`stevedb`.`ocpp_tag`.`max_active_transaction_count` = 0) then true else false end) AS `blocked` from (`stevedb`.`ocpp_tag` left join (select `stevedb`.`transaction`.`id_tag` AS `id_tag`,count(`stevedb`.`transaction`.`id_tag`) AS `active_transaction_count` from `stevedb`.`transaction` where ((`stevedb`.`transaction`.`stop_timestamp` is null) and (`stevedb`.`transaction`.`stop_value` is null)) group by `stevedb`.`transaction`.`id_tag`) `tx_activity` on((`stevedb`.`ocpp_tag`.`id_tag` = `tx_activity`.`id_tag`)))"));
    }

    /**
     * Create an aliased <code>stevedb.ocpp_tag_activity</code> table reference
     */
    public OcppTagActivity(String alias) {
        this(DSL.name(alias), OCPP_TAG_ACTIVITY);
    }

    /**
     * Create an aliased <code>stevedb.ocpp_tag_activity</code> table reference
     */
    public OcppTagActivity(Name alias) {
        this(alias, OCPP_TAG_ACTIVITY);
    }

    /**
     * Create a <code>stevedb.ocpp_tag_activity</code> table reference
     */
    public OcppTagActivity() {
        this(DSL.name("ocpp_tag_activity"), null);
    }

    public <O extends Record> OcppTagActivity(Table<O> child, ForeignKey<O, OcppTagActivityRecord> key) {
        super(child, key, OCPP_TAG_ACTIVITY);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Stevedb.STEVEDB;
    }

    @Override
    public OcppTagActivity as(String alias) {
        return new OcppTagActivity(DSL.name(alias), this);
    }

    @Override
    public OcppTagActivity as(Name alias) {
        return new OcppTagActivity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OcppTagActivity rename(String name) {
        return new OcppTagActivity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OcppTagActivity rename(Name name) {
        return new OcppTagActivity(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, String, String, DateTime, Integer, String, Long, Boolean, Boolean> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
