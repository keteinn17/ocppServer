package eu.chargetime.ocpp.jsonserverimplementation.ws.sendrequest;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityConfirmation;
import eu.chargetime.ocpp.model.core.ChangeAvailabilityRequest;
import eu.chargetime.ocpp.model.core.ClearCacheConfirmation;
import eu.chargetime.ocpp.model.core.ClearCacheRequest;
import eu.chargetime.ocpp.model.reservation.CancelReservationConfirmation;
import eu.chargetime.ocpp.model.reservation.CancelReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class SendRequestImpl implements SendRequest{
    @Autowired
    private final JSONServer server;
    @Autowired
    public SendRequestImpl(JSONServer jsonServer){
        this.server=jsonServer;
    }

    public ChangeAvailabilityConfirmation sendChangeAvail(ChangeAvailabilityRequest changeAvailabilityRequest,UUID indexSession) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        ChangeAvailabilityConfirmation confirmation= (ChangeAvailabilityConfirmation) server.send(indexSession,changeAvailabilityRequest).toCompletableFuture().get();
        return confirmation;
    }

    @Override
    public ClearCacheConfirmation sendClearCache(ClearCacheRequest clearCacheRequest, UUID indexSession) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException, ExecutionException, InterruptedException {
        ClearCacheConfirmation confirmation = (ClearCacheConfirmation) server.send(indexSession,clearCacheRequest).toCompletableFuture().get();
        return confirmation;
    }
}
