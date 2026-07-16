package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.constant.Role;
import com.imnkeuangan.personal_finance_api2.config.JwtUtil;
import com.imnkeuangan.personal_finance_api2.dto.NewCreate.UserRegisterDto;
import com.imnkeuangan.personal_finance_api2.dto.request.UserLoginDto;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // 1. PROSES REGISTRASI (Enkripsi Password)
    public User registerUser(UserRegisterDto dto) {
        // Validasi jika username sudah terpakai
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username sudah terdaftar!");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(Role.USER);

        // AMANKAN PASSWORD: Ubah text asli menjadi hash BCrypt sebelum disimpan
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    // 2. PROSES LOGIN (Validasi & Pembuatan Token)
    public String loginUser(UserLoginDto dto) {
        // Cari user berdasarkan username
        User user = userRepository.findByUsername(dto.getUsernameOrEmail())
                .orElseThrow(() -> new RuntimeException("Username atau password salah!"));

        // Cek apakah password yang dimasukkan cocok dengan hash di database
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username atau password salah!");
        }

        // Jika cocok, cetak token JWT berdurasi 1 hari untuk user ini
        return jwtUtil.generateToken(user.getUsername());
    }
}
