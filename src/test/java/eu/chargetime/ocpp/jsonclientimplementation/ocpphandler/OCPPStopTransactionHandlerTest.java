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
public class OCPPStopTransactionHandlerTest {

    @Autowired
    private JSONClient jsonClient;

    @Autowired
    private ClientCoreProfile clientCoreProfile;

    @Autowired
    private ApiConfigurations apiConfigurations;

    @Value("${chargebox.id}")
    private String chargeBoxId;

    @Test
    public void testOCPPStartTransactionHandler() {
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + chargeBoxId;
        int connectorId = 10;
        int transactionId = 4;
        ZonedDateTime timestamp = ZonedDateTime.now();

        SampledValue sampledValue = new SampledValue("100");
        sampledValue.setUnit("W");
        MeterValue meterValue = new MeterValue(timestamp, new SampledValue[]{sampledValue});

        StopTransactionRequest request = clientCoreProfile.createStopTransactionRequest(connectorId, timestamp, transactionId);
        request.setReason(Reason.EmergencyStop);
        request.setTransactionData(new MeterValue[]{meterValue});

        jsonClient.connect(url, null);
        try {
            StopTransactionConfirmation confirmation = (StopTransactionConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            assertTrue(true);
            System.out.println("ID Tag Info: " + confirmation.getIdTagInfo());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
            fail();
        }
    }
}
