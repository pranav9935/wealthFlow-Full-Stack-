package com.wealthmanager.service.impl;

import com.wealthmanager.dto.*;
import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.InvestmentRepository;
import com.wealthmanager.service.IDashboardService;
import com.wealthmanager.service.IStockPriceService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final InvestmentRepository investmentRepository;
    private final IStockPriceService stockPriceService;
     private final SimpMessagingTemplate messagingTemplate;

    @Override
    public DashboardResponseDTO getDashboard(User user) {

        List<Investment> investments = investmentRepository.findByUser(user);

        double totalInvestment = 0;
        double currentValue = 0;

        List<InvestmentViewDTO> investmentViews = new ArrayList<>();

        for (Investment inv : investments) {

            double currentPrice = round(stockPriceService.getCurrentPrice(inv.getStockSymbol()));

            double investedAmount = round(inv.getBuyPrice() * inv.getQuantity());
            double currentAmount = round(currentPrice * inv.getQuantity());
            double profit = round(currentAmount - investedAmount);

            totalInvestment += investedAmount;
            currentValue += currentAmount;
            System.out.println("Current price for " + inv.getStockSymbol() + " = " + currentPrice);

            investmentViews.add(
                    new InvestmentViewDTO(
                            inv.getStockSymbol(),
                            inv.getStockName(),
                            inv.getQuantity(),
                            round(inv.getBuyPrice()),
                            currentPrice,
                            currentAmount,
                            profit
                    )
            );
        }

        // Round totals before sending to frontend
        totalInvestment = round(totalInvestment);
        currentValue = round(currentValue);
        double totalProfit = round(currentValue - totalInvestment);

   DashboardResponseDTO response = new DashboardResponseDTO(
        totalInvestment,
        currentValue,
        totalProfit,
        investmentViews
);
System.out.println("Sending update to websocket");

messagingTemplate.convertAndSend("/topic/portfolio/" + user.getEmail(), response);
return response;
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}