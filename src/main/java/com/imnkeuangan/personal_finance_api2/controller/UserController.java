package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.NewCreate.UserRegisterDto;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegisterDto registerDto,
            @RequestParam(required = false) Long creatorId) { // Menerima parameter ?creatorId=...
        try {
            User registeredUser = userService.registerNewUser(registerDto, creatorId);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
