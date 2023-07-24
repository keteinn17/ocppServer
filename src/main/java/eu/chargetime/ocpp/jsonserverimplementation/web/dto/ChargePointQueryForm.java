package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppVersion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@ToString
public class ChargePointQueryForm {
    private String chargeBoxId;
    private String description;
    private OcppVersion ocppVersion;
    private QueryPeriodType heartbeatPeriod;

    /**
     * Init with sensible default values
     */
    public ChargePointQueryForm() {
        heartbeatPeriod = QueryPeriodType.ALL;
    }

    public boolean isSetOcppVersion() {
        return ocppVersion != null;
    }

    public boolean isSetDescription() {
        return description != null;
    }

    public boolean isSetChargeBoxId() {
        return chargeBoxId != null;
    }

    @RequiredArgsConstructor
    public enum QueryPeriodType {
        ALL("All"),
        TODAY("Today"),
        YESTERDAY("Yesterday"),
        EARLIER("Earlier");

        @Getter private final String value;

        public static QueryPeriodType fromValue(String v) {
            for (QueryPeriodType c: QueryPeriodType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }
}
