package com.wealthmanager.service.impl;

import com.wealthmanager.dto.RegisterRequestDTO;
import com.wealthmanager.entity.Role;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.UserRepository;
import com.wealthmanager.service.IUserServiceAuth;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAuth implements IUserServiceAuth {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceAuth(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterRequestDTO request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }


        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        // Save to database
        userRepository.save(user);

        return "User Registered Successfully";
    }


}
