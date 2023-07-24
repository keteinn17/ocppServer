package eu.chargetime.ocpp.jsonserverimplementation.web.controller;

import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.jsonserverimplementation.config.DatabaseConfiguration;
import eu.chargetime.ocpp.jsonserverimplementation.server.service.CentralSystemService;
import eu.chargetime.ocpp.model.core.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.UUID;

@Configuration
@Getter
@Slf4j
@NoArgsConstructor

public class ServerCoreProfileConfig {

    @Autowired
    private CentralSystemService centralSystemService;
    @Autowired
    public ServerCoreProfileConfig(CentralSystemService centralSystemService){
        this.centralSystemService=centralSystemService;
    }

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
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                BootNotificationConfirmation confirmation = centralSystemService.bootNotification(request,chargeBox);
                return confirmation;
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(UUID sessionIndex, DataTransferRequest request) {

                System.out.println(request);
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                DataTransferConfirmation confirmation=centralSystemService.dataTransfer(request,chargeBox);
                return confirmation; // returning null means unsupported feature
            }

            @Override
            public HeartbeatConfirmation handleHeartbeatRequest(UUID sessionIndex, HeartbeatRequest request) {

                System.out.println(request);
                try {
                    System.out.println(sessionIndex);
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
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                System.out.println(sessionIndex);
                MeterValuesConfirmation confirmation =centralSystemService.meterValue(request,chargeBox);

                return confirmation; // returning null means unsupported feature
            }

            @Override
            public StartTransactionConfirmation handleStartTransactionRequest(UUID sessionIndex, StartTransactionRequest request) {

                System.out.println(request);
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                try {
                    StartTransactionConfirmation confirmation=centralSystemService.startTransaction(request,chargeBox);
                    return confirmation;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public StatusNotificationConfirmation handleStatusNotificationRequest(UUID sessionIndex, StatusNotificationRequest request) {

                System.out.println(request);
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                StatusNotificationConfirmation confirmation = centralSystemService.statusConfirmation(request,chargeBox);

                return confirmation; // returning null means unsupported feature
            }

            @Override
            public StopTransactionConfirmation handleStopTransactionRequest(UUID sessionIndex, StopTransactionRequest request) {

                System.out.println(request);
                String chargeBox = DatabaseConfiguration.CONFIG.getChargerBox().getChargeBox();
                System.out.println(chargeBox);
                StopTransactionConfirmation confirmation = centralSystemService.stopTransaction(request,chargeBox);

                return confirmation; // returning null means unsupported feature
            }
        };
    }

    @Bean
    public ServerCoreProfile createCore(ServerCoreEventHandler serverCoreEventHandler) {
        return new ServerCoreProfile(serverCoreEventHandler);
    }
}
