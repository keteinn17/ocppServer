package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.repository.UserRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.User;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.UserQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.CodecException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.OCPP_TAG;
import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.USER;
import static eu.chargetime.ocpp.jsonserverimplementation.utils.CustomDSL.includes;

/**
 * @author ket_ein17
 */
@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private DSLContext ctx;
    @Override
    public List<User.Overview> getOverview(UserQueryForm form) {
            List<User.Overview> list = getOverviewInternal(form)
                    .map(r -> User.Overview.builder()
                            .userPk(r.value1())
                            .ocppTagPk(r.value2())
                            .ocppIdTag(r.value3())
                            .name(r.value4() + " " + r.value5())
                            .phone(r.value6())
                            .email(r.value7())
                            .build()
                    );
            return list;
    }

    @SuppressWarnings("unchecked")
    private Result<Record7<Integer, Integer, String, String, String, String, String>> getOverviewInternal(UserQueryForm form) {
        SelectQuery selectQuery = ctx.selectQuery();
        selectQuery.addFrom(USER);
        selectQuery.addJoin(OCPP_TAG, JoinType.LEFT_OUTER_JOIN, USER.OCPP_TAG_PK.eq(OCPP_TAG.OCPP_TAG_PK));
        selectQuery.addSelect(
                USER.USER_PK,
                USER.OCPP_TAG_PK,
                OCPP_TAG.ID_TAG,
                USER.FIRST_NAME,
                USER.LAST_NAME,
                USER.PHONE,
                USER.E_MAIL
        );

        if (form.isSetUserPk()) {
            selectQuery.addConditions(USER.USER_PK.eq(form.getUserPk()));
        }

        if (form.isSetOcppIdTag()) {
            selectQuery.addConditions(includes(OCPP_TAG.ID_TAG, form.getOcppIdTag()));
        }

        if (form.isSetEmail()) {
            selectQuery.addConditions(includes(USER.E_MAIL, form.getEmail()));
        }

        if (form.isSetName()) {

            // Concatenate the two columns and search within the resulting representation
            // for flexibility, since the user can search by first or last name, or both.
            Field<String> joinedField = DSL.concat(USER.FIRST_NAME, USER.LAST_NAME);

            // Find a matching sequence anywhere within the concatenated representation
            selectQuery.addConditions(includes(joinedField, form.getName()));
        }
        Result<Record7<Integer, Integer, String, String, String, String, String>> result=selectQuery.fetch();
        return selectQuery.fetch();
    }
}
