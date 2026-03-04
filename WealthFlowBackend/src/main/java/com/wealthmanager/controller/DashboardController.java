package com.wealthmanager.controller;

import com.wealthmanager.entity.User;
import com.wealthmanager.service.IDashboardService;
import com.wealthmanager.service.IUserServiceAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final IDashboardService dashboardService;
    private final IUserServiceAuth userService;

    @GetMapping
    public Object getDashboard(Authentication authentication) {

        String email = authentication.getName();

        User user = userService.findByEmail(email);

        return dashboardService.getDashboard(user);
    }
}