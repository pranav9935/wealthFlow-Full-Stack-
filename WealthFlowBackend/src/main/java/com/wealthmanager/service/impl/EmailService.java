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
        body.put("to", email);
        body.put("subject", "WealthFlow Email Verification");
        body.put("text", "Your OTP is: " + otp);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, request, String.class);

            System.out.println("Email API response: " + response.getBody());

        } catch (Exception e) {

            System.out.println("Email sending failed:");
            e.printStackTrace();

        }
    }
}