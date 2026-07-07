package com.imnkeuangan.personal_finance_api2.dto.NewCreate;

import com.imnkeuangan.personal_finance_api2.constant.TransactionType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionCreateDto {
    private Long walletId;       // ID dompet yang digunakan
    private BigDecimal amount;   // Nominal uang
    private String description;  // Catatan transaksi
    private TransactionType type; // INCOME atau EXPENSE
}
