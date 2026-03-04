package com.wealthmanager.service.impl;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.InvestmentRepository;
import com.wealthmanager.service.IInvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvestmentService implements IInvestmentService {

    private final InvestmentRepository investmentRepository;

    @Override
    public void addInvestment(InvestmentRequestDTO request, User user) {

        Investment investment = Investment.builder()
                .stockSymbol(request.getStockSymbol())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        investmentRepository.save(investment);
    }
}