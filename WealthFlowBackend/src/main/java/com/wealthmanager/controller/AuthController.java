package com.wealthmanager.controller;

import com.wealthmanager.dto.LoginRequestDTO;
import com.wealthmanager.dto.RegisterRequestDTO;
import com.wealthmanager.dto.VerifyOtpDTO;
import com.wealthmanager.service.impl.LoginService;
import com.wealthmanager.service.impl.UserServiceAuth;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceAuth userService;
    private final LoginService loginservice;
    public AuthController(UserServiceAuth userService, LoginService loginservice) {
        this.userService = userService;
        this.loginservice = loginservice;
    }

    /**
     * Register new user
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
        String response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(loginservice.login(request));
    }

    @PostMapping("/verify-otp")
        public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDTO request) {

            return ResponseEntity.ok(userService.verifyOtp(request));
        }

        @PostMapping("/resend-otp")
public ResponseEntity<String> resendOtp(@RequestBody VerifyOtpDTO request) {

    return ResponseEntity.ok(userService.resendOtp(request.getEmail()));

}


}
