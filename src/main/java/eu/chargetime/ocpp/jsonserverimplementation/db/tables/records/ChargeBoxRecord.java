/*
 * This file is generated by jOOQ.
 */
package eu.chargetime.ocpp.jsonserverimplementation.db.tables.records;


import eu.chargetime.ocpp.jsonserverimplementation.db.tables.ChargeBox;
import org.joda.time.DateTime;
import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import java.math.BigDecimal;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ChargeBoxRecord extends UpdatableRecordImpl<ChargeBoxRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>stevedb.charge_box.charge_box_pk</code>.
     */
    public ChargeBoxRecord setChargeBoxPk(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_box_pk</code>.
     */
    public Integer getChargeBoxPk() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>stevedb.charge_box.charge_box_id</code>.
     */
    public ChargeBoxRecord setChargeBoxId(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_box_id</code>.
     */
    public String getChargeBoxId() {
        return (String) get(1);
    }

    /**
     * Setter for <code>stevedb.charge_box.endpoint_address</code>.
     */
    public ChargeBoxRecord setEndpointAddress(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.endpoint_address</code>.
     */
    public String getEndpointAddress() {
        return (String) get(2);
    }

    /**
     * Setter for <code>stevedb.charge_box.ocpp_protocol</code>.
     */
    public ChargeBoxRecord setOcppProtocol(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.ocpp_protocol</code>.
     */
    public String getOcppProtocol() {
        return (String) get(3);
    }

    /**
     * Setter for <code>stevedb.charge_box.registration_status</code>.
     */
    public ChargeBoxRecord setRegistrationStatus(String value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.registration_status</code>.
     */
    public String getRegistrationStatus() {
        return (String) get(4);
    }

    /**
     * Setter for <code>stevedb.charge_box.charge_point_vendor</code>.
     */
    public ChargeBoxRecord setChargePointVendor(String value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_point_vendor</code>.
     */
    public String getChargePointVendor() {
        return (String) get(5);
    }

    /**
     * Setter for <code>stevedb.charge_box.charge_point_model</code>.
     */
    public ChargeBoxRecord setChargePointModel(String value) {
        set(6, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_point_model</code>.
     */
    public String getChargePointModel() {
        return (String) get(6);
    }

    /**
     * Setter for <code>stevedb.charge_box.charge_point_serial_number</code>.
     */
    public ChargeBoxRecord setChargePointSerialNumber(String value) {
        set(7, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_point_serial_number</code>.
     */
    public String getChargePointSerialNumber() {
        return (String) get(7);
    }

    /**
     * Setter for <code>stevedb.charge_box.charge_box_serial_number</code>.
     */
    public ChargeBoxRecord setChargeBoxSerialNumber(String value) {
        set(8, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.charge_box_serial_number</code>.
     */
    public String getChargeBoxSerialNumber() {
        return (String) get(8);
    }

    /**
     * Setter for <code>stevedb.charge_box.fw_version</code>.
     */
    public ChargeBoxRecord setFwVersion(String value) {
        set(9, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.fw_version</code>.
     */
    public String getFwVersion() {
        return (String) get(9);
    }

    /**
     * Setter for <code>stevedb.charge_box.fw_update_status</code>.
     */
    public ChargeBoxRecord setFwUpdateStatus(String value) {
        set(10, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.fw_update_status</code>.
     */
    public String getFwUpdateStatus() {
        return (String) get(10);
    }

    /**
     * Setter for <code>stevedb.charge_box.fw_update_timestamp</code>.
     */
    public ChargeBoxRecord setFwUpdateTimestamp(DateTime value) {
        set(11, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.fw_update_timestamp</code>.
     */
    public DateTime getFwUpdateTimestamp() {
        return (DateTime) get(11);
    }

    /**
     * Setter for <code>stevedb.charge_box.iccid</code>.
     */
    public ChargeBoxRecord setIccid(String value) {
        set(12, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.iccid</code>.
     */
    public String getIccid() {
        return (String) get(12);
    }

    /**
     * Setter for <code>stevedb.charge_box.imsi</code>.
     */
    public ChargeBoxRecord setImsi(String value) {
        set(13, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.imsi</code>.
     */
    public String getImsi() {
        return (String) get(13);
    }

    /**
     * Setter for <code>stevedb.charge_box.meter_type</code>.
     */
    public ChargeBoxRecord setMeterType(String value) {
        set(14, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.meter_type</code>.
     */
    public String getMeterType() {
        return (String) get(14);
    }

    /**
     * Setter for <code>stevedb.charge_box.meter_serial_number</code>.
     */
    public ChargeBoxRecord setMeterSerialNumber(String value) {
        set(15, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.meter_serial_number</code>.
     */
    public String getMeterSerialNumber() {
        return (String) get(15);
    }

    /**
     * Setter for <code>stevedb.charge_box.diagnostics_status</code>.
     */
    public ChargeBoxRecord setDiagnosticsStatus(String value) {
        set(16, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.diagnostics_status</code>.
     */
    public String getDiagnosticsStatus() {
        return (String) get(16);
    }

    /**
     * Setter for <code>stevedb.charge_box.diagnostics_timestamp</code>.
     */
    public ChargeBoxRecord setDiagnosticsTimestamp(DateTime value) {
        set(17, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.diagnostics_timestamp</code>.
     */
    public DateTime getDiagnosticsTimestamp() {
        return (DateTime) get(17);
    }

    /**
     * Setter for <code>stevedb.charge_box.last_heartbeat_timestamp</code>.
     */
    public ChargeBoxRecord setLastHeartbeatTimestamp(DateTime value) {
        set(18, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.last_heartbeat_timestamp</code>.
     */
    public DateTime getLastHeartbeatTimestamp() {
        return (DateTime) get(18);
    }

    /**
     * Setter for <code>stevedb.charge_box.description</code>.
     */
    public ChargeBoxRecord setDescription(String value) {
        set(19, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.description</code>.
     */
    public String getDescription() {
        return (String) get(19);
    }

    /**
     * Setter for <code>stevedb.charge_box.note</code>.
     */
    public ChargeBoxRecord setNote(String value) {
        set(20, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.note</code>.
     */
    public String getNote() {
        return (String) get(20);
    }

    /**
     * Setter for <code>stevedb.charge_box.location_latitude</code>.
     */
    public ChargeBoxRecord setLocationLatitude(BigDecimal value) {
        set(21, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.location_latitude</code>.
     */
    public BigDecimal getLocationLatitude() {
        return (BigDecimal) get(21);
    }

    /**
     * Setter for <code>stevedb.charge_box.location_longitude</code>.
     */
    public ChargeBoxRecord setLocationLongitude(BigDecimal value) {
        set(22, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.location_longitude</code>.
     */
    public BigDecimal getLocationLongitude() {
        return (BigDecimal) get(22);
    }

    /**
     * Setter for <code>stevedb.charge_box.address_pk</code>.
     */
    public ChargeBoxRecord setAddressPk(Integer value) {
        set(23, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.address_pk</code>.
     */
    public Integer getAddressPk() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>stevedb.charge_box.admin_address</code>.
     */
    public ChargeBoxRecord setAdminAddress(String value) {
        set(24, value);
        return this;
    }

    /**
     * Getter for <code>stevedb.charge_box.admin_address</code>.
     */
    public String getAdminAddress() {
        return (String) get(24);
    }

    /**
     * Setter for
     * <code>stevedb.charge_box.insert_connector_status_after_transaction_msg</code>.
     */
    public ChargeBoxRecord setInsertConnectorStatusAfterTransactionMsg(Boolean value) {
        set(25, value);
        return this;
    }

    /**
     * Getter for
     * <code>stevedb.charge_box.insert_connector_status_after_transaction_msg</code>.
     */
    public Boolean getInsertConnectorStatusAfterTransactionMsg() {
        return (Boolean) get(25);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ChargeBoxRecord
     */
    public ChargeBoxRecord() {
        super(ChargeBox.CHARGE_BOX);
    }

    /**
     * Create a detached, initialised ChargeBoxRecord
     */
    public ChargeBoxRecord(Integer chargeBoxPk, String chargeBoxId, String endpointAddress, String ocppProtocol, String registrationStatus, String chargePointVendor, String chargePointModel, String chargePointSerialNumber, String chargeBoxSerialNumber, String fwVersion, String fwUpdateStatus, DateTime fwUpdateTimestamp, String iccid, String imsi, String meterType, String meterSerialNumber, String diagnosticsStatus, DateTime diagnosticsTimestamp, DateTime lastHeartbeatTimestamp, String description, String note, BigDecimal locationLatitude, BigDecimal locationLongitude, Integer addressPk, String adminAddress, Boolean insertConnectorStatusAfterTransactionMsg) {
        super(ChargeBox.CHARGE_BOX);

        setChargeBoxPk(chargeBoxPk);
        setChargeBoxId(chargeBoxId);
        setEndpointAddress(endpointAddress);
        setOcppProtocol(ocppProtocol);
        setRegistrationStatus(registrationStatus);
        setChargePointVendor(chargePointVendor);
        setChargePointModel(chargePointModel);
        setChargePointSerialNumber(chargePointSerialNumber);
        setChargeBoxSerialNumber(chargeBoxSerialNumber);
        setFwVersion(fwVersion);
        setFwUpdateStatus(fwUpdateStatus);
        setFwUpdateTimestamp(fwUpdateTimestamp);
        setIccid(iccid);
        setImsi(imsi);
        setMeterType(meterType);
        setMeterSerialNumber(meterSerialNumber);
        setDiagnosticsStatus(diagnosticsStatus);
        setDiagnosticsTimestamp(diagnosticsTimestamp);
        setLastHeartbeatTimestamp(lastHeartbeatTimestamp);
        setDescription(description);
        setNote(note);
        setLocationLatitude(locationLatitude);
        setLocationLongitude(locationLongitude);
        setAddressPk(addressPk);
        setAdminAddress(adminAddress);
        setInsertConnectorStatusAfterTransactionMsg(insertConnectorStatusAfterTransactionMsg);
    }
}
