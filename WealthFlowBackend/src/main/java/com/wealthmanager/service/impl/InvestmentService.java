package com.wealthmanager.service.impl;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.Investment;
import com.wealthmanager.entity.User;
import com.wealthmanager.repository.InvestmentRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvestmentService  {

    private final InvestmentRepository investmentRepository;

 public void buyStock(InvestmentRequestDTO request, User user) {

    Optional<Investment> existing =
            investmentRepository.findByUserAndStockSymbol(user, request.getStockSymbol());

    if(existing.isPresent()) {

        Investment inv = existing.get();

        int newQty = inv.getQuantity() + request.getQuantity();

        double newAvgPrice =
                ((inv.getBuyPrice() * inv.getQuantity())
                + (request.getBuyPrice() * request.getQuantity()))
                / newQty;

        inv.setQuantity(newQty);
        inv.setBuyPrice(newAvgPrice);

        investmentRepository.save(inv);

    } else {

        Investment inv = Investment.builder()
                .stockSymbol(request.getStockSymbol())
                .stockName(request.getStockName())
                .quantity(request.getQuantity())
                .buyPrice(request.getBuyPrice())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        investmentRepository.save(inv);
    }
}

public void sellStock(InvestmentRequestDTO request, User user) {

    Investment inv = investmentRepository
            .findByUserAndStockSymbol(user, request.getStockSymbol())
            .orElseThrow(() -> new RuntimeException("Stock not owned"));

    if(request.getQuantity() > inv.getQuantity()) {
        throw new RuntimeException("You don't have enough quantity");
    }

    int remaining = inv.getQuantity() - request.getQuantity();

    if(remaining == 0) {

        investmentRepository.delete(inv);

    } else {

        inv.setQuantity(remaining);
        investmentRepository.save(inv);

    }
}



}