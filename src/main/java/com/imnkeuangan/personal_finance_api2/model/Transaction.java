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

    private BigDecimal amount; // 2. Diubah ke BigDecimal agar sinkron dengan Service
    private String type;
    private String description;
    private ZonedDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @PrePersist
    protected void onCreate() {
        this.transactionDate = ZonedDateTime.now();
    }
}
