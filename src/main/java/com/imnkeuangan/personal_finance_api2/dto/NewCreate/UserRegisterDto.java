package com.imnkeuangan.personal_finance_api2.dto.NewCreate;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
}
