package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.User;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.UserQueryForm;

import java.util.List;

/**
 * @author ket_ein17
 */
public interface UserRepository {
    List<User.Overview> getOverview(UserQueryForm form);
}
