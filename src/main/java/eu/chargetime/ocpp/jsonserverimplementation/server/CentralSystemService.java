package eu.chargetime.ocpp.jsonserverimplementation.server;

import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.model.core.HeartbeatConfirmation;
import eu.chargetime.ocpp.model.core.HeartbeatRequest;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class CentralSystemService {
    private OcppServerRepository ocppServerRepository;
    public CentralSystemService(OcppServerRepository ocppServerRepository){
        this.ocppServerRepository=ocppServerRepository;
    }
    public HeartbeatConfirmation heartbeat(HeartbeatRequest request,String chargeBox) throws SQLException {
        DateTime dateTime=DateTime.now();
        ZonedDateTime zonedDateTime=ZonedDateTime.now();
        ocppServerRepository.updateChargeboxHeartbeat(chargeBox,dateTime);
        return new HeartbeatConfirmation(zonedDateTime);
    }
}
