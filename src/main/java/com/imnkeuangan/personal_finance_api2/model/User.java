package com.imnkeuangan.personal_finance_api2.model;

import com.imnkeuangan.personal_finance_api2.constant.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    // TAMBAHKAN FIELD INI AGAR MATCH DENGAN USERREPOSITORY
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Pastikan nanti dienkripsi (misal pakai BCrypt)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
