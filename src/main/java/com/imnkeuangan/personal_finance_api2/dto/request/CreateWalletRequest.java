package com.imnkeuangan.personal_finance_api2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateWalletRequest {

    @NotBlank(message = "Nama dompet tidak boleh kosong")
    @Size(max = 100, message = "Nama dompet maksimal 100 karakter")
    private String name;

    @NotNull(message = "Saldo awal tidak boleh kosong")
    private BigDecimal balance;

    private String description;
}
