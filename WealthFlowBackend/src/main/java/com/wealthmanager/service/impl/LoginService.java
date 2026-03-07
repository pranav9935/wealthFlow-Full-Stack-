package com.wealthmanager.service.impl;

import com.wealthmanager.dto.LoginRequestDTO;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.UserRepository;
import com.wealthmanager.security.JwtUtil;
import com.wealthmanager.service.ILoginService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String login(LoginRequestDTO request) {


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

                if (!user.isVerified()) {
                throw new RuntimeException("Please verify your email.");
            }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
