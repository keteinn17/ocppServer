package eu.chargetime.ocpp.jsonclientimplementation.ocppClient;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.jsonclientimplementation.config.ApiConfigurations;
import eu.chargetime.ocpp.model.core.*;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@RestController
@Slf4j
@RequestMapping(value="/Client")
@Import({JsonClientConfig.class, ClientCoreProfileConfig.class, ClientCoreEventHandlerConfig.class,
        ApiConfigurations.class})
public class ClientService {
    @Autowired
    private JSONClient jsonClient;

    @Autowired
    private ClientCoreProfile clientCoreProfile;

    @Autowired
    private ApiConfigurations apiConfigurations;


    @GetMapping(path = "/Authorize")
    public ResponseEntity<Object> Authorize(
            @RequestParam("IdTag") String IdTag
    ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl()+"/"+apiConfigurations.getChargeBoxId();

        AuthorizeRequest testRequest = clientCoreProfile.createAuthorizeRequest(IdTag);
        jsonClient.connect(url, null);
        try {
            AuthorizeConfirmation authorizeConfirmation = (AuthorizeConfirmation) jsonClient.send(testRequest)
                    .toCompletableFuture().get();
            if(authorizeConfirmation.getIdTagInfo().getStatus() == AuthorizationStatus.Accepted){
                resInf.put("Message","Authorize Success");
            }else{
                resInf.put("Message","Authorize Fail");
            }
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
//        resInf.put("Message","Authorize Success");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping(path = "/StartTransaction")
    public ResponseEntity<Object> StartTransaction(
            @ModelAttribute StartTransactionRequest req
//            @RequestParam("connectorId") Integer connectorId,
//            @RequestParam("IdTag") String IdTag
    )throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl()+"/"+apiConfigurations.getChargeBoxId();
//        int meterStart = 2;
//        ZonedDateTime timestamp = ZonedDateTime.now();
        req.setTimestamp(ZonedDateTime.now());
        //StartTransactionRequest request = clientCoreProfile.createStartTransactionRequest(connectorId, IdTag, meterStart, timestamp);
        jsonClient.connect(url, null);
        try {
            StartTransactionConfirmation confirmation = (StartTransactionConfirmation) jsonClient.send(req)
                    .toCompletableFuture().get();
            resInf.put("info",confirmation.getIdTagInfo().toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
//        resInf.put("Message","Authorize Success");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/StopTransaction")
    public ResponseEntity<Object> StopTransaction(
            @ModelAttribute StopTransactionRequest req
//            @RequestParam("stopValue") Integer stopValue,
//            @RequestParam("transactionId") Integer transactionId
    ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl()+"/"+apiConfigurations.getChargeBoxId();
        ZonedDateTime timestamp = ZonedDateTime.now();
        req.setTimestamp(timestamp);
//        StopTransactionRequest request = clientCoreProfile.createStopTransactionRequest(stopValue, timestamp, transactionId);
//        request.setReason(Reason.PowerLoss);

        jsonClient.connect(url, null);
        try {
            StopTransactionConfirmation confirmation = (StopTransactionConfirmation) jsonClient.send(req)
                    .toCompletableFuture().get();
            resInf.put("info",confirmation.getIdTagInfo().toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        //resInf.put("Message","Stop Transaction Success");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/StatusNotification")
    public ResponseEntity<Object> StatusNotification(
            @ModelAttribute StatusNotificationRequest request
    ) throws Exception {
        Map<String, Object> reqMap = new HashMap<>();
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + apiConfigurations.getChargeBoxId();

        ChargePointErrorCode errorCode = ChargePointErrorCode.NoError;
        ChargePointStatus status = ChargePointStatus.Charging;

        //StatusNotificationRequest request = clientCoreProfile.createStatusNotificationRequest(connectorId, errorCode, status);
        jsonClient.connect(url, null);
        try {
            StatusNotificationConfirmation confirmation = (StatusNotificationConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            resInf.put("Message", confirmation.toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(path = "/HeartBeat")
    public ResponseEntity<Object> HeartBeat()
            throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + apiConfigurations.getChargeBoxId();

        HeartbeatRequest request = clientCoreProfile.createHeartbeatRequest();
        jsonClient.connect(url, null);
        try {
            HeartbeatConfirmation confirmation = (HeartbeatConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            resInf.put("Message", confirmation.getCurrentTime());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/BootNotification")
    public ResponseEntity<Object> BootNotification(@ModelAttribute BootNotificationRequest request)
            throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + apiConfigurations.getChargeBoxId();

/*        String vendor = "Dasvision Vendor";
        String model = "TruongVD";

        BootNotificationRequest request = clientCoreProfile.createBootNotificationRequest(vendor, model);
        request.setImsi("IMSI Dasvision");
        request.setIccid("ICCID Dasvision");
        request.setFirmwareVersion("Version 1.0 for 1.6 Dasvision");
        request.setMeterType("MeterType Dasvision");
        request.setChargePointSerialNumber("#CP Serial Dasvision");
        request.setMeterSerialNumber("#Meter Serial Dasvision");*/

        jsonClient.connect(url, null);
        try {
            BootNotificationConfirmation confirmation = (BootNotificationConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            resInf.put("Status", confirmation.getStatus());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path = "/MeterValues")
    public ResponseEntity<Object> MeterValues()
            throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + apiConfigurations.getChargeBoxId();

        int connectorId = 7;
        SampledValue[] sampledValue = new SampledValue[1];
        sampledValue[0] = new SampledValue("1000");
        sampledValue[0].setUnit("W");
        sampledValue[0].setLocation(Location.Body);
        sampledValue[0].setMeasurand("Frequency");

        MeterValue meterValue = new MeterValue(ZonedDateTime.now(), sampledValue);

        MeterValuesRequest request = clientCoreProfile.createMeterValuesRequest(connectorId, meterValue);

        jsonClient.connect(url, null);
        try {
            MeterValuesConfirmation confirmation = (MeterValuesConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            resInf.put("Message", confirmation.toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PostMapping(path = "/DataTransfer")
    public ResponseEntity<Object> DataTransfer(
            @RequestParam("vendor") String vendor,
            @RequestParam(name = "MessageID", required = false) String messageID,
            @RequestParam(name = "Data", required = false) String data
    ) throws Exception {
        Map<String, Object> resInf = new HashMap<>();
        JSONObject res;
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + apiConfigurations.getChargeBoxId();

        DataTransferRequest request = clientCoreProfile.createDataTransferRequest(vendor);
        if(messageID != null && data != null){
            request.setMessageId(messageID);
            request.setData(data);
        }
        jsonClient.connect(url, null);
        try {
            DataTransferConfirmation confirmation = (DataTransferConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            resInf.put("Message", confirmation.toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                 | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
        }
        log.info("==============================");
        res = new JSONObject(resInf);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
