package com.imnkeuangan.personal_finance_api2.dto.NewCreate;

import com.imnkeuangan.personal_finance_api2.constant.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
    private Role role; // Bisa diisi ADMIN atau USER, jika kosong nanti di-handle otomatis
}
