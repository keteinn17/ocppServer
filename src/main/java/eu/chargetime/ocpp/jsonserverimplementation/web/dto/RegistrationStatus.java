package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ket_ein17
 */
@XmlType(
        name = "RegistrationStatus"
)
@XmlEnum
public enum RegistrationStatus {
    @XmlEnumValue("Accepted")
    ACCEPTED("Accepted"),
    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Rejected")
    REJECTED("Rejected");

    private final String value;

    private RegistrationStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RegistrationStatus fromValue(String v) {
        RegistrationStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            RegistrationStatus c = var1[var3];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
