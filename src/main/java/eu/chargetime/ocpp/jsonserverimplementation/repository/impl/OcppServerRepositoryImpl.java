package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
import eu.chargetime.ocpp.jsonserverimplementation.utils.ConnectionUtil;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

import static eu.chargetime.ocpp.jsonserverimplementation.db.tables.ChargeBox.CHARGE_BOX;


@Service
@Component
public class OcppServerRepositoryImpl implements OcppServerRepository {

    private DSLContext ctx;

    @Autowired
    public OcppServerRepositoryImpl(DSLContext dslContext){
        ctx=dslContext;
    }
    @Override
    public void updateChargeboxHeartbeat(String chargeBoxIdentity, DateTime ts) throws SQLException {
        ctx= ConnectionUtil.getConnection();
        ctx.update(CHARGE_BOX)
                .set(CHARGE_BOX.LAST_HEARTBEAT_TIMESTAMP, ts)
                .where(CHARGE_BOX.CHARGE_BOX_ID.equal(chargeBoxIdentity))
                .execute();
    }

    public Integer getChargeBoxId(String chargeBoxId) throws SQLException {
        ctx=ConnectionUtil.getConnection();
        Integer res = Integer.valueOf(String.valueOf(ctx.select(CHARGE_BOX.CHARGE_BOX_ID)
                .from(CHARGE_BOX)
                .where(CHARGE_BOX.CHARGE_BOX_ID.equal(chargeBoxId))
                .execute()));
        return res;
    }
}
