package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.NewCreate.UserRegisterDto;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserRegisterDto registerDto) {
        // 1. Validasi jika username sudah dipakai
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username sudah terdaftar!");
        }

        // 2. Validasi jika email sudah dipakai
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar!");
        }

        // 3. Mapping dari DTO ke Entity User
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());

        // Catatan: Untuk saat ini password disimpan dalam bentuk teks biasa (plain text).
        // Nanti kita bisa tingkatkan ke BCrypt jika sudah masuk ke materi Spring Security.
        user.setPassword(registerDto.getPassword());

        // 4. Simpan ke database
        return userRepository.save(user);
    }
}
