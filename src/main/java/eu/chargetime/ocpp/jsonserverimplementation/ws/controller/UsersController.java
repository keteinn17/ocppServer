package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppTagService;
import eu.chargetime.ocpp.jsonserverimplementation.repository.UserRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.User;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.UserQueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ket_ein17
 */
@Controller
@RequestMapping(value = "/manager/users")
public class UsersController {
    @Autowired
    private OcppTagService ocppTagService;
    @Autowired private UserRepository userRepository;
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    private static final String PARAMS = "params";
    private static final String QUERY_PATH = "/query";

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getOverview(Model model) {
        initList(model, new UserQueryForm());
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(value = QUERY_PATH, method = RequestMethod.GET)
    public ResponseEntity<Object> getQuery(@ModelAttribute(PARAMS) UserQueryForm params, Model model) {
        initList(model, params);
        List<User.Overview> user = (List<User.Overview>) model.getAttribute("userList");
        Map<String,Object> res= new HashMap<>();
        res.put(PARAMS,(UserQueryForm)model.getAttribute(PARAMS));
        res.put("userList",user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private void initList(Model model, UserQueryForm params) {
        model.addAttribute(PARAMS, params);
        model.addAttribute("userList", userRepository.getOverview(params));
    }
}
