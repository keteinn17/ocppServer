package eu.chargetime.ocpp.jsonserverimplementation.dao;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(
        name = "ChargePointStatus"
)
@XmlEnum
public enum ChargePointStatus {
    @XmlEnumValue("Available")
    AVAILABLE("Available"),
    @XmlEnumValue("Preparing")
    PREPARING("Preparing"),
    @XmlEnumValue("Charging")
    CHARGING("Charging"),
    @XmlEnumValue("SuspendedEV")
    SUSPENDED_EV("SuspendedEV"),
    @XmlEnumValue("SuspendedEVSE")
    SUSPENDED_EVSE("SuspendedEVSE"),
    @XmlEnumValue("Finishing")
    FINISHING("Finishing"),
    @XmlEnumValue("Reserved")
    RESERVED("Reserved"),
    @XmlEnumValue("Faulted")
    FAULTED("Faulted"),
    @XmlEnumValue("Unavailable")
    UNAVAILABLE("Unavailable");

    private final String value;

    private ChargePointStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ChargePointStatus fromValue(String v) {
        ChargePointStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ChargePointStatus c = var1[var3];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
