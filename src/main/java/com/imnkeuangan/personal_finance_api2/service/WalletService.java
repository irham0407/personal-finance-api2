package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateWalletRequest;
import com.imnkeuangan.personal_finance_api2.dto.response.WalletResponse;
import com.imnkeuangan.personal_finance_api2.model.Wallet;

import java.util.List;

public interface WalletService {
    Wallet createWallet(CreateWalletRequest request);
    List<Wallet> getWalletsByUserId(Long userId); // Tambahkan ini di interface

    List<WalletResponse> getAllWallets();
}