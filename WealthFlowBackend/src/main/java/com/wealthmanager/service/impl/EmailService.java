package com.wealthmanager.service.impl;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final String API_KEY = System.getenv("RESEND_API_KEY");

    public void sendOtp(String email, String otp) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.resend.com/emails";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        Map<String, Object> body = new HashMap<>();

        body.put("from", "onboarding@resend.dev");

        // send all OTPs to your email
        body.put("to", "pranavmishra9807@gmail.com");

        body.put("subject", "WealthFlow OTP Verification");

        body.put("text",
                "User Email: " + email +
                "\nOTP: " + otp);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, String.class);
    }
}