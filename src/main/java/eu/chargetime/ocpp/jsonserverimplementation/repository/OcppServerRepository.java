package eu.chargetime.ocpp.jsonserverimplementation.repository;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.ZonedDateTime;

@Service
public interface OcppServerRepository {
    void updateChargeboxHeartbeat(String chargeBoxIdentity, DateTime ts) throws SQLException;
    public Integer getChargeBoxId(String chargeBoxId) throws SQLException ;
}
