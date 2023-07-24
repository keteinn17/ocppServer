package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.AddressRecord;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.ChargeBoxRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author ket_ein17
 */
public final class ChargePoint {
    @Getter
    @Builder
    public static final class Overview {
        private final int chargeBoxPk;
        private final String chargeBoxId, description, ocppProtocol, lastHeartbeatTimestamp;
        private final String lastHeartbeatTimestampDT;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Details {
        private final ChargeBoxRecord chargeBox;
        private final AddressRecord address;
    }
}
