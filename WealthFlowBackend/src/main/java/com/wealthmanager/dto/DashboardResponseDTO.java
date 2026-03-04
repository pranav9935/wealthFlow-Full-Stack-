package com.wealthmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardResponseDTO {

    private double totalInvestment;
    private double currentValue;
    private double profitLoss;

    private List<InvestmentViewDTO> investments;
}