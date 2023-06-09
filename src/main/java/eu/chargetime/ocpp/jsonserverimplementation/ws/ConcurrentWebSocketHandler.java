package eu.chargetime.ocpp.jsonserverimplementation.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration

public abstract class ConcurrentWebSocketHandler implements WebSocketHandler {
    private final Map<String, ConcurrentWebSocketSessionDecorator> sessions = new ConcurrentHashMap<>();
    private static final int sendTimeLimit = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int bufferSizeLimit = 5 *8_388_608;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session);
        this.onOpen(internalGet(session));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(message.toString());
        this.onMessage(internalGet(session), message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        this.onError(internalGet(session), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        this.onClose(sessions.remove(session.getId()), closeStatus);
    }



    private ConcurrentWebSocketSessionDecorator internalGet(WebSocketSession session) {
        return sessions.computeIfAbsent(session.getId(), s -> new ConcurrentWebSocketSessionDecorator(session, sendTimeLimit, bufferSizeLimit));
    }

    abstract void onMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception;
    abstract void onOpen(WebSocketSession session) throws Exception;
    abstract void onClose(WebSocketSession session, CloseStatus closeStatus) throws Exception;
    abstract void onError(WebSocketSession session, Throwable throwable) throws Exception;
}
