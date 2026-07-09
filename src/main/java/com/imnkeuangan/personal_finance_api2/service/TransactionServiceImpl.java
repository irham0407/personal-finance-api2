package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateTransactionRequest;
import com.imnkeuangan.personal_finance_api2.model.Transaction;
import com.imnkeuangan.personal_finance_api2.model.Wallet;
import com.imnkeuangan.personal_finance_api2.repository.TransactionRepository;
import com.imnkeuangan.personal_finance_api2.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService2{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    @Transactional
    public Transaction createTransaction(CreateTransactionRequest request) {
        // 1. Ambil data dompet berdasarkan walletId dari DTO request
        Wallet wallet = walletRepository.findById(request.getWalletId())
                .orElseThrow(() -> new RuntimeException("Dompet tidak ditemukan!"));

        // 2. Ambil saldo saat ini, handle jika null agar tidak menyebabkan NullPointerException
        BigDecimal currentBalance = wallet.getBalance() != null ? wallet.getBalance() : BigDecimal.ZERO;

        // Ambil nominal transaksi dari request DTO
        BigDecimal transactionAmount = request.getAmount();

        // 3. Logika Otomatisasi Perubahan Saldo (INCOME atau EXPENSE)
        if (request.getType().equalsIgnoreCase("INCOME")) {
            // Jika Pemasukan -> Saldo bertambah
            wallet.setBalance(currentBalance.add(transactionAmount));
        } else if (request.getType().equalsIgnoreCase("EXPENSE")) {
            // Jika Pengeluaran -> Validasi apakah saldo mencukupi
            if (currentBalance.compareTo(transactionAmount) < 0) {
                throw new RuntimeException("Saldo dompet tidak mencukupi untuk melakukan transaksi ini!");
            }
            // Saldo berkurang
            wallet.setBalance(currentBalance.subtract(transactionAmount));
        } else {
            throw new RuntimeException("Tipe transaksi tidak valid! Gunakan INCOME atau EXPENSE.");
        }

        // 4. Simpan perubahan nilai saldo dompet terbaru ke database (tb_wallets)
        walletRepository.save(wallet);

        // 5. Buat objek riwayat transaksi baru untuk dicatat
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionAmount);
        transaction.setType(request.getType().toUpperCase());
        transaction.setDescription(request.getDescription());
        transaction.setWallet(wallet);

        // 6. Simpan data riwayat transaksi ke database (tb_transactions) lalu kembalikan hasilnya
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        // Validasi dulu apakah wallet-nya eksis di database sebelum mencari transaksinya
        if (!walletRepository.existsById(walletId)) {
            throw new RuntimeException("Dompet dengan ID " + walletId + " tidak ditemukan!");
        }
        return transactionRepository.findByWalletId(walletId);
    }
}
