package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppProtocol;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;

@Getter
@Builder
public final class UpdateChargeBoxParams {
    private OcppProtocol ocppProtocol;
    private final DateTime heartbeatTimestamp;
    private final String vendor, model, pointSerial, boxSerial, fwVersion,
            iccid, imsi, meterType, meterSerial, chargeBoxId;
}
