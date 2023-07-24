package eu.chargetime.ocpp.jsonserverimplementation.ws.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ScheduledFuture;

/**
 * @author ket_ein17
 */
@Getter
@RequiredArgsConstructor
public class SessionContext {
    private final WebSocketSession session;
    private final ScheduledFuture pingSchedule;
    private final DateTime openSince;
}
