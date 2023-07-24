package eu.chargetime.ocpp.jsonserverimplementation.repository.impl;

import eu.chargetime.ocpp.jsonserverimplementation.db.tables.OcppTagActivity;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.OcppTagActivityRecord;
import eu.chargetime.ocpp.jsonserverimplementation.repository.OcppTagRepository;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.OcppTag;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.OcppTagQueryForm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.OCPP_TAG;
import static eu.chargetime.ocpp.jsonserverimplementation.db.Tables.OCPP_TAG_ACTIVITY;
import static eu.chargetime.ocpp.jsonserverimplementation.utils.DateTimeUtils.humanize;

/**
 * @author ket_ein17
 */
@Slf4j
@Repository
public class OcppTagRepositoryImpl  implements OcppTagRepository {
    private final DSLContext ctx;

    @Autowired
    public OcppTagRepositoryImpl(DSLContext ctx) {
        this.ctx = ctx;
    }
    @Override
    public OcppTagActivityRecord getRecord(String idTag) {
        return ctx.selectFrom(OCPP_TAG_ACTIVITY)
                .where(OCPP_TAG_ACTIVITY.ID_TAG.equal(idTag))
                .fetchOne();
    }

    @Override
    public OcppTagActivityRecord getRecord(int ocppTagPk) {
        return ctx.selectFrom(OCPP_TAG_ACTIVITY)
                .where(OCPP_TAG_ACTIVITY.OCPP_TAG_PK.equal(ocppTagPk))
                .fetchOne();
    }

    @Override
    public List<String> getIdTags() {
        return ctx.select(OCPP_TAG.ID_TAG)
                .from(OCPP_TAG)
                .fetch(OCPP_TAG.ID_TAG);
    }

    @Override
    public List<String> getParentIdTags() {
        return ctx.selectDistinct(OCPP_TAG.PARENT_ID_TAG)
                .from(OCPP_TAG)
                .where(OCPP_TAG.PARENT_ID_TAG.isNotNull())
                .fetch(OCPP_TAG.PARENT_ID_TAG);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OcppTag.Overview> getOverview(OcppTagQueryForm form) throws Exception {
        SelectQuery selectQuery = ctx.selectQuery();
        selectQuery.addFrom(OCPP_TAG_ACTIVITY);

        OcppTagActivity parentTable = OCPP_TAG_ACTIVITY.as("parent");

        selectQuery.addSelect(
                OCPP_TAG_ACTIVITY.OCPP_TAG_PK,
                parentTable.OCPP_TAG_PK,
                OCPP_TAG_ACTIVITY.ID_TAG,
                OCPP_TAG_ACTIVITY.PARENT_ID_TAG,
                OCPP_TAG_ACTIVITY.EXPIRY_DATE,
                OCPP_TAG_ACTIVITY.IN_TRANSACTION,
                OCPP_TAG_ACTIVITY.BLOCKED,
                OCPP_TAG_ACTIVITY.MAX_ACTIVE_TRANSACTION_COUNT,
                OCPP_TAG_ACTIVITY.ACTIVE_TRANSACTION_COUNT,
                OCPP_TAG_ACTIVITY.NOTE
        );

        selectQuery.addJoin(parentTable, JoinType.LEFT_OUTER_JOIN, parentTable.ID_TAG.eq(OCPP_TAG_ACTIVITY.PARENT_ID_TAG));

        if (form.isOcppTagPkSet()) {
            selectQuery.addConditions(OCPP_TAG_ACTIVITY.OCPP_TAG_PK.eq(form.getOcppTagPk()));
        }

        if (form.isIdTagSet()) {
            selectQuery.addConditions(OCPP_TAG_ACTIVITY.ID_TAG.eq(form.getIdTag()));
        }

        if (form.isParentIdTagSet()) {
            selectQuery.addConditions(OCPP_TAG_ACTIVITY.PARENT_ID_TAG.eq(form.getParentIdTag()));
        }

        switch (form.getExpired()) {
            case ALL:
                break;

            case TRUE:
                selectQuery.addConditions(OCPP_TAG_ACTIVITY.EXPIRY_DATE.lessOrEqual(DateTime.now()));
                break;

            case FALSE:
                selectQuery.addConditions(
                        OCPP_TAG_ACTIVITY.EXPIRY_DATE.isNull().or(OCPP_TAG_ACTIVITY.EXPIRY_DATE.greaterThan(DateTime.now()))
                );
                break;

            default:
                throw new Exception("Unknown enum type");
        }

        processBooleanType(selectQuery, OCPP_TAG_ACTIVITY.IN_TRANSACTION, form.getInTransaction());
        processBooleanType(selectQuery, OCPP_TAG_ACTIVITY.BLOCKED, form.getBlocked());

        return selectQuery.fetch().map(new UserMapper());
    }

    private void processBooleanType(SelectQuery selectQuery,
                                    TableField<OcppTagActivityRecord, Boolean> field,
                                    OcppTagQueryForm.BooleanType type) {
        if (type != OcppTagQueryForm.BooleanType.ALL) {
            selectQuery.addConditions(field.eq(type.getBoolValue()));
        }
    }

    private static class UserMapper
            implements RecordMapper<Record10<Integer, Integer, String, String, DateTime, Boolean, Boolean, Integer, Long, String>, OcppTag.Overview> {
        @Override
        public OcppTag.Overview map(Record10<Integer, Integer, String, String, DateTime, Boolean, Boolean, Integer, Long, String> r) {
            return OcppTag.Overview.builder()
                    .ocppTagPk(r.value1())
                    .parentOcppTagPk(r.value2())
                    .idTag(r.value3())
                    .parentIdTag(r.value4())
                    .expiryDate(r.value5())
                    .expiryDateFormatted(humanize(r.value5()))
                    .inTransaction(r.value6())
                    .blocked(r.value7())
                    .maxActiveTransactionCount(r.value8())
                    .activeTransactionCount(r.value9())
                    .note(r.value10())
                    .build();
        }
    }
}
