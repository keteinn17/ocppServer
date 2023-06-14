//package eu.chargetime.ocpp.jsonserverimplementation.ws;
//
//import com.google.common.base.Strings;
//import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppServerRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketHttpHeaders;
//import org.springframework.web.socket.server.HandshakeFailureException;
//import org.springframework.web.socket.server.HandshakeHandler;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RequiredArgsConstructor
//public class OcppHandshakeHandler implements HandshakeHandler {
//    private OcppServerRepository ocppServerRepository;
//    @Autowired
//    public OcppHandshakeHandler(OcppServerRepository ocppServerRepository){
//        this.ocppServerRepository=ocppServerRepository;
//    }
//    @Override
//    public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
//        String chargeBoxId = getLastBitFromUrl(request.getURI().getPath());
//        try {
//            Integer check = ocppServerRepository.getChargeBoxId(chargeBoxId);
//            if(check==0){
//                log.error("ChargeBoxId '{}' is not recognized.", chargeBoxId);
//                response.setStatusCode(HttpStatus.NOT_FOUND);
//                return false;
//            }
//            attributes.put("CHARGEBOX_ID_KEY",chargeBoxId);
//            List<String> requestedProtocols = new WebSocketHttpHeaders(request.getHeaders()).getSecWebSocketProtocol();
//            if (CollectionUtils.isEmpty(requestedProtocols)) {
//                log.error("No protocol (OCPP version) is specified.");
//                response.setStatusCode(HttpStatus.BAD_REQUEST);
//                return false;
//            }
//            return true;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static String getLastBitFromUrl(final String input) {
//        if (Strings.isNullOrEmpty(input)) {
//            return "";
//        }
//
//        final String substring = WebSocketConfiguration.PATH_INFIX;
//
//        int index = input.indexOf(substring);
//        if (index == -1) {
//            return "";
//        } else {
//            return input.substring(index + substring.length());
//        }
//    }
//}
