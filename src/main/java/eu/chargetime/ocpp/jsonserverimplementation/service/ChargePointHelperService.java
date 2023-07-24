package eu.chargetime.ocpp.jsonserverimplementation.service;

import com.google.common.util.concurrent.Striped;
import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppProtocol;
import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppTransport;
import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppVersion;
import eu.chargetime.ocpp.jsonserverimplementation.repository.ChargePointRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;
import eu.chargetime.ocpp.jsonserverimplementation.service.dto.UnidentifiedIncomingObject;
import eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeUtils;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ConnectorStatusForm;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.OcppJsonStatus;
import eu.chargetime.ocpp.jsonserverimplementation.ws.data.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration.CONFIG;

/**
 * @author ket_ein17
 */

@Slf4j
@Service
public class ChargePointHelperService {
    private final boolean autoRegisterUnknownStations = CONFIG.getOcpp().isAutoRegisterUnknownStations();
    private final Striped<Lock> isRegisteredLocks = Striped.lock(16);
    @Autowired private ChargePointRepository chargePointRepository;
    /*@Autowired
    private Ocpp16WebSocketEndpoint ocpp16WebSocketEndpoint;*/

    private final UnidentifiedIncomingObjectService unknownChargePointService = new UnidentifiedIncomingObjectService(100);


    public List<UnidentifiedIncomingObject> getUnknownChargePoints() {
        return unknownChargePointService.getObjects();
    }

    public List<OcppJsonStatus> getOcppJsonStatus(){
//        Map<String, Deque<SessionContext>> ocpp12Map = ocpp12WebSocketEndpoint.getACopy();
//        Map<String, Deque<SessionContext>> ocpp15Map = ocpp15WebSocketEndpoint.getACopy();
        Map<String, Deque<SessionContext>> ocpp16Map = new HashMap<>();
        List<String> idList = extractIds(Arrays.asList(ocpp16Map));
        Map<String, Integer> primaryKeyLookup = chargePointRepository.getChargeBoxIdPkPair(idList);

        DateTime now = DateTime.now();
        List<OcppJsonStatus> returnList = new ArrayList<>();

//        appendList(ocpp12Map, returnList, now, OcppVersion.V_12, primaryKeyLookup);
//        appendList(ocpp15Map, returnList, now, OcppVersion.V_15, primaryKeyLookup);
        appendList(ocpp16Map, returnList, now, OcppVersion.V_16, primaryKeyLookup);
        return returnList;
    }

    private static void appendList(Map<String, Deque<SessionContext>> map, List<OcppJsonStatus> returnList,
                                   DateTime now, OcppVersion version, Map<String, Integer> primaryKeyLookup) {

        for (Map.Entry<String, Deque<SessionContext>> entry : map.entrySet()) {
            String chargeBoxId = entry.getKey();
            Deque<SessionContext> endpointDeque = entry.getValue();

            for (SessionContext ctx : endpointDeque) {
                DateTime openSince = ctx.getOpenSince();

                OcppJsonStatus status = OcppJsonStatus.builder()
                        .chargeBoxPk(primaryKeyLookup.get(chargeBoxId))
                        .chargeBoxId(chargeBoxId)
                        .connectedSinceDT(openSince)
                        .connectedSince(DateTimeUtils.humanize(openSince))
                        .connectionDuration(DateTimeUtils.timeElapsed(openSince, now))
                        .version(version)
                        .build();

                returnList.add(status);
            }
        }
    }

    public List<ConnectorStatus> getChargePointConnectorStatus(ConnectorStatusForm params) {
//        Map<String, Deque<SessionContext>> ocpp12Map = ocpp12WebSocketEndpoint.getACopy();
//        Map<String, Deque<SessionContext>> ocpp15Map = ocpp15WebSocketEndpoint.getACopy();
        Map<String, Deque<SessionContext>> ocpp16Map = new HashMap<>();

        Set<String> connectedJsonChargeBoxIds = new HashSet<>(extractIds(Arrays.asList( ocpp16Map)));

        List<ConnectorStatus> latestList = chargePointRepository.getChargePointConnectorStatus(params);

        // iterate over JSON stations and mark disconnected ones
        // https://github.com/steve-community/steve/issues/355
        //
        for (ConnectorStatus status : latestList) {
            OcppProtocol protocol = status.getOcppProtocol();
            if (protocol != null && protocol.getTransport() == OcppTransport.JSON) {
                status.setJsonAndDisconnected(!connectedJsonChargeBoxIds.contains(status.getChargeBoxId()));
            }
        }

        return latestList;
    }

    private static List<String> extractIds(List<Map<String, Deque<SessionContext>>> ocppMaps) {
        return ocppMaps.stream()
                .map(Map::keySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
