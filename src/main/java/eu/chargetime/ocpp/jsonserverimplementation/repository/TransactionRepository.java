package eu.chargetime.ocpp.jsonserverimplementation.repository;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.Transaction;
import eu.chargetime.ocpp.jsonserverimplementation.web.dto.TransactionQueryForm;

import java.util.List;

/**
 * @author ket_ein17
 */
public interface TransactionRepository {
    List<Transaction> getTransactions(TransactionQueryForm form) throws Exception;
}
