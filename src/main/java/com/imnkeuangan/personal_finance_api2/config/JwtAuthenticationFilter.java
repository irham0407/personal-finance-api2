package com.imnkeuangan.personal_finance_api2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Ambil header bernama "Authorization" dari request
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 2. Token JWT standar selalu diawali dengan kata "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Potong kata "Bearer " untuk mengambil tokennya saja
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("Gagal mengekstrak token JWT: " + e.getMessage());
            }
        }

        // 3. Jika username ada dan user belum diotentikasi di session Spring Context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Validasi kecocokan token
            if (jwtUtil.validateToken(jwt, username)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Masukkan user ke dalam daftar 'Authorized' Spring Security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // 4. Lanjutkan request ke filter/controller berikutnya
        filterChain.doFilter(request, response);
    }
}
