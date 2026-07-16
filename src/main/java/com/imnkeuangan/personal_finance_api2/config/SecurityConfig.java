package com.imnkeuangan.personal_finance_api2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean ini digunakan untuk menyandikan password agar aman di database
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Nonaktifkan CSRF karena kita pakai token berbasis REST API
                .authorizeHttpRequests(auth -> auth
                        // Izinkan endpoint register dan login diakses tanpa token
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        // Endpoint sisanya wajib login / bawa token
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
