package com.wealthmanager.service.impl;

import com.wealthmanager.dto.DashboardResponseDTO;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.wealthmanager.service.IDashboardService;

@Service
@RequiredArgsConstructor
public class PortfolioScheduler {

    private final UserRepository userRepository;
    private final IDashboardService dashboardService;
    private final StockPriceProducer producer;

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void updatePortfolios() {
       for (User user : userRepository.findAll()) {

            DashboardResponseDTO dashboard =
                    dashboardService.getDashboard(user);

            producer.sendStockPrice(new ObjectMapper()
                    .writeValueAsString(dashboard));

        }

    }

}