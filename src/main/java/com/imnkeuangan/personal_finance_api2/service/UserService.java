package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.constant.Role;
import com.imnkeuangan.personal_finance_api2.dto.NewCreate.UserRegisterDto;
import com.imnkeuangan.personal_finance_api2.dto.request.UserLoginDto;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserRegisterDto registerDto, Long creatorId) {
        // 1. Validasi duplikasi username & email
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username sudah terdaftar!");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email sudah terdaftar!");
        }

        // 2. Hitung jumlah user saat ini di database
        long userCount = userRepository.count();

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        // LOGIKA PENENTUAN ROLE & OTORITAS
        if (userCount == 0) {
            // JIKA DATABASE MASIH KOSONG: Pendaftar pertama otomatis dipaksa jadi ADMIN (Superadmin)
            user.setRole(Role.ADMIN);
        } else {
            // JIKA DATABASE SUDAH ADA ISINYA: Maka pembuatan akun selanjutnya harus dilakukan oleh ADMIN
            if (creatorId == null) {
                throw new RuntimeException("Akses ditolak! Harus menyertakan ID Admin yang membuat akun ini.");
            }

            // Cari siapa yang sedang mencoba membuat akun ini
            User creator = userRepository.findById(creatorId)
                    .orElseThrow(() -> new RuntimeException("Data pembuat akun tidak ditemukan!"));

            // Validasi apakah creator adalah seorang ADMIN
            if (creator.getRole() != Role.ADMIN) {
                throw new RuntimeException("Akses ditolak! Hanya akun dengan role ADMIN yang boleh membuat akun baru.");
            }

            // Jika yang membuat terbukti ADMIN, tentukan role user baru (default ke USER jika tidak diinput)
            if (registerDto.getRole() != null) {
                user.setRole(registerDto.getRole());
            } else {
                user.setRole(Role.USER);
            }
        }

        return userRepository.save(user);
    }

    public User loginUser(UserLoginDto loginDto) {
        // 1. Cari user berdasarkan username ATAU email
        User user = userRepository.findByUsername(loginDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findAll().stream()
                        .filter(u -> u.getEmail().equalsIgnoreCase(loginDto.getUsernameOrEmail()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Username atau Email tidak ditemukan!")));

        // 2. Cocokkan password (sementara masih plain-text)
        if (!user.getPassword().equals(loginDto.getPassword())) {
            throw new RuntimeException("Password salah!");
        }

        // 3. Jika cocok, kembalikan data user
        return user;
    }
}