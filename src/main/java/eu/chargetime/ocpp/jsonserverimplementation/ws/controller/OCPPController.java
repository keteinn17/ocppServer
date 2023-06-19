package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.jsonserverimplementation.config.server.ServerEventConfig;
import eu.chargetime.ocpp.jsonserverimplementation.ws.sendrequest.SendRequest;

import eu.chargetime.ocpp.model.core.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;


@Slf4j
@Controller
@RequestMapping(value = "/manager/operations/v1.6")
public class OCPPController {

    @Autowired
    private final JSONServer server;
    @Autowired
    public OCPPController(JSONServer jsonServer){
        this.server=jsonServer;
    }
    private static final String CHANGE_AVAIL_PATH = "/ChangeAvailability";
    private static final String CANCEL_RESERVATION = "/CancelReservation";
    @Autowired private SendRequest sendRequest;
    protected String getPrefix() {
        return "op16";
    }
    @RequestMapping(value = CHANGE_AVAIL_PATH, method = RequestMethod.POST)
    public ResponseEntity<Object> postChangeAvail(@ModelAttribute ChangeAvailabilityRequest params, @RequestParam String chargeBoxId) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        try{
            UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
            System.out.println("Charge box id: "+chargeBoxId+" "+params.toString());
            return new ResponseEntity<>(server.send(indexSession,params).toCompletableFuture().get(),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Charge box is not recognized!",HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = CANCEL_RESERVATION, method = RequestMethod.POST)
    public ResponseEntity<Object> clearCache(@ModelAttribute ClearCacheRequest params, @RequestParam String chargeBoxId) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        try{
            UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
            return  new ResponseEntity<>(server.send(indexSession,params).toCompletableFuture().get(),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Charge box is not recognized!",HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/ChangeConfiguration", method = RequestMethod.POST)
    public ResponseEntity<Object> changeConfiguration(@ModelAttribute ChangeConfigurationRequest params,@RequestParam String chargeBoxId) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
        try {
            return new ResponseEntity<>(server.send(indexSession,params).toCompletableFuture().get(),HttpStatus.OK);
        } catch (InterruptedException | NotConnectedException | UnsupportedFeatureException |
                 OccurenceConstraintException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/GetConfiguration", method = RequestMethod.POST)
    public ResponseEntity<Object> getConfiguration(@ModelAttribute GetConfigurationRequest params, @RequestParam String chargeBoxId) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
        try {
            return new ResponseEntity<>(server.send(indexSession,params).toCompletableFuture().get(),HttpStatus.OK);
        } catch (InterruptedException | NotConnectedException | UnsupportedFeatureException |
                 OccurenceConstraintException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/DataTransfer", method = RequestMethod.POST)
    public ResponseEntity<Object> dataTransfer(@ModelAttribute DataTransferRequest params, @RequestParam String chargeBoxId) throws Exception {
        UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
        try {
            return new ResponseEntity<>(server.send(indexSession,params).toCompletableFuture().get(),HttpStatus.OK);
        } catch (InterruptedException | NotConnectedException | UnsupportedFeatureException |
                 OccurenceConstraintException | ExecutionException e) {
            throw new Exception();
        }
    }

    @GetMapping("/getSession")
    public ResponseEntity<Object> getSession(){
        return new ResponseEntity<>(ServerEventConfig.listConnection,HttpStatus.ACCEPTED);
    }

}
