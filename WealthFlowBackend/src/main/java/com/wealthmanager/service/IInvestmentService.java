package com.wealthmanager.service;

import com.wealthmanager.dto.InvestmentRequestDTO;
import com.wealthmanager.entity.User;


public interface IInvestmentService {
        
    void addInvestment(InvestmentRequestDTO request, User user);

    
}
