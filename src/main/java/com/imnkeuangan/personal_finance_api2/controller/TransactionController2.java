package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateTransactionRequest;
import com.imnkeuangan.personal_finance_api2.model.Transaction;
import com.imnkeuangan.personal_finance_api2.service.TransactionService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 1. Endpoint untuk mendapatkan SEMUA riwayat transaksi tanpa terkecuali
    @GetMapping("/wallet")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // 2. Endpoint untuk mendapatkan riwayat transaksi khusus untuk satu wallet saja
    @GetMapping("/wallet/{walletId}")
    public ResponseEntity<?> getTransactionsByWallet(@PathVariable Long walletId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByWalletId(walletId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
