package com.wealthmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
     @Async
    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom("pranavmishra9807@gmail.com");
    message.setTo(email);
    message.setSubject("WealthFlow Email Verification");
    message.setText("Your OTP is: " + otp);

    mailSender.send(message);
    }
}