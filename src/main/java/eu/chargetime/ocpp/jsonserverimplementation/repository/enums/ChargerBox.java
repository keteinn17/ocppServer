package eu.chargetime.ocpp.jsonserverimplementation.repository.enums;

import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import eu.chargetime.ocpp.jsonserverimplementation.config.server.ServerEventConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
public enum ChargerBox {
    CONFIG;
    private final ChargeBox chargeBox;

    ChargerBox(){
        ServerEventConfig serverEventConfig=new ServerEventConfig();
        chargeBox=null;
    }
    @Setter
    @Getter
    public static class ChargeBox{
        private List<String> chargeBox;
    }
}
