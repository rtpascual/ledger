package com.rtpascual.ledger.core.dao;

import com.rtpascual.ledger.core.model.Transaction;

import java.util.List;

public interface LedgerDao {
    List<Transaction> getAllTransactions();
    void saveTransaction(Transaction transaction);
}
