package eu.chargetime.ocpp.jsonclientimplementation.ocpphandler;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.jsonclientimplementation.config.ApiConfigurations;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import eu.chargetime.ocpp.model.core.BootNotificationRequest;
import eu.chargetime.ocpp.model.core.HeartbeatConfirmation;
import eu.chargetime.ocpp.model.core.HeartbeatRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@Import({JsonClientConfig.class, ClientCoreProfileConfig.class, ClientCoreEventHandlerConfig.class,
        ApiConfigurations.class})
public class OCPPBootNotificationHandlerTest {

    @Autowired
    private JSONClient jsonClient;

    @Autowired
    private ClientCoreProfile clientCoreProfile;

    @Autowired
    private ApiConfigurations apiConfigurations;

    @Value("${chargebox.id}")
    private String chargeBoxId;

    @Test
    public void testOCPPBootNotificationHandler() {
        String url = "ws://" + apiConfigurations.getWebSocketBaseUrl() + "/" + chargeBoxId;
        String vendor = "Java Vendor";
        String model = "Java Model";

        BootNotificationRequest request = clientCoreProfile.createBootNotificationRequest(vendor, model);
        request.setChargePointModel("Java CP Model");
        request.setImsi("Java IMSI");
        request.setIccid("Java ICC ID");
        request.setFirmwareVersion("V0.2");
        request.setMeterType("Java Meter");
        request.setChargePointSerialNumber("1234567890");
        request.setChargePointVendor("Jaa CP Vendor");
        request.setMeterSerialNumber("0123456789");

        jsonClient.connect(url, null);
        try {
            BootNotificationConfirmation confirmation = (BootNotificationConfirmation) jsonClient.send(request)
                    .toCompletableFuture().get();
            assertTrue(true);
            System.out.println(confirmation.getCurrentTime() + ": ");
            System.out.println("Status: " + confirmation.getStatus());
            System.out.println("Interval: " + confirmation.getInterval());
        } catch (OccurenceConstraintException | UnsupportedFeatureException
                | ExecutionException | InterruptedException e) {
            log.error("Exception occurred: " + e);
            log.error("Test will fail");
            fail();
        }
    }
}
