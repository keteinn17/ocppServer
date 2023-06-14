package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;
@Getter
@Builder
public class InsertReservationParams {
    private final String idTag, chargeBoxId;
    private final int connectorId;
    private final DateTime startTimestamp, expiryTimestamp;
}
