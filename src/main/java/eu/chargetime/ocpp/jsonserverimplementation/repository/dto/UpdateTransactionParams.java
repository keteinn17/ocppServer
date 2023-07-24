package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

@Getter
@Builder
public class UpdateTransactionParams {
    private final String chargeBoxId;
    private final int transactionId;
    private final DateTime stopTimestamp;
    private final String stopMeterValue;
    private final String stopReason;

    private final TransactionStatusUpdate statusUpdate = TransactionStatusUpdate.AfterStop;

    // these two came after splitting transaction table into two tables (start and stop)
    private final TransactionStopEventActor eventActor;
    private final DateTime eventTimestamp;
}
