package eu.chargetime.ocpp.jsonserverimplementation.dao;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
        name = "ChargePointErrorCode"
)
@XmlEnum
public enum ChargePointErrorCode {
    @XmlEnumValue("ConnectorLockFailure")
    CONNECTOR_LOCK_FAILURE("ConnectorLockFailure"),
    @XmlEnumValue("EVCommunicationError")
    EV_COMMUNICATION_ERROR("EVCommunicationError"),
    @XmlEnumValue("GroundFailure")
    GROUND_FAILURE("GroundFailure"),
    @XmlEnumValue("HighTemperature")
    HIGH_TEMPERATURE("HighTemperature"),
    @XmlEnumValue("InternalError")
    INTERNAL_ERROR("InternalError"),
    @XmlEnumValue("LocalListConflict")
    LOCAL_LIST_CONFLICT("LocalListConflict"),
    @XmlEnumValue("NoError")
    NO_ERROR("NoError"),
    @XmlEnumValue("OtherError")
    OTHER_ERROR("OtherError"),
    @XmlEnumValue("OverCurrentFailure")
    OVER_CURRENT_FAILURE("OverCurrentFailure"),
    @XmlEnumValue("OverVoltage")
    OVER_VOLTAGE("OverVoltage"),
    @XmlEnumValue("PowerMeterFailure")
    POWER_METER_FAILURE("PowerMeterFailure"),
    @XmlEnumValue("PowerSwitchFailure")
    POWER_SWITCH_FAILURE("PowerSwitchFailure"),
    @XmlEnumValue("ReaderFailure")
    READER_FAILURE("ReaderFailure"),
    @XmlEnumValue("ResetFailure")
    RESET_FAILURE("ResetFailure"),
    @XmlEnumValue("UnderVoltage")
    UNDER_VOLTAGE("UnderVoltage"),
    @XmlEnumValue("WeakSignal")
    WEAK_SIGNAL("WeakSignal");

    private final String value;

    private ChargePointErrorCode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ChargePointErrorCode fromValue(String v) {
        ChargePointErrorCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ChargePointErrorCode c = var1[var3];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
