package eu.chargetime.ocpp.jsonserverimplementation.config;

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@Getter
@Slf4j
public class ServerEventConfig {

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
                System.out.println(chargeBox);
                DatabaseConfiguration.CONFIG.getChargerBox().setChargeBox(chargeBox);
            }

            @Override
            public void lostSession(UUID sessionIndex) {

                System.out.println("Session " + sessionIndex + " lost connection");
            }
        };
    }
}
