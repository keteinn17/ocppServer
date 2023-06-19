package eu.chargetime.ocpp.jsonserverimplementation.service;

import lombok.Data;

@Data
public class OcppStationStatusFailure {
    private final String chargeBoxId;
    private final int connectorId;
    private final String errorCode;
}
