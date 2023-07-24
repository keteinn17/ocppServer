package eu.chargetime.ocpp.jsonserverimplementation.web.controller;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.jsonserverimplementation.config.server.ServerEventConfig;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.Error;
import eu.chargetime.ocpp.jsonserverimplementation.ws.sendrequest.SendRequest;

import eu.chargetime.ocpp.model.core.*;

import eu.chargetime.ocpp.model.reservation.*;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileConfirmation;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileRequest;
import eu.chargetime.ocpp.model.smartcharging.ClearChargingProfileStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/manager/operations/v1.6")
public class OCPPController {

    @Autowired
    private final JSONServer server;
    @Autowired
    private ServerCoreProfile serverCoreProfile;
    @Autowired
    private OcppServerRepository ocppServerRepository;
    @Autowired
    public OCPPController(JSONServer jsonServer){
        this.server=jsonServer;
    }
    @Autowired private SendRequest sendRequest;
    protected String getPrefix() {
        return "op16";
    }
    @PostMapping(path = "/ChangeAvailability")
    public ResponseEntity<Object> ChangeAvailability(
            @RequestParam("chargerBoxId") List<String> chargerBoxId,
            @RequestParam("availabilityType") AvailabilityType availabilityType,
            @RequestParam("connectorId") Integer connectorId
    ) throws Exception {
        ChangeAvailabilityRequest changeAvailabilityRequest = serverCoreProfile.createChangeAvailabilityRequest(availabilityType, connectorId);
        ChangeAvailabilityConfirmation changeAvailabilityConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            changeAvailabilityConfirmation = (ChangeAvailabilityConfirmation) server.send(indexSession, changeAvailabilityRequest).toCompletableFuture().get();
            log.info(String.valueOf(changeAvailabilityConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(changeAvailabilityConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/CancelReservation")
    public ResponseEntity<Object> CancelReservation(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "reservationId", required = false) Integer reservationId
    ) throws Exception {
        CancelReservationRequest cancelReservationRequest = new CancelReservationRequest();
        cancelReservationRequest.setReservationId(reservationId);
        CancelReservationConfirmation cancelReservationConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            cancelReservationConfirmation = (CancelReservationConfirmation) server.send(indexSession, cancelReservationRequest).toCompletableFuture().get();
            log.info(String.valueOf(cancelReservationConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        if (cancelReservationConfirmation.getStatus() == CancelReservationStatus.Accepted) {
            ocppServerRepository.deleteReservation(reservationId);
        }
        return new ResponseEntity<>(cancelReservationConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/ChangeConfiguration")
    public ResponseEntity<Object> ChangeConfiguration(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "key") String key,
            @RequestParam(value = "value") String value
    ) throws Exception {

        ChangeConfigurationRequest changeConfigurationRequest = serverCoreProfile.createChangeConfigurationRequest(key, value);

        ChangeConfigurationConfirmation changeConfigurationConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            changeConfigurationConfirmation = (ChangeConfigurationConfirmation) server.send(indexSession, changeConfigurationRequest).toCompletableFuture().get();
            log.info(String.valueOf(changeConfigurationConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(changeConfigurationConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/ClearCache")
    public ResponseEntity<Object> ClearCache(
            @RequestParam("chargerBoxId") String chargerBoxId
    ) throws Exception {

        ClearCacheRequest clearCacheRequest = serverCoreProfile.createClearCacheRequest();

        ClearCacheConfirmation clearCacheConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            clearCacheConfirmation = (ClearCacheConfirmation) server.send(indexSession, clearCacheRequest).toCompletableFuture().get();
            log.info(String.valueOf(clearCacheConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(clearCacheConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/ClearChargingProfile")
    public ResponseEntity<Object> ClearChargingProfile(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "connectorId", required = false) Integer connectorId,
            @RequestParam(value = "chargingProfilePurpose", required = false, defaultValue = "TxDefaultProfile") ChargingProfilePurposeType chargingProfilePurpose,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "stackLevel", required = false) Integer stackLevel
    ) throws Exception {

        ClearChargingProfileRequest clearCacheRequest = new ClearChargingProfileRequest();
        clearCacheRequest.setChargingProfilePurpose(chargingProfilePurpose);
        clearCacheRequest.setConnectorId(connectorId);
        clearCacheRequest.setId(id);
        clearCacheRequest.setStackLevel(stackLevel);
        ClearChargingProfileConfirmation clearChargingProfileConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            clearChargingProfileConfirmation = (ClearChargingProfileConfirmation) server.send(indexSession, clearCacheRequest).toCompletableFuture().get();
            log.info(String.valueOf(clearChargingProfileConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (clearChargingProfileConfirmation.getStatus() == ClearChargingProfileStatus.Accepted) {
            ocppServerRepository.clearChargingProfile(connectorId, id);
        }
        log.info("==============================");
        return new ResponseEntity<>(clearChargingProfileConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/DataTransfer")
    public ResponseEntity<Object> DataTransfer(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "vendorId") String vendorId,
            @RequestParam(value = "messageId", required = false, defaultValue = "") String messageId,
            @RequestParam(value = "data", required = false, defaultValue = "") String data
    ) throws Exception {

        DataTransferRequest dataTransferRequest = serverCoreProfile.createDataTransferRequest(vendorId);
        dataTransferRequest.setMessageId(messageId);
        dataTransferRequest.setData(data);

        DataTransferConfirmation dataTransferConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            dataTransferConfirmation = (DataTransferConfirmation) server.send(indexSession, dataTransferRequest).toCompletableFuture().get();
            log.info(String.valueOf(dataTransferConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(dataTransferConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/GetConfiguration")
    public ResponseEntity<Object> GetConfiguration(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "keys", required = false, defaultValue = "") String[] key
    ) throws Exception {

        GetConfigurationRequest getConfigurationRequest = serverCoreProfile.createGetConfigurationRequest();
        getConfigurationRequest.setKey(key);

        GetConfigurationConfirmation getConfigurationConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            getConfigurationConfirmation = (GetConfigurationConfirmation) server.send(indexSession, getConfigurationRequest).toCompletableFuture().get();
            log.info(String.valueOf(getConfigurationConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(getConfigurationConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/RemoteStartTransaction")
    public ResponseEntity<Object> RemoteStartTransaction(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "idTag") String idTag,
            @RequestParam(value = "connectorId", required = false) Integer connectorId,
            @RequestParam(value = "chargingProfile", required = false) ChargingProfile chargingProfile
    ) throws Exception {

        RemoteStartTransactionRequest remoteStartTransactionRequest = serverCoreProfile.createRemoteStartTransactionRequest(idTag);
        remoteStartTransactionRequest.setConnectorId(connectorId);
        if (chargingProfile != null) {
            remoteStartTransactionRequest.setChargingProfile(chargingProfile);
        }
        RemoteStartTransactionConfirmation remoteStartTransactionConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            remoteStartTransactionConfirmation = (RemoteStartTransactionConfirmation) server.send(indexSession, remoteStartTransactionRequest).toCompletableFuture().get();
            log.info(String.valueOf(remoteStartTransactionConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");

        return new ResponseEntity<>(remoteStartTransactionConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/RemoteStopTransaction")
    public ResponseEntity<Object> RemoteStopTransaction(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "transactionId") Integer transactionId
    ) throws Exception {

        RemoteStopTransactionRequest remoteStopTransactionRequest = serverCoreProfile.createRemoteStopTransactionRequest(transactionId);
        remoteStopTransactionRequest.setTransactionId(transactionId);

        RemoteStopTransactionConfirmation remoteStopTransactionConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            remoteStopTransactionConfirmation = (RemoteStopTransactionConfirmation) server.send(indexSession, remoteStopTransactionRequest).toCompletableFuture().get();
            log.info(String.valueOf(remoteStopTransactionConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(remoteStopTransactionConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/ReserveNow")
    public ResponseEntity<Object> ReserveNow(
            @RequestParam("connectorId") Integer connectorId,
            @RequestParam("expiryDate") ZonedDateTime expiryDate,
            @RequestParam("idTag") String idTag,
            @RequestParam(value = "parentIdTag", required = false, defaultValue = "") String parentIdTag,
            @RequestParam("reservationId") Integer reservationId,
            @RequestParam("chargerBoxId") String chargerBoxId
    ) throws Exception {

        ReserveNowRequest reserveNowRequest = new ReserveNowRequest();
        reserveNowRequest.setReservationId(reservationId);
        reserveNowRequest.setConnectorId(connectorId);
        reserveNowRequest.setExpiryDate(expiryDate);
        reserveNowRequest.setIdTag(idTag);
        reserveNowRequest.setParentIdTag(parentIdTag);

        ReserveNowConfirmation reserveNowConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            reserveNowConfirmation = (ReserveNowConfirmation) server.send(indexSession, reserveNowRequest).toCompletableFuture().get();
            log.info(String.valueOf(reserveNowConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        ocppServerRepository.insertReservation(connectorId, expiryDate, idTag, reservationId, reserveNowConfirmation.getStatus());
        log.info("==============================");
        return new ResponseEntity<>(reserveNowConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/Reset")
    public ResponseEntity<Object> Reset(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "resetType") ResetType resetType
    ) throws Exception {

        ResetRequest resetRequest = serverCoreProfile.createResetRequest(resetType);

        ResetConfirmation resetConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            resetConfirmation = (ResetConfirmation) server.send(indexSession, resetRequest).toCompletableFuture().get();
            log.info(String.valueOf(resetConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(resetConfirmation, HttpStatus.OK);
    }

    @PostMapping(path = "/UnlockConnector")
    public ResponseEntity<Object> UnlockConnector(
            @RequestParam("chargerBoxId") String chargerBoxId,
            @RequestParam(value = "connectorId") Integer connectorId
    ) throws Exception {

        UnlockConnectorRequest unlockConnectorRequest = serverCoreProfile.createUnlockConnectorRequest(connectorId);

        UnlockConnectorConfirmation unlockConnectorConfirmation = null;
        try {
            UUID indexSession = ServerEventConfig.listConnection.get(chargerBoxId);
            if (indexSession == null) {
                return new ResponseEntity<>(new Error("This Charger Box Not Exists Or Not Powered Up"), HttpStatus.UNPROCESSABLE_ENTITY);
            }
            unlockConnectorConfirmation = (UnlockConnectorConfirmation) server.send(indexSession, unlockConnectorRequest).toCompletableFuture().get();
            log.info(String.valueOf(unlockConnectorConfirmation));
        }
        catch (UnsupportedFeatureException e) {
            return new ResponseEntity<>(new Error("This Feature Is Not Supported In The Client"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (OccurenceConstraintException | ExecutionException | InterruptedException e) {
            return new ResponseEntity<>(new Error("Something Went Wrong"), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        log.info("==============================");
        return new ResponseEntity<>(unlockConnectorConfirmation, HttpStatus.OK);
    }


    @RequestMapping(value = "/")

    @GetMapping("/getSession")
    public ResponseEntity<Object> getSession(){
        return new ResponseEntity<>(ServerEventConfig.listConnection,HttpStatus.ACCEPTED);
    }

}
