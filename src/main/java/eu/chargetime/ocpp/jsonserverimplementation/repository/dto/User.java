package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.AddressRecord;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.UserRecord;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author ket_ein17
 */
public class User {
    @Getter
    @Builder
    public static final class Overview implements Serializable {
        private final Integer userPk, ocppTagPk;
        private final String ocppIdTag, name, phone, email;
    }

    @Getter
    @Builder
    public static final class Details {
        private final UserRecord userRecord;
        private final AddressRecord address;
        private Optional<String> ocppIdTag;
    }
}
