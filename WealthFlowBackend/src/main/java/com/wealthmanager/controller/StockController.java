package com.wealthmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import com.wealthmanager.service.impl.StockSearchService;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockSearchService stockSearchService;

    @GetMapping("/search")
    public List<Map<String, Object>> searchStocks(@RequestParam String query) {

        return stockSearchService.searchStock(query);

    }

}