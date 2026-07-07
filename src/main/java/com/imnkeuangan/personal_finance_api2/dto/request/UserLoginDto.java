package com.imnkeuangan.personal_finance_api2.dto.request;

import lombok.Data;

@Data
public class UserLoginDto {
    private String usernameOrEmail; // Pengguna bisa login menggunakan username ATAU email
    private String password;
}