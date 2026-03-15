package com.wealthmanager.service.impl;

import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("pranavmishra9807@gmail.com");
        message.setTo(email);
        message.setSubject("WealthFlow Email Verification");
        message.setText("Your OTP is: " + otp);

        mailSender.send(message);
    }
}