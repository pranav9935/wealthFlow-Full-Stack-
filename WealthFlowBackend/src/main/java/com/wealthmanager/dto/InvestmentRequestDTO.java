package com.wealthmanager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentRequestDTO {

    private String stockSymbol;
    private Integer quantity;
    private Double buyPrice;

}