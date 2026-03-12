package com.wealthmanager.service.impl;

import com.wealthmanager.dto.DashboardResponseDTO;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.wealthmanager.service.IDashboardService;

@Service
@RequiredArgsConstructor
public class PortfolioScheduler {

    private final UserRepository userRepository;
    private final IDashboardService dashboardService;

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void updatePortfolios() {
        System.out.println("Updating portfolios...");

        for (User user : userRepository.findAll()) {

            DashboardResponseDTO dashboard =
                    dashboardService.getDashboard(user);

        }

    }
}