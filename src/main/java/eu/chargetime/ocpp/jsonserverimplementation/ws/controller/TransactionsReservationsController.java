package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import eu.chargetime.ocpp.jsonserverimplementation.repository.ChargePointRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppTagService;
import eu.chargetime.ocpp.jsonserverimplementation.repository.TransactionRepository;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.TransactionQueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ket_ein17
 */
@Controller
@RequestMapping(value = "/manager", method = RequestMethod.GET)
public class TransactionsReservationsController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ChargePointRepository chargePointRepository;
    @Autowired
    private OcppTagService ocppTagService;
    private static final String PARAMS = "params";

    // -------------------------------------------------------------------------
    // Paths
    // -------------------------------------------------------------------------

    private static final String TRANSACTIONS_PATH = "/transactions";
    private static final String TRANSACTIONS_QUERY_PATH = "/transactions/query";

    @RequestMapping(value = TRANSACTIONS_PATH)
    public ResponseEntity<Object> getTransactions(Model model) throws Exception {
        TransactionQueryForm params = new TransactionQueryForm();
        initList(model);

        model.addAttribute("transList", transactionRepository.getTransactions(params));
        model.addAttribute(PARAMS, params);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = TRANSACTIONS_QUERY_PATH,method = RequestMethod.GET)
    public ResponseEntity<Object> getTransactionsQuery(@Valid @ModelAttribute(PARAMS) TransactionQueryForm params, Model model) throws Exception {
        model.addAttribute("transList", transactionRepository.getTransactions(params));
        initList(model);
        model.addAttribute(PARAMS, params);
        Map<String,Object>  res = new HashMap<>();
        res.put("cpList",model.getAttribute("cpList"));
        res.put("idTagList",model.getAttribute("idTagList"));
        res.put("transList",model.getAttribute("transList"));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private void initList(Model model) {
        model.addAttribute("cpList", chargePointRepository.getChargeBoxIds());
        model.addAttribute("idTagList", ocppTagService.getIdTags());
    }
}
