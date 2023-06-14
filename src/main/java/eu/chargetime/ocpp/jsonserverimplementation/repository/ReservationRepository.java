package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.InsertReservationParams;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.Reservation;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.ReservationQueryForm;
import org.jooq.Record1;
import org.jooq.Select;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> getReservations(ReservationQueryForm form) throws Exception;

    List<Integer> getActiveReservationIds(String chargeBoxId);

    /**
     * Returns the id of the reservation, if the reservation is inserted.
     */
    int insert(InsertReservationParams params);

    /**
     * Deletes the temporarily inserted reservation, when
     * 1) the charging station does not accept the reservation,
     * 2) there is a technical problem (communication failure etc.) with the charging station,
     */
    void delete(int reservationId);

    void accepted(int reservationId);
    void cancelled(int reservationId);
    void used(Select<Record1<Integer>> connectorPkSelect, String ocppIdTag, int reservationId, int transactionId);
}
