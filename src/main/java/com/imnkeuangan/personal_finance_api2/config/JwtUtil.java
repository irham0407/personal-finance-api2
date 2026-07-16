package com.imnkeuangan.personal_finance_api2.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Kunci rahasia minimal 32 karakter untuk mengamankan enkripsi token
    private final String SECRET_KEY = "ini_adalah_kunci_rahasia_untuk_aplikasi_keuangan_pribadi_jwt_2026";

    // TAMBAHKAN FUNGSI INI DI DALAM KELAS JWTUTIL AGAR MERAHNYA HILANG:
    private javax.crypto.SecretKey getSignKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }

    // Durasi token aktif: 1 Hari (dalam milidetik)
    private final long TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. Fungsi untuk MEMBUAT Token JWT berdasarkan username
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSignKey())
                .compact();
    }

    // 2. Fungsi untuk MENGAMBIL username dari dalam Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Fungsi untuk MENGAMBIL tanggal kadaluarsa token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 4. Fungsi untuk MEMERIKSA apakah token sudah kadaluarsa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 5. Fungsi untuk VALIDASI apakah token sah milik user tersebut
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
