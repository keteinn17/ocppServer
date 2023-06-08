package eu.chargetime.ocpp.jsonserverimplementation.config;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class WebSocketConfiguration implements WebSocketConfigurer {
    public static final String PATH_INFIX = "/websocket/CentralSystemService/";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

    }
}
