package eu.chargetime.ocpp.jsonclientimplementation.ocppClient;

import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class JsonClientConfig {

    @Bean
    public JSONClient configureJsonClient(ClientCoreProfile core) {
        return new JSONClient(core);
    }
}
