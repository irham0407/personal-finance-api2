package com.imnkeuangan.personal_finance_api2.repository;

import com.imnkeuangan.personal_finance_api2.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Fungsi otomatis untuk mencari riwayat transaksi berdasarkan ID Wallet
    List<Transaction> findByWalletId(Long walletId);
}
