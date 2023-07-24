/*
package eu.chargetime.ocpp.jsonserverimplementation.ws;

import org.springframework.web.socket.*;

import java.util.List;

public class AbstractWebSocketEndpoint extends ConcurrentWebSocketHandler implements SubProtocolCapable {

    @Override
    void onMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            handleTextMessage(session, (TextMessage) message);

        } else if (message instanceof PongMessage) {
            handlePongMessage(session);

        } else if (message instanceof BinaryMessage) {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Binary messages not supported"));

        } else {
            throw new IllegalStateException("Unexpected WebSocket message type: " + message);
        }
    }

    @Override
    void onOpen(WebSocketSession session) throws Exception {

    }

    @Override
    void onClose(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    void onError(WebSocketSession session, Throwable throwable) throws Exception {

    }

    @Override
    public List<String> getSubProtocols() {
        return null;
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
*/
