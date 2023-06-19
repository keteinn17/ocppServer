package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;
@Getter
@Builder
public class InsertConnectorStatusParams {
    private final String chargeBoxId;
    private final int connectorId;
    private final DateTime timestamp;
    private final String status;
    private final String errorCode;

    // Only in OCPP1.5
    private final String errorInfo, vendorId, vendorErrorCode;
}
