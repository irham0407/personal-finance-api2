package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateTransactionRequest;
import com.imnkeuangan.personal_finance_api2.model.Transaction;
import com.imnkeuangan.personal_finance_api2.service.TransactionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions2")
public class TransactionController2 {

    @Autowired
    private TransactionService2 transactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody CreateTransactionRequest request) {
        try {
            Transaction savedTransaction = transactionService.createTransaction(request);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Jika saldo kurang atau wallet tidak ketemu, kirim pesan errornya ke Insomnia
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
