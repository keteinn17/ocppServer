package eu.chargetime.ocpp.jsonserverimplementation.repository;

public interface SettingRepository {
    int getHeartbeatIntervalInSeconds();
    int getHoursToExpire();
}
