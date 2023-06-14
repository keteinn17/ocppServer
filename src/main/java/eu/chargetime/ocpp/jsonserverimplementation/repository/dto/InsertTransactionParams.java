package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

import java.time.ZonedDateTime;

@Getter
@Builder
public class InsertTransactionParams {
    private final String chargeBoxId;
    private final int connectorId;
    private final String idTag;
    private final DateTime startTimestamp;
    private final String startMeterValue;

    private final TransactionStatusUpdate statusUpdate = TransactionStatusUpdate.AfterStart;


    // Only in OCPP1.5
    private final Integer reservationId;

    // this came after splitting transaction table into two tables (start and stop)
    private final DateTime eventTimestamp;

    public boolean isSetReservationId() {
        return reservationId != null;
    }
}
