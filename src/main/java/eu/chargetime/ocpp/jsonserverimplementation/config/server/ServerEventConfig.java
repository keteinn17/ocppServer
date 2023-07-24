package eu.chargetime.ocpp.jsonserverimplementation.config.server;

import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


@Configuration
@Getter
@Slf4j
public class ServerEventConfig {
    @Autowired private JSONServer server;
    public static Map<String,UUID> listConnection=new HashMap<>();
    @Bean
    public ServerEvents createServerCoreImpl() {
        return getNewServerEventsImpl();
    }

    private ServerEvents getNewServerEventsImpl() {
        return new ServerEvents() {

            @Override
            public void newSession(UUID sessionIndex, SessionInformation information) {

                // sessionIndex is used to send messages.
                System.out.println("New session " + sessionIndex + ": " + information.getIdentifier());
                String chargeBox = information.getIdentifier().substring("/steve/websocket/CentralSystemService/".length());
                System.out.println(information.getAddress());
                if(!listConnection.containsKey(chargeBox)){
                    listConnection.put(chargeBox,sessionIndex);
                }else{
                    lostSession(listConnection.get(chargeBox));
                    listConnection.remove(chargeBox);
                    listConnection.put(chargeBox,sessionIndex);
                }
                DatabaseConfiguration.CONFIG.getChargerBox().setChargeBox(chargeBox);
            }

            @Override
            public void lostSession(UUID sessionIndex) {

                System.out.println("Session " + sessionIndex + " lost connection");
                listConnection.remove(sessionIndex);
            }
        };
    }
}
