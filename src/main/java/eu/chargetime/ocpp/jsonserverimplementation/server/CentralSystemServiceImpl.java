package eu.chargetime.ocpp.jsonserverimplementation.server;

import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.ocpp.OcppProtocol;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.SettingRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertConnectorStatusParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertTransactionParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.UpdateChargeBoxParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.UpdateTransactionParams;
import eu.chargetime.ocpp.jsonserverimplementation.server.service.CentralSystemService;
import eu.chargetime.ocpp.jsonserverimplementation.service.OcppStationStatusFailure;
import eu.chargetime.ocpp.model.core.*;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
public class CentralSystemServiceImpl implements CentralSystemService {
    @Autowired private OcppServerRepository ocppServerRepository;
    @Autowired private ApplicationEventPublisher applicationEventPublisher;
    @Autowired private SettingRepository settingRepository;
/*    public CentralSystemServiceImpl(OcppServerRepository ocppServerRepository){
        this.ocppServerRepository=ocppServerRepository;
    }*/
    @Override
    public HeartbeatConfirmation heartbeat(HeartbeatRequest request,String chargeBox) throws SQLException {
        DateTime dateTime=DateTime.now();
        ZonedDateTime zonedDateTime=ZonedDateTime.now();
        ocppServerRepository.updateChargeboxHeartbeat(chargeBox,dateTime);
        return new HeartbeatConfirmation(zonedDateTime);
    }

    @Override
    public StartTransactionConfirmation startTransaction(StartTransactionRequest request, String chargeBox) throws Exception {

        InsertTransactionParams params =
                InsertTransactionParams.builder()
                        .chargeBoxId(chargeBox)
                        .connectorId(request.getConnectorId())
                        .idTag(request.getIdTag())
                        .startTimestamp(convert(request.getTimestamp()))
                        .startMeterValue(String.valueOf(request.getMeterStart()))
                        .reservationId(request.getReservationId())
                        .eventTimestamp(DateTime.now())
                        .build();
        int transactionId = ocppServerRepository.insertTransaction(params);
        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted),transactionId);
    }

    public StopTransactionConfirmation stopTransaction(StopTransactionRequest request,String chargeBox){
        int transactionId = request.getTransactionId();
        String stopReason = request.getReason() != null ? String.valueOf(request.getReason()) : null;
        IdTagInfo idTag = new IdTagInfo(AuthorizationStatus.Accepted);
        idTag.setExpiryDate(ZonedDateTime.now());

        UpdateTransactionParams params=
                UpdateTransactionParams.builder()
                        .chargeBoxId(chargeBox)
                        .transactionId(transactionId)
                        .stopTimestamp(convert(request.getTimestamp()))
                        .stopMeterValue(String.valueOf(request.getMeterStop()))
                        .stopReason(stopReason)
                        .eventTimestamp(DateTime.now())
                        .eventActor(TransactionStopEventActor.station)
                        .build();

        ocppServerRepository.updateTransaction(params);
        ocppServerRepository.insertMeterValues(chargeBox,request.getTransactionData(),transactionId);
        StopTransactionConfirmation confirmation=new StopTransactionConfirmation();
        confirmation.setIdTagInfo(idTag);
        return confirmation;
    }

    @Override
    public BootNotificationConfirmation bootNotification(BootNotificationRequest request, String chargeBox) {
        log.info("The boot of the chargebox '{}' with registration status '{}' is acknowledged.", chargeBox);
        ZonedDateTime now = ZonedDateTime.now();
        UpdateChargeBoxParams params =
                UpdateChargeBoxParams.builder()
                        .ocppProtocol(OcppProtocol.V_16_JSON)
                        .vendor(request.getChargePointVendor())
                        .model(request.getChargePointModel())
                        .pointSerial(request.getChargePointSerialNumber())
                        .boxSerial(request.getChargeBoxSerialNumber())
                        .fwVersion(request.getFirmwareVersion())
                        .iccid(request.getIccid())
                        .imsi(request.getImsi())
                        .meterType(request.getMeterType())
                        .meterSerial(request.getMeterSerialNumber())
                        .chargeBoxId(chargeBox)
                        .heartbeatTimestamp(convert(now))
                        .build();
        ocppServerRepository.updateChargebox(params);
        return new BootNotificationConfirmation(now, settingRepository.getHeartbeatIntervalInSeconds(), RegistrationStatus.Accepted);
    }

    @Override
    public StatusNotificationConfirmation statusConfirmation(StatusNotificationRequest request, String chargeBox) {
        ZonedDateTime timestamp=request.getTimestamp() != null ? request.getTimestamp(): ZonedDateTime.now();
        InsertConnectorStatusParams params=
                InsertConnectorStatusParams.builder()
                        .chargeBoxId(chargeBox)
                        .connectorId(request.getConnectorId())
                        .status(request.getStatus().toString())
                        .errorCode(request.getErrorCode().toString())
                        .timestamp(convert(timestamp))
                        .errorInfo(request.getInfo())
                        .vendorId(request.getVendorId())
                        .vendorErrorCode(request.getVendorErrorCode())
                        .build();
        if(request.getStatus() == ChargePointStatus.Faulted){
            applicationEventPublisher.publishEvent(new OcppStationStatusFailure(
                    chargeBox, request.getConnectorId(), request.getErrorCode().toString()));
        }
        ocppServerRepository.insertConnectorStatus(params);
        return new StatusNotificationConfirmation();
    }

    @Override
    public MeterValuesConfirmation meterValue(MeterValuesRequest request, String chargeBox) {
        ocppServerRepository.insertMeterValues(
                chargeBox,
                List.of(request.getMeterValue()),
                request.getConnectorId(),
                request.getTransactionId()
        );

        return new MeterValuesConfirmation();
    }

    public static DateTime convert(ZonedDateTime zdt){
        return new DateTime(zdt.toInstant().toEpochMilli(),DateTimeZone.UTC);
    }
}
