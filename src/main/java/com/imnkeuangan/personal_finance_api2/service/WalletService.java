package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateWalletRequest;
import com.imnkeuangan.personal_finance_api2.dto.response.WalletResponse;

public interface WalletService {
    WalletResponse createWallet(CreateWalletRequest request);
}
