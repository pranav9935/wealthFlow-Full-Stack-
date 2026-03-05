package com.wealthmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentRequestDTO {

    private String stockSymbol;

    private String stockName;

    private Integer quantity;

    private Double buyPrice;
}