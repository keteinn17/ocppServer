package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@ToString
public class ConnectorStatusForm {
    private String chargeBoxId;
    private String status;
}
