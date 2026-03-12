package com.wealthmanager.controller;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.User;

import com.wealthmanager.service.IUserServiceAuth;
import com.wealthmanager.service.impl.InvestmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/investments")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestmentService investmentService;
    private final IUserServiceAuth userService;

    @PostMapping("/buy")
public String buyStock(
        @RequestBody InvestmentRequestDTO request,
        Authentication authentication
) {

    String email = authentication.getName();

    User user = userService.findByEmail(email);

    investmentService.buyStock(request, user);

    return "Stock purchased";
}

  @PostMapping("/sell")
public String sellStock(
        @RequestBody InvestmentRequestDTO request,
        Authentication authentication
) {

    String email = authentication.getName();

    User user = userService.findByEmail(email);

    investmentService.sellStock(request, user);

    return "Stock sold";
}
}