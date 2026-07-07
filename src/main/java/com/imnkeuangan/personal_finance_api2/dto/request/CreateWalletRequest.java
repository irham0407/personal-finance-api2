package com.imnkeuangan.personal_finance_api2.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateWalletRequest {

    private String name;
    private BigDecimal balance;
    private String description;
    private Long userId; // ID User pemilik dompet
}