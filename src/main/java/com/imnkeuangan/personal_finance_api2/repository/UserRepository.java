package com.imnkeuangan.personal_finance_api2.repository;
import com.imnkeuangan.personal_finance_api2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method tambahan untuk mencari user berdasarkan username (berguna untuk login nanti)
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

    // Method untuk mengecek apakah email sudah terdaftar atau belum
    boolean existsByEmail(String email);

}
