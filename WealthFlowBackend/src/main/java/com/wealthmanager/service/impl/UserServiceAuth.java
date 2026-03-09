package com.wealthmanager.service.impl;

import com.wealthmanager.dto.RegisterRequestDTO;
import com.wealthmanager.dto.VerifyOtpDTO;
import com.wealthmanager.entity.OtpVerification;
import com.wealthmanager.entity.Role;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.OtpRepository;
import com.wealthmanager.repository.UserRepository;
import com.wealthmanager.service.IUserServiceAuth;
import com.wealthmanager.util.OtpUtil;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAuth implements IUserServiceAuth {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final EmailService emailService;

    public UserServiceAuth(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpRepository otpRepository,EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
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
        .verified(false)
        .build();
          // Save to database
        userRepository.save(user);

        String otp = OtpUtil.generateOtp();

        OtpVerification otpEntity = OtpVerification.builder()
        .email(user.getEmail())
        .otp(otp)
        .expiryTime(LocalDateTime.now().plusMinutes(5))
        .build();

        otpRepository.save(otpEntity);

       
        emailService.sendOtp(user.getEmail(), otp);

      

        return "OTP sent to email";
    }
    @Override
public User findByEmail(String email) {

    return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

}


public String verifyOtp(VerifyOtpDTO request) {

    OtpVerification otpData = otpRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("OTP not found"));

    if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
    otpRepository.delete(otpData);
    throw new RuntimeException("OTP expired");
   }

    // Check OTP match
    if (!otpData.getOtp().equals(request.getOtp())) {
        throw new RuntimeException("Invalid OTP");
    }

    // Check expiry
    if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) {
        otpRepository.delete(otpData);
        throw new RuntimeException("OTP expired");
    }

    // Find user
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Verify user
    user.setVerified(true);
    userRepository.save(user);

    // Delete OTP after successful verification
    otpRepository.delete(otpData);

    return "Email verified successfully";
}

public String resendOtp(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.isVerified()) {
        throw new RuntimeException("Email already verified");
    }

    String otp = OtpUtil.generateOtp();

    OtpVerification otpEntity = otpRepository.findByEmail(email)
            .orElse(
                    OtpVerification.builder()
                            .email(email)
                            .build()
            );

    otpEntity.setOtp(otp);
    otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

    otpRepository.save(otpEntity);

    emailService.sendOtp(email, otp);

    return "OTP sent to email";
}

public String requestVerification(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Account does not exist"));

    if (user.isVerified()) {
        throw new RuntimeException("Account already verified");
    }

    String otp = OtpUtil.generateOtp();

    OtpVerification otpEntity = otpRepository.findByEmail(email)
            .orElse(
                    OtpVerification.builder()
                            .email(email)
                            .build()
            );

    otpEntity.setOtp(otp);
    otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

    otpRepository.save(otpEntity);

    emailService.sendOtp(email, otp);

    return "OTP sent to email";
}

}
