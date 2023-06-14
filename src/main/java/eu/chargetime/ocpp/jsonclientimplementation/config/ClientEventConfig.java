package eu.chargetime.ocpp.jsonclientimplementation.config;

import eu.chargetime.ocpp.ClientEvents;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Slf4j
public class ClientEventConfig {
    @Bean
    public ClientEvents createServerCoreImpl(){
        return getNewClientEventsImpl();
    }

    private ClientEvents getNewClientEventsImpl(){
        return new ClientEvents() {
            @Override
            public void connectionOpened() {
                System.out.println("Connected");
            }

            @Override
            public void connectionClosed() {
                System.out.println("Disconnected");
            }
        };
    }
}
