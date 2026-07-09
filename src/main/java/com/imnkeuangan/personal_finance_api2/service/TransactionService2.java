package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateTransactionRequest;
import com.imnkeuangan.personal_finance_api2.model.Transaction;

import java.util.List;

public interface TransactionService2 {
    Transaction createTransaction(CreateTransactionRequest request);

    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByWalletId(Long walletId);
}
