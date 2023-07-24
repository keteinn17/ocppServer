package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertConnectorStatusParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertTransactionParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.UpdateChargeBoxParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.UpdateTransactionParams;
import eu.chargetime.ocpp.model.core.ChargingProfilePurposeType;
import eu.chargetime.ocpp.model.core.MeterValue;
import eu.chargetime.ocpp.model.reservation.ReservationStatus;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface OcppServerRepository {
    void updateChargeboxHeartbeat(String chargeBoxIdentity, DateTime ts) throws SQLException;
     Integer getChargeBoxId(String chargeBoxId) throws SQLException;
     int insertTransaction(InsertTransactionParams p);
    void updateTransaction(UpdateTransactionParams params);

    void insertMeterValues(String chargeBoxIdentity, MeterValue[] list, int transactionId);
    void updateChargebox(UpdateChargeBoxParams params);
    void insertConnectorStatus(InsertConnectorStatusParams params);
    void insertMeterValues(String chargeBoxIdentity, List<MeterValue> list, int connectorId, Integer transactionId);
    void insertReservation(Integer connectorId, ZonedDateTime expiryDate,
                           String idTag, Integer reservationId, ReservationStatus status);
    void deleteReservation(Integer reservationId);
    void clearChargingProfile(Integer connectorId, Integer chargingProfileId);
}
