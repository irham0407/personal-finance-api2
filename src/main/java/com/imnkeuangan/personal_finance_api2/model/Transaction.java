package com.imnkeuangan.personal_finance_api2.model;

import com.imnkeuangan.personal_finance_api2.constant.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tb_transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // INCOME atau EXPENSE

    @Column(nullable = false)
    private ZonedDateTime transactionDate;

    // Menghubungkan Transaksi ke Wallet tertentu (Many Transactions to One Wallet)
    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;
}
