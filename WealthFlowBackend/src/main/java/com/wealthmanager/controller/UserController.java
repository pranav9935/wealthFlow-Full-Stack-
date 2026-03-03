package com.wealthmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        return ResponseEntity.ok(
                new UserResponse(
                        authentication.getName(),
                        "USER"
                )
        );
    }

    record UserResponse(String email, String role) {}
}