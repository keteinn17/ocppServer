package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.SettingsRecord;
import eu.chargetime.ocpp.jsonserverimplementation.repository.SettingRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.SETTINGS;

@Repository
public class SettingRepositoryImpl implements SettingRepository {
    @Autowired
    private DSLContext ctx;

    private static final String APP_ID = new String(
            Base64.getEncoder().encode("SteckdosenVerwaltung".getBytes(StandardCharsets.UTF_8)),
            StandardCharsets.UTF_8
    );
    @Override
    public int getHeartbeatIntervalInSeconds() {
        return getInternal().getHeartbeatIntervalInSeconds();
    }

    @Override
    public int getHoursToExpire() {
        return getInternal().getHoursToExpire();
    }

    private SettingsRecord getInternal() {
        return ctx.selectFrom(SETTINGS)
                .where(SETTINGS.APP_ID.eq(APP_ID))
                .fetchOne();
    }
}
