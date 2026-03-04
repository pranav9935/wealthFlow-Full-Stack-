package com.wealthmanager.controller;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.User;
import com.wealthmanager.service.IInvestmentService;
import com.wealthmanager.service.IUserServiceAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final IInvestmentService investmentService;
    private final IUserServiceAuth userService;

    @PostMapping
    public String addInvestment(@RequestBody InvestmentRequestDTO request,
                                Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        investmentService.addInvestment(request, user);

        return "Investment added successfully";
    }
}