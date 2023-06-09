package eu.chargetime.ocpp.jsonserverimplementation.config;

import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.jsonserverimplementation.server.CentralSystemService;
import eu.chargetime.ocpp.model.core.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Configuration
@Getter
@Slf4j
@NoArgsConstructor
/*@ServerEndpoint(value = "/websocket/CentralSystemService/{chargerBox}")
@EnableWebSocket*/
public class ServerCoreProfileConfig {

    private CentralSystemService centralSystemService;
    @Autowired
    public ServerCoreProfileConfig(CentralSystemService centralSystemService){
        this.centralSystemService=centralSystemService;
    }

/*    @OnOpen
    public void onOpen(Session s, @PathParam("chargerBox") String chargeBox){
        System.out.println("WebSocket opened for user " + chargeBox + ": " + s.getId());
    }*/

    @Bean
    public ServerCoreEventHandler getCoreEventHandler() {
        return new ServerCoreEventHandler() {
            @Override
            public AuthorizeConfirmation handleAuthorizeRequest(UUID sessionIndex, AuthorizeRequest request) {

                System.out.println(request);
                // ... handle event
                IdTagInfo idTagInfo = new IdTagInfo();
                idTagInfo.setExpiryDate(ZonedDateTime.now());
                idTagInfo.setParentIdTag("test");
                idTagInfo.setStatus(AuthorizationStatus.Accepted);

                return new AuthorizeConfirmation(idTagInfo);
            }

            @Override
            public BootNotificationConfirmation handleBootNotificationRequest(UUID sessionIndex, BootNotificationRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {

                System.out.println(request);
                try {
                    String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                    System.out.println(chargeBox);
                    centralSystemService.heartbeat(request,chargeBox);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                return new HeartbeatConfirmation(ZonedDateTime.now()); // returning null means unsupported feature
            }

            @Override
            public MeterValuesConfirmation handleMeterValuesRequest(UUID sessionIndex, MeterValuesRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex, StartTransactionRequest request) {

                System.out.println(request);
                // ... handle event

                return new StartTransactionConfirmation(); // returning null means unsupported feature
            }

            @Override
            public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex, StatusNotificationRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }

            @Override
            public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex, StopTransactionRequest request) {

                System.out.println(request);
                // ... handle event

                return null; // returning null means unsupported feature
            }
        };
    }

    @Bean
    public ServerCoreProfile createCore(ServerCoreEventHandler serverCoreEventHandler) {
        return new ServerCoreProfile(serverCoreEventHandler);
    }
}
