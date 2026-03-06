package com.wealthmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvestmentViewDTO {

    private String stockSymbol;
     private String stockName;
    private int quantity;
    private double buyPrice;
    private double currentPrice;
    private double profit;

}