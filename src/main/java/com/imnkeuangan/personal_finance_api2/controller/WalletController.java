package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateWalletRequest;
import com.imnkeuangan.personal_finance_api2.dto.response.WalletResponse;
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

    @PostMapping
    public ResponseEntity<WalletResponse> create(@Valid @RequestBody CreateWalletRequest request) {
        WalletResponse response = walletService.createWallet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getAll() {
        List<WalletResponse> responses = walletService.getAllWallets();
        return ResponseEntity.ok(responses);
    }
}
