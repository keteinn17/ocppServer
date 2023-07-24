package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import eu.chargetime.ocpp.jsonserverimplementation.repository.ChargePointRepository;
import eu.chargetime.ocpp.jsonserverimplementation.service.ChargePointHelperService;
import eu.chargetime.ocpp.jsonserverimplementation.utils.ConnectorStatusCountFilter;
import eu.chargetime.ocpp.jsonserverimplementation.utils.ConnectorStatusFilter;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ConnectorStatusForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ket_ein17
 */

@Controller
@RequestMapping(value = "/manager", method = RequestMethod.GET)
public class HomeController {
    @Autowired
    private ChargePointRepository chargePointRepository;
    @Autowired private ChargePointHelperService chargePointHelperService;
    private static final String PARAMS = "params";

    private static final String HOME_PREFIX = "/home";

    private static final String OCPP_JSON_STATUS = HOME_PREFIX + "/ocppJsonStatus";
    private static final String CONNECTOR_STATUS_PATH = HOME_PREFIX + "/connectorStatus";
    private static final String CONNECTOR_STATUS_QUERY_PATH = HOME_PREFIX + "/connectorStatus/query";

    @RequestMapping(value = OCPP_JSON_STATUS)
    public ResponseEntity<Object> getOcppJsonStatus(Model model) {
        model.addAttribute("ocppJsonStatusList", chargePointHelperService.getOcppJsonStatus());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }
    @RequestMapping(value = CONNECTOR_STATUS_PATH)
    public ResponseEntity<Object> getConnectorStatus(Model model) {
        return getConnectorStatusQuery(new ConnectorStatusForm(), model);
    }

    @RequestMapping(value = CONNECTOR_STATUS_QUERY_PATH)
    public ResponseEntity<Object> getConnectorStatusQuery(@ModelAttribute(PARAMS) ConnectorStatusForm params, Model model) {
        model.addAttribute("cpList", chargePointRepository.getChargeBoxIds());
        model.addAttribute("statusValues", ConnectorStatusCountFilter.ALL_STATUS_VALUES);
        model.addAttribute(PARAMS, params);

        List<ConnectorStatus> latestList = chargePointHelperService.getChargePointConnectorStatus(params);
        List<ConnectorStatus> filteredList = ConnectorStatusFilter.filterAndPreferZero(latestList);
        for(ConnectorStatus connectorStatus:filteredList){
            connectorStatus.setStatusTimest(connectorStatus.getStatusTimestamp().toString());
            connectorStatus.setStatusTimestamp(null);
        }
        model.addAttribute("connectorStatusList", filteredList);

        Map<String, Object> res = new HashMap<>();
        res.put("params",params);
        res.put("cpList",model.getAttribute("cpList"));
        res.put("statusValues",model.getAttribute("statusValues"));
        res.put("connectorStatusList",model.getAttribute("connectorStatusList"));
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
