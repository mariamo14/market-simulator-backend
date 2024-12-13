package com.marketdata.MarketSimulatorBackend.api.controller;

import com.marketdata.MarketSimulatorBackend.model.StockPrice;
import com.marketdata.MarketSimulatorBackend.service.StockService;
import com.marketdata.MarketSimulatorBackend.service.StockServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockController {
    private final StockServiceProvider stockServiceProvider;

    @Autowired
    public StockController(StockServiceProvider stockServiceProvider) {
        this.stockServiceProvider = stockServiceProvider;
    }

    @GetMapping("/stocks")
    public List<StockPrice> getStockPrices(@RequestParam List<String> symbols) {
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("Symbols parameter is required.");
        }
        return stockServiceProvider.getStockPrices(symbols);
    }
}
