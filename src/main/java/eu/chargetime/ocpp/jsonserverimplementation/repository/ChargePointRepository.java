package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ChargePoint;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ChargePointQueryForm;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ConnectorStatusForm;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author ket_ein17
 */
public interface ChargePointRepository {
    List<ChargePoint.Overview> getOverview(ChargePointQueryForm form) throws Exception;
    List<String> getChargeBoxIds();
    Map<String, Integer> getChargeBoxIdPkPair(List<String> chargeBoxIdList);
    List<ConnectorStatus> getChargePointConnectorStatus(@Nullable ConnectorStatusForm form);
}
