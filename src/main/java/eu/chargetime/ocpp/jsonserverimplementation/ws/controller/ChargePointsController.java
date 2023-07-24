package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eu.chargetime.ocpp.jsonserverimplementation.repository.ChargePointRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppTagService;
import eu.chargetime.ocpp.jsonserverimplementation.service.ChargePointHelperService;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ChargePointQueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ket_ein17
 */
@Controller
@RequestMapping(value = "/manager/chargepoints")
public class ChargePointsController {
    @Autowired
    protected ChargePointRepository chargePointRepository;
    @Autowired protected ChargePointHelperService chargePointHelperService;

    protected static final String PARAMS = "params";
    protected static final String QUERY_PATH = "/query";
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getOverview(Model model) throws Exception {
        initList(model, new ChargePointQueryForm());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = QUERY_PATH, method = RequestMethod.GET)
    public ResponseEntity<Object> getQuery(@ModelAttribute(PARAMS) ChargePointQueryForm params, Model model) throws Exception {
        initList(model, params);
        Map<String,Object> res=new HashMap<>();
        res.put("cpList",model.getAttribute("cpList"));
        res.put("unknownList",model.getAttribute("unknownList"));
        res.put("numberOfChargePoints",model.getAttribute("numberOfChargePoints"));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private void initList(Model model, ChargePointQueryForm params) throws Exception {
        model.addAttribute(PARAMS, params);
        model.addAttribute("cpList", chargePointRepository.getOverview(params));
        model.addAttribute("unknownList", chargePointHelperService.getUnknownChargePoints());
        model.addAttribute("numberOfChargePoints",
                chargePointRepository.getOverview(params).size()
                        +chargePointHelperService.getUnknownChargePoints().size());
    }
}
