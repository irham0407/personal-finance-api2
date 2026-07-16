package com.imnkeuangan.personal_finance_api2.Unused;

import com.imnkeuangan.personal_finance_api2.constant.TransactionType;
import com.imnkeuangan.personal_finance_api2.dto.NewCreate.TransactionCreateDto;
import com.imnkeuangan.personal_finance_api2.model.Transaction;
import com.imnkeuangan.personal_finance_api2.model.Wallet;
import com.imnkeuangan.personal_finance_api2.repository.TransactionRepository;
import com.imnkeuangan.personal_finance_api2.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public Transaction createTransaction(TransactionCreateDto dto) {
        // 1. Cari Wallet berdasarkan ID, jika tidak ada lempar error
        Wallet wallet = walletRepository.findById(dto.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet tidak ditemukan!"));

        // 2. Logika Update Saldo Wallet otomatis
        if (dto.getType() == TransactionType.INCOME) {
            // Jika pemasukan: saldo lama + amount baru
            wallet.setBalance(wallet.getBalance().add(dto.getAmount()));
        } else if (dto.getType() == TransactionType.EXPENSE) {
            // Validasi: Cek apakah saldo cukup untuk pengeluaran
            if (wallet.getBalance().compareTo(dto.getAmount()) < 0) {
                throw new RuntimeException("Saldo dompet tidak mencukupi!");
            }
            // Jika pengeluaran: saldo lama - amount baru
            wallet.setBalance(wallet.getBalance().subtract(dto.getAmount()));
        }

        // Simpan update saldo wallet terbaru
        walletRepository.save(wallet);

        // 3. Mapping DTO ke Entity Transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setType(dto.getType().name()); // Mengubah objek Enum menjadi String agar cocok dengan Entity
        transaction.setTransactionDate(ZonedDateTime.now()); // Set jam saat ini
        transaction.setWallet(wallet);

        // 4. Simpan data transaksi ke database
        return transactionRepository.save(transaction);
    }

    // 1. Ambil semua riwayat transaksi tanpa terkecuali
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // 2. Ambil riwayat transaksi khusus untuk satu wallet saja
    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        // Validasi dulu apakah wallet-nya eksis di database
        if (!walletRepository.existsById(walletId)) {
            throw new RuntimeException("Wallet dengan ID " + walletId + " tidak ditemukan!");
        }
        return transactionRepository.findByWalletId(walletId);
    }
}
