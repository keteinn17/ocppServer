package eu.chargetime.ocpp.jsonserverimplementation.service.dto;

import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * @author ket_ein17
 */
@ToString
@Getter
public class UnidentifiedIncomingObject {
    private final String key;
    private int numberOfAttempts = 0;
    private DateTime lastAttemptTimestamp;

    public UnidentifiedIncomingObject(String key) {
        this.key = key;
    }

    public synchronized void updateStats() {
        numberOfAttempts++;
        lastAttemptTimestamp = DateTime.now();
    }
}
