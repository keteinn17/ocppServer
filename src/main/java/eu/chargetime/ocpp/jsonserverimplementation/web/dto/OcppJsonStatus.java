package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppVersion;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * @author ket_ein17
 */

@Getter
@Builder
@ToString
public class OcppJsonStatus {
    private final int chargeBoxPk;
    private final String chargeBoxId, connectedSince;
    private final String connectionDuration;
    private final OcppVersion version;
    private final DateTime connectedSinceDT;
}
