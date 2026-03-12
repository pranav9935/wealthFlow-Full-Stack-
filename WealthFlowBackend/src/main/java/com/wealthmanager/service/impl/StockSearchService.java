package com.wealthmanager.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class StockSearchService {

    private final String API_KEY = "d6jtmg9r01qkvh5rausgd6jtmg9r01qkvh5raut0";

    public List<Map<String, Object>> searchStock(String query) {

        String url =
        "https://finnhub.io/api/v1/search?q=" + query + "&token=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();

        Map response = restTemplate.getForObject(url, Map.class);

        return (List<Map<String, Object>>) response.get("result");

    }
}