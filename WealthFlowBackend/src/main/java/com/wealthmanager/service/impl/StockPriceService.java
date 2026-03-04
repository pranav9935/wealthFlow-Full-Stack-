package com.wealthmanager.service.impl;

import com.wealthmanager.service.IStockPriceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class StockPriceService implements IStockPriceService {

    private final String API_KEY = "d6jtmg9r01qkvh5rausgd6jtmg9r01qkvh5raut0";

    @Override
    public double getCurrentPrice(String symbol) {

        String url = "https://finnhub.io/api/v1/quote?symbol="
                + symbol +
                "&token=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        return Double.parseDouble(response.get("c").toString());
    }
}