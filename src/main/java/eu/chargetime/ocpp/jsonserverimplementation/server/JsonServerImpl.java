package eu.chargetime.ocpp.jsonserverimplementation.server;

import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.jsonserverimplementation.config.ApplicationConfiguration;
//import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@AllArgsConstructor
public class JsonServerImpl {

    private final ServerEvents serverEvents;
    private final JSONServer server;
    private final ApplicationConfiguration applicationConfiguration;

    @PostConstruct
    public void startServer() throws Exception {
        System.out.println("Server open: "+"localhost:"+ applicationConfiguration.getServerPort());
//        server.open("localhost", 7070, serverEvents);
        server.open("0.0.0.0", 7070, serverEvents);
    }
}
