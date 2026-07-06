package com.imnkeuangan.personal_finance_api2.repository;

import com.imnkeuangan.personal_finance_api2.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    // Fungsi bawaan save(), findAll(), findById(), deleteById() otomatis tersedia
}
