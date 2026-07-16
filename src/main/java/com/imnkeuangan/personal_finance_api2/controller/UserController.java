package com.imnkeuangan.personal_finance_api2.controller;

import com.imnkeuangan.personal_finance_api2.dto.NewCreate.UserRegisterDto;
import com.imnkeuangan.personal_finance_api2.dto.request.UserLoginDto;
import com.imnkeuangan.personal_finance_api2.model.User;
import com.imnkeuangan.personal_finance_api2.service.UserService;
import com.imnkeuangan.personal_finance_api2.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(
//            @RequestBody UserRegisterDto registerDto,
//            @RequestParam(required = false) Long creatorId) { // Menerima parameter ?creatorId=...
//        try {
//            User registeredUser = userService.registerNewUser(registerDto, creatorId);
//            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
//        try {
//            User user = userService.loginUser(loginDto);
//            return new ResponseEntity<>(user, HttpStatus.OK); // Mengembalikan status 200 OK jika sukses
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED); // 401 Unauthorized jika gagal
//        }
//    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDto dto) {
        try {
            User registeredUser = userService.registerUser(dto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto dto) {
        try {
            // Dapatkan token JWT dari Service
            String token = userService.loginUser(dto);

            // Bungkus ke dalam Map JSON agar rapi saat diterima di Insomnia
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}