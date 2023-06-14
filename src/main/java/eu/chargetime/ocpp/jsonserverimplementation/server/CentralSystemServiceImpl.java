package eu.chargetime.ocpp.jsonserverimplementation.server;

import eu.chargetime.ocpp.jsonserverimplementation.db.enums.TransactionStopEventActor;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertTransactionParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.UpdateTransactionParams;
import eu.chargetime.ocpp.jsonserverimplementation.server.service.CentralSystemService;
import eu.chargetime.ocpp.model.core.*;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class CentralSystemServiceImpl implements CentralSystemService {
    private OcppServerRepository ocppServerRepository;
    public CentralSystemServiceImpl(OcppServerRepository ocppServerRepository){
        this.ocppServerRepository=ocppServerRepository;
    }
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

    public static DateTime convert(ZonedDateTime zdt){
        return new DateTime(zdt.toInstant().toEpochMilli(),DateTimeZone.UTC);
    }
}
