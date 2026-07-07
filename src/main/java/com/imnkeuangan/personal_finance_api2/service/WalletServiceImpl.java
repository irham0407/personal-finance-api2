package com.imnkeuangan.personal_finance_api2.service;

import com.imnkeuangan.personal_finance_api2.dto.request.CreateWalletRequest;
import com.imnkeuangan.personal_finance_api2.dto.response.WalletResponse;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.model.Wallet;
import com.imnkeuangan.personal_finance_api2.repository.UserRepository;
import com.imnkeuangan.personal_finance_api2.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Wallet createWallet(CreateWalletRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan!"));

        Wallet wallet = new Wallet();
        wallet.setName(request.getName());
        wallet.setBalance(request.getBalance());
        wallet.setDescription(request.getDescription());
        wallet.setUser(user);

        return walletRepository.save(wallet);
    }

    @Override
    public List<Wallet> getWalletsByUserId(Long userId) {
        return walletRepository.findAll().stream()
                .filter(wallet -> wallet.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public List<WalletResponse> getAllWallets() {
        return walletRepository.findAll().stream()
                .map(wallet -> {
                    WalletResponse response = new WalletResponse();
                    response.setId(wallet.getId());
                    response.setName(wallet.getName());
                    response.setBalance(wallet.getBalance());
                    response.setDescription(wallet.getDescription());
                    return response;
                })
                .toList();
    }
}