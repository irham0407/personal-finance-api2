package com.imnkeuangan.personal_finance_api2.dto.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateTransactionRequest {
    private BigDecimal amount;
    private String type; // "INCOME" atau "EXPENSE"
    private String description;
    private Long walletId; // ID Dompet tujuan transaksi
}
