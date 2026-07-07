package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateWalletRequest;
import com.imnkeuangan.personal_finance_api2.dto.response.WalletResponse;
import com.imnkeuangan.personal_finance_api2.model.Wallet;
import com.imnkeuangan.personal_finance_api2.service.WalletServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletServiceImpl walletService;

//    @GetMapping
//    public ResponseEntity<List<WalletResponse>> getAllWallets() { // Sesuaikan generic type ResponseEntity jika diperlukan
//        // UBAH 'List<Wallet>' MENJADI 'List<WalletResponse>' DI BAWAH INI:
//        List<WalletResponse> wallets = walletService.getAllWallets();
//        return new ResponseEntity<>(wallets, HttpStatus.OK);
//    }

//    @PostMapping
//    public ResponseEntity<Wallet> createWallet(@RequestBody CreateWalletRequest request) {
//        try {
//            Wallet wallet = walletService.createWallet(request);
//            return new ResponseEntity<>(wallet, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getAll() {
        List<WalletResponse> responses = walletService.getAllWallets();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/create-new") // Beri path pembeda jika bentrok dengan method create bawaan
    public ResponseEntity<?> createWallet(@RequestBody CreateWalletRequest request) {
        try {
            // Pastikan menggunakan walletService (bukan walletRepositoryService)
            Wallet wallet = walletService.createWallet(request);
            return new ResponseEntity<>(wallet, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint baru untuk melihat dompet berdasarkan User ID
    // URL: GET http://localhost:8080/api/wallets/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWalletsByUser(@PathVariable Long userId) {
        return new ResponseEntity<>(walletService.getWalletsByUserId(userId), HttpStatus.OK);
    }
}