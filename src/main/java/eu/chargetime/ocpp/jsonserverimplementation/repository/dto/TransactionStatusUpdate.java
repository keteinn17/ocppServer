package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import eu.chargetime.ocpp.jsonserverimplementation.dao.ChargePointErrorCode;
import eu.chargetime.ocpp.jsonserverimplementation.dao.ChargePointStatus;
import lombok.Getter;

@Getter
public enum TransactionStatusUpdate {
    AfterStart(ChargePointStatus.CHARGING),
    AfterStop(ChargePointStatus.AVAILABLE);

    private final String status;
    private final String errorCode = ChargePointErrorCode.NO_ERROR.value();

    TransactionStatusUpdate(ChargePointStatus status) {
        this.status = status.value();
    }
}
