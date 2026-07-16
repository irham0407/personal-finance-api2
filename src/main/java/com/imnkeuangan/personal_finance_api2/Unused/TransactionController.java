package com.imnkeuangan.personal_finance_api2.Unused;

import com.imnkeuangan.personal_finance_api2.dto.NewCreate.TransactionCreateDto;
import com.imnkeuangan.personal_finance_api2.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionCreateDto dto) {
        try {
            Transaction transaction = transactionService.createTransaction(dto);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 1. Endpoint untuk mendapatkan SEMUA riwayat transaksi
    // URL: GET http://localhost:8080/api/transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    // 2. Endpoint untuk mendapatkan riwayat transaksi berdasarkan ID Wallet tertentu
    // URL: GET http://localhost:8080/api/transactions/wallet/{walletId}
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
