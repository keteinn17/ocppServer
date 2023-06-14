package eu.chargetime.ocpp.jsonclientimplementation.ocpphandler;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.jsonclientimplementation.config.ApiConfigurations;
import eu.chargetime.ocpp.model.core.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@Import({JsonClientConfig.class, ClientCoreProfileConfig.class, ClientCoreEventHandlerConfig.class,
        ApiConfigurations.class})
public class OCPPStatusNotificationHandlerTest {

    @Autowired
    private JSONClient jsonClient;

    @Autowired
    private ClientCoreProfile clientCoreProfile;

    @Autowired
    private ApiConfigurations apiConfigurations;

    @Value("${chargebox.id}")
    private String chargeBoxId;

    @Test
    public void testOCPPStatusNotificationHandler() {
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + chargeBoxId;
        int connectorId = 10;
        ChargePointErrorCode errorCode = ChargePointErrorCode.EVCommunicationError;
        ChargePointStatus status = ChargePointStatus.Faulted;

        StatusNotificationRequest request = clientCoreProfile.createStatusNotificationRequest(connectorId, errorCode, status);
        request.setInfo("No error. Continue charging.");

        jsonClient.connect(url, null);
        try {
            StatusNotificationConfirmation confirmation = (StatusNotificationConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            assertTrue(true);
            System.out.println("Response: " + confirmation.toString());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
            fail();
        }
    }
}
