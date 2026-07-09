package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateTransactionRequest;
import com.imnkeuangan.personal_finance_api2.model.Transaction;

public interface TransactionService2 {
    Transaction createTransaction(CreateTransactionRequest request);
}
