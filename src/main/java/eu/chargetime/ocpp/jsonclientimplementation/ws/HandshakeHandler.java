/*
package eu.chargetime.ocpp.jsonclientimplementation.ws;

import com.google.common.base.Strings;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;

import java.util.Map;

public class HandshakeHandler implements org.springframework.web.socket.server.HandshakeHandler {
    @Override
    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
        return false;
    }

    public static String getLastBitFromUrl(final String input) {
        if (Strings.isNullOrEmpty(input)) {
            return "";
        }

        final String substring = WebSocketConfiguration.PATH_INFIX;

        int index = input.indexOf(substring);
        if (index == -1) {
            return "";
        } else {
            return input.substring(index + substring.length());
        }
    }
}
*/
