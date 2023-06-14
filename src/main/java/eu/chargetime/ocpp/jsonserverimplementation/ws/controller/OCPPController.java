package eu.chargetime.ocpp.jsonserverimplementation.ws.controller;

import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.jsonserverimplementation.config.server.ServerEventConfig;
import eu.chargetime.ocpp.jsonserverimplementation.ws.sendrequest.SendRequest;

import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;

import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
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
            return new ResponseEntity<>(sendRequest.sendChangeAvail(params,indexSession),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Charge box is not recognized!",HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = CANCEL_RESERVATION, method = RequestMethod.POST)
    public ResponseEntity<Object> cancelReservation(@ModelAttribute ClearCacheRequest params, @RequestParam String chargeBoxId) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        try{
            UUID indexSession = ServerEventConfig.listConnection.get(chargeBoxId);
            return  new ResponseEntity<>(sendRequest.sendClearCache(params,indexSession),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Charge box is not recognized!",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getSession")
    public ResponseEntity<Object> getSession(){
        return new ResponseEntity<>(ServerEventConfig.listConnection,HttpStatus.ACCEPTED);
    }

}
