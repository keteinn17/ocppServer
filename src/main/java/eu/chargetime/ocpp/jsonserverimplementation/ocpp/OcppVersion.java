package eu.chargetime.ocpp.jsonserverimplementation.ocpp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OcppVersion {
    V_12("ocpp1.2"),
    V_15("ocpp1.5"),
    V_16("ocpp1.6");

    private final String value;

    public static OcppVersion fromValue(String v) {
        for (OcppVersion c: OcppVersion.values()) {
            if (c.getValue().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public OcppProtocol toProtocol(OcppTransport transport) {
        for (OcppProtocol value : OcppProtocol.values()) {
            if (value.getVersion() == this && value.getTransport() == transport) {
                return value;
            }
        }
        throw new IllegalArgumentException("Could not find OcppProtocol for " + transport);
    }
}
