package com.wealthmanager.service.impl;

import com.wealthmanager.dto.*;
import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.InvestmentRepository;
import com.wealthmanager.service.IDashboardService;
import com.wealthmanager.service.IStockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService {

    private final InvestmentRepository investmentRepository;
    private final IStockPriceService stockPriceService;

    @Override
    public DashboardResponseDTO getDashboard(User user) {

        List<Investment> investments = investmentRepository.findByUser(user);

        double totalInvestment = 0;
        double currentValue = 0;

        List<InvestmentViewDTO> investmentViews = new ArrayList<>();

        for (Investment inv : investments) {

            double currentPrice = stockPriceService.getCurrentPrice(inv.getStockSymbol());

            double investedAmount = inv.getBuyPrice() * inv.getQuantity();
            double currentAmount = currentPrice * inv.getQuantity();

            totalInvestment += investedAmount;
            currentValue += currentAmount;

            investmentViews.add(
                    new InvestmentViewDTO(
                           inv.getStockSymbol(),
                           inv.getStockName(),
                            inv.getQuantity(),
                            inv.getBuyPrice(),
                            currentPrice,
                            currentAmount - investedAmount
                    )
            );
        }

        return new DashboardResponseDTO(
                totalInvestment,
                currentValue,
                currentValue - totalInvestment,
                investmentViews
        );
    }
}