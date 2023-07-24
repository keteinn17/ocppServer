package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppTagService;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.OcppTagQueryForm;
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
@RequestMapping(value = "/manager/ocppTags")
public class OcppTagsController {
    @Autowired
    protected OcppTagService ocppTagService;

    protected static final String PARAMS = "params";
    protected static final String QUERY_PATH = "/query";

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> get(Model model) throws Exception {
        initList(model, new OcppTagQueryForm());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = QUERY_PATH, method = RequestMethod.GET)
    public ResponseEntity<Object> getQuery(@ModelAttribute(PARAMS) OcppTagQueryForm params, Model model) throws Exception {
        initList(model, params);
        Map<String,Object> res=new HashMap<>();
        res.put("idTagList", model.getAttribute("idTagList"));
        res.put("parentIdTagList", model.getAttribute("parentIdTagList"));
        res.put("ocppTagList", model.getAttribute("ocppTagList"));
        res.put("unknownList", model.getAttribute("unknownList"));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private void initList(Model model, OcppTagQueryForm params) throws Exception {
        model.addAttribute(PARAMS, params);
        model.addAttribute("idTagList", ocppTagService.getIdTags());
        model.addAttribute("parentIdTagList", ocppTagService.getParentIdTags());
        model.addAttribute("ocppTagList", ocppTagService.getOverview(params));
        model.addAttribute("unknownList", ocppTagService.getUnknownOcppTags());
    }
}
