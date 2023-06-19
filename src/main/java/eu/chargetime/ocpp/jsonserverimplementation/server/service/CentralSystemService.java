package eu.chargetime.ocpp.jsonserverimplementation.server.service;

import eu.chargetime.ocpp.model.core.*;

import java.sql.SQLException;

public interface CentralSystemService {
    HeartbeatConfirmation heartbeat(HeartbeatRequest request, String chargeBox) throws SQLException;
    StartTransactionConfirmation startTransaction(StartTransactionRequest request, String chargeBox) throws Exception;
    StopTransactionConfirmation stopTransaction(StopTransactionRequest request,String chargeBox);
    BootNotificationConfirmation bootNotification(BootNotificationRequest request,String chargeBox);
    StatusNotificationConfirmation statusConfirmation(StatusNotificationRequest request, String chargeBox);
}
