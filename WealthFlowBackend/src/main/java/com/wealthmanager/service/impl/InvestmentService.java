package com.wealthmanager.service.impl;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.InvestmentRepository;
import com.wealthmanager.service.IInvestmentService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvestmentService implements IInvestmentService {

    private final InvestmentRepository investmentRepository;

  @Override
public void addInvestment(InvestmentRequestDTO request, User user) {

    Optional<Investment> existingInvestment =
            investmentRepository.findByUserAndStockSymbol(user, request.getStockSymbol());

    if (existingInvestment.isPresent()) {

        Investment inv = existingInvestment.get();

        int oldQty = inv.getQuantity();
        double oldPrice = inv.getBuyPrice();

        int newQty = request.getQuantity();
        double newPrice = request.getBuyPrice();

        int totalQty = oldQty + newQty;

        double avgPrice =
                ((oldQty * oldPrice) + (newQty * newPrice)) / totalQty;

        inv.setQuantity(totalQty);
        inv.setBuyPrice(avgPrice);

        investmentRepository.save(inv);

    } else {

        Investment investment = Investment.builder()
                .stockSymbol(request.getStockSymbol())
                .stockName(request.getStockName())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        investmentRepository.save(investment);

    }

}
}