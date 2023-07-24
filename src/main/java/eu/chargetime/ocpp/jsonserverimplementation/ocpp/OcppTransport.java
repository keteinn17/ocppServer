package eu.chargetime.ocpp.jsonserverimplementation.ocpp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OcppTransport {
    SOAP("S"),  // HTTP with SOAP payloads
    JSON("J");  // WebSocket with JSON payloads

    // The value should always contain ONE character!
    // Otherwise, it will break OcppProtocol.fromCompositeValue()
    //
    private final String value;

    public static OcppTransport fromValue(String v) {
        for (OcppTransport c: OcppTransport.values()) {
            if (c.getValue().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static OcppTransport fromName(String v) {
        for (OcppTransport c: OcppTransport.values()) {
            if (c.name().equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
