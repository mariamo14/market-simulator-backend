package com.marketdata.MarketSimulatorBackend.api.controller;

import com.marketdata.MarketSimulatorBackend.model.StockPrice;
import com.marketdata.MarketSimulatorBackend.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockController {

    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    public List<StockPrice> getMockStockPrices() {
        StockService stockService = new StockService();
        return stockService.getMockStockPrices();
    }


}
