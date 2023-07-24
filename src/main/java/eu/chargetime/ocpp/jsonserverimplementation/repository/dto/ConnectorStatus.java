package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppProtocol;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 * @author ket_ein17
 */
@Getter
@Builder
public class ConnectorStatus {
    private final String chargeBoxId, timeStamp, status, errorCode;
    private final int chargeBoxPk, connectorId;

    // For additional internal processing. Not related to the humanized
    // String version above, which is for representation on frontend
    @Setter
    private  DateTime statusTimestamp;
    @Setter
    private  String statusTimest;
    private final OcppProtocol ocppProtocol;

    // This is true, if the chargeBox this connector belongs to is a WS/JSON station
    // and it is disconnected at the moment of building this DTO.
    @Setter
    @Builder.Default
    private boolean jsonAndDisconnected = false;


}
