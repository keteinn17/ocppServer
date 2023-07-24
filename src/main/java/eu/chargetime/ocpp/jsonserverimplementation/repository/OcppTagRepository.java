package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.db.tables.records.OcppTagActivityRecord;
import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.OcppTag;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.OcppTagQueryForm;

import java.util.List;

/**
 * @author ket_ein17
 */
public interface OcppTagRepository {
    OcppTagActivityRecord getRecord(String idTag);
    OcppTagActivityRecord getRecord(int ocppTagPk);
    public List<String> getIdTags();
    List<String> getParentIdTags();
    List<OcppTag.Overview> getOverview(OcppTagQueryForm form) throws Exception;
}
