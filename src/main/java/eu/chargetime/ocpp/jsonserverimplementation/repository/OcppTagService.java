package eu.chargetime.ocpp.jsonserverimplementation.repository;

import com.google.common.base.Strings;
import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.OcppTagActivityRecord;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.OcppTag;
import eu.chargetime.ocpp.jsonserverimplementation.server.CentralSystemServiceImpl;
import eu.chargetime.ocpp.jsonserverimplementation.service.UnidentifiedIncomingObjectService;
import eu.chargetime.ocpp.jsonserverimplementation.service.dto.UnidentifiedIncomingObject;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.OcppTagQueryForm;
import eu.chargetime.ocpp.model.core.AuthorizationStatus;
import eu.chargetime.ocpp.model.core.IdTagInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static eu.chargetime.ocpp.jsonserverimplementation.web.dto.AuthorizationStatus.*;

/**
 * @author ket_ein17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OcppTagService {
    private final UnidentifiedIncomingObjectService invalidOcppTagService = new UnidentifiedIncomingObjectService(1000);
    private final OcppTagRepository ocppTagRepository;
    private final SettingRepository settingRepository;
    @Nullable
    public IdTagInfo getIdTagInfo(@Nullable String idTag, boolean isStartTransactionReqContext) throws Exception {
        if (Strings.isNullOrEmpty(idTag)) {
            return null;
        }

        OcppTagActivityRecord record = ocppTagRepository.getRecord(idTag);
        AuthorizationStatus status = decideStatus(record, idTag, isStartTransactionReqContext);

        switch (status) {
            case Invalid:
                invalidOcppTagService.processNewUnidentified(idTag);
                return new IdTagInfo(status);

            case Blocked:
            case Expired:
            case ConcurrentTx:
            case Accepted:
                IdTagInfo idTagInfo = new IdTagInfo(status);
                idTagInfo.setParentIdTag(record.getParentIdTag());
                //idTagInfo.setExpiryDate(CentralSystemServiceImpl.convert(getExpiryDateOrDefault(record)));
                return idTagInfo;
            default:
                throw new Exception("Unexpected AuthorizationStatus");
        }
    }

    @Nullable
    public IdTagInfo getIdTagInfo(@Nullable String idTag, boolean isStartTransactionReqContext, Supplier<IdTagInfo> supplierWhenException) {
        try {
            return getIdTagInfo(idTag, isStartTransactionReqContext);
        } catch (Exception e) {
            log.error("Exception occurred", e);
            return supplierWhenException.get();
        }
    }

    public List<String> getIdTags() {
        return ocppTagRepository.getIdTags();
    }

    public List<String> getParentIdTags() {
        return ocppTagRepository.getParentIdTags();
    }

    public List<OcppTag.Overview> getOverview(OcppTagQueryForm form) throws Exception {
        return ocppTagRepository.getOverview(form);
    }
    public List<UnidentifiedIncomingObject> getUnknownOcppTags() {
        return invalidOcppTagService.getObjects();
    }

    private AuthorizationStatus decideStatus(OcppTagActivityRecord record, String idTag, boolean isStartTransactionReqContext) {
        if (record == null) {
            log.error("The user with idTag '{}' is INVALID (not present in DB).", idTag);
            return AuthorizationStatus.Invalid;
        }

        if (isBlocked(record)) {
            log.error("The user with idTag '{}' is BLOCKED.", idTag);
            return AuthorizationStatus.Blocked;
        }

        if (isExpired(record, DateTime.now())) {
            log.error("The user with idTag '{}' is EXPIRED.", idTag);
            return AuthorizationStatus.Expired;
        }

        // https://github.com/steve-community/steve/issues/219
        if (isStartTransactionReqContext && reachedLimitOfActiveTransactions(record)) {
            log.warn("The user with idTag '{}' is ALREADY in another transaction(s).", idTag);
            return AuthorizationStatus.ConcurrentTx;
        }

        log.debug("The user with idTag '{}' is ACCEPTED.", record.getIdTag());
        return AuthorizationStatus.Accepted;
    }

    private static boolean isBlocked(OcppTagActivityRecord record) {
        return record.getMaxActiveTransactionCount() == 0;
    }

    private static boolean isExpired(OcppTagActivityRecord record, DateTime now) {
        DateTime expiry = record.getExpiryDate();
        return expiry != null && now.isAfter(expiry);
    }

    private static boolean reachedLimitOfActiveTransactions(OcppTagActivityRecord record) {
        int max = record.getMaxActiveTransactionCount();

        // blocked
        if (max == 0) {
            return true;
        }

        // allow all
        if (max < 0) {
            return false;
        }

        // allow as specified
        return record.getActiveTransactionCount() >= max;
    }

    @Nullable
    private DateTime getExpiryDateOrDefault(OcppTagActivityRecord record) {
        if (record.getExpiryDate() != null) {
            return record.getExpiryDate();
        }

        int hoursToExpire = settingRepository.getHoursToExpire();

        // From web page: The value 0 disables this functionality (i.e. no expiry date will be set).
        if (hoursToExpire == 0) {
            return null;
        } else {
            return DateTime.now().plusHours(hoursToExpire);
        }
    }
}
