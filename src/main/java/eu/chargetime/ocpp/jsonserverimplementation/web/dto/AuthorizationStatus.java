package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ket_ein17
 */
@XmlType(
        name = "AuthorizationStatus"
)
@XmlEnum
public enum AuthorizationStatus {
    @XmlEnumValue("Accepted")
    ACCEPTED("Accepted"),
    @XmlEnumValue("Blocked")
    BLOCKED("Blocked"),
    @XmlEnumValue("Expired")
    EXPIRED("Expired"),
    @XmlEnumValue("Invalid")
    INVALID("Invalid"),
    @XmlEnumValue("ConcurrentTx")
    CONCURRENT_TX("ConcurrentTx");

    private final String value;

    private AuthorizationStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AuthorizationStatus fromValue(String v) {
        AuthorizationStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            AuthorizationStatus c = var1[var3];
            if (c.value.equals(v)) {
                return c;
            }
        }

        throw new IllegalArgumentException(v);
    }
}
