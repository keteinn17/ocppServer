/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db;


import eu.chargetime.ocpp.jsonserverimplementation.db.tables.*;


/**
 * Convenience access to all tables in stevedb.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>stevedb.address</code>.
     */
    public static final Address ADDRESS = Address.ADDRESS;

    /**
     * The table <code>stevedb.charge_box</code>.
     */
    public static final ChargeBox CHARGE_BOX = ChargeBox.CHARGE_BOX;

    /**
     * The table <code>stevedb.charging_profile</code>.
     */
    public static final ChargingProfile CHARGING_PROFILE = ChargingProfile.CHARGING_PROFILE;

    /**
     * The table <code>stevedb.charging_schedule_period</code>.
     */
    public static final ChargingSchedulePeriod CHARGING_SCHEDULE_PERIOD = ChargingSchedulePeriod.CHARGING_SCHEDULE_PERIOD;

    /**
     * The table <code>stevedb.connector</code>.
     */
    public static final Connector CONNECTOR = Connector.CONNECTOR;

    /**
     * The table <code>stevedb.connector_charging_profile</code>.
     */
    public static final ConnectorChargingProfile CONNECTOR_CHARGING_PROFILE = ConnectorChargingProfile.CONNECTOR_CHARGING_PROFILE;

    /**
     * The table <code>stevedb.connector_meter_value</code>.
     */
    public static final ConnectorMeterValue CONNECTOR_METER_VALUE = ConnectorMeterValue.CONNECTOR_METER_VALUE;

    /**
     * The table <code>stevedb.connector_status</code>.
     */
    public static final ConnectorStatus CONNECTOR_STATUS = ConnectorStatus.CONNECTOR_STATUS;

    /**
     * The table <code>stevedb.ocpp_tag</code>.
     */
    public static final OcppTag OCPP_TAG = OcppTag.OCPP_TAG;

    /**
     * VIEW
     */
    public static final OcppTagActivity OCPP_TAG_ACTIVITY = OcppTagActivity.OCPP_TAG_ACTIVITY;

    /**
     * The table <code>stevedb.reservation</code>.
     */
    public static final Reservation RESERVATION = Reservation.RESERVATION;

    /**
     * The table <code>stevedb.schema_version</code>.
     */
    public static final SchemaVersion SCHEMA_VERSION = SchemaVersion.SCHEMA_VERSION;

    /**
     * The table <code>stevedb.settings</code>.
     */
    public static final Settings SETTINGS = Settings.SETTINGS;

    /**
     * VIEW
     */
    public static final Transaction TRANSACTION = Transaction.TRANSACTION;

    /**
     * The table <code>stevedb.transaction_start</code>.
     */
    public static final TransactionStart TRANSACTION_START = TransactionStart.TRANSACTION_START;

    /**
     * The table <code>stevedb.transaction_stop</code>.
     */
    public static final TransactionStop TRANSACTION_STOP = TransactionStop.TRANSACTION_STOP;

    /**
     * The table <code>stevedb.transaction_stop_failed</code>.
     */
    public static final TransactionStopFailed TRANSACTION_STOP_FAILED = TransactionStopFailed.TRANSACTION_STOP_FAILED;

    /**
     * The table <code>stevedb.user</code>.
     */
    public static final User USER = User.USER;
}
