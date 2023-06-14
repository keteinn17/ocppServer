package eu.chargetime.ocpp.jsonserverimplementation.ws.sendrequest;

import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityConfirmation;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;
import eu.chargetime.ocpp.model.core.ClearCacheConfirmation;
import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.reservation.CancelReservationConfirmation;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface SendRequest {
    ChangeAvailabilityConfirmation sendChangeAvail(ChangeAvailabilityRequest changeAvailabilityRequest, UUID indexSession) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException;
    ClearCacheConfirmation sendClearCache(ClearCacheRequest cancelReservationRequest, UUID indexSession) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException;
}
