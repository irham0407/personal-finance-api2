package com.imnkeuangan.personal_finance_api2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import com.imnkeuangan.personal_finance_api2.model.User;

@Entity
@Table(name = "tb_wallets")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    private String description;

    // Hubungan: Banyak Dompet bisa dimiliki oleh Satu User (Many Wallets to One User)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}