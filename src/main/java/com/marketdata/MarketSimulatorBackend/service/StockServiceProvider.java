package com.marketdata.MarketSimulatorBackend.service;

import com.marketdata.MarketSimulatorBackend.model.StockPrice;

import java.util.List;

public interface StockServiceProvider {
    List<StockPrice> getStockPrices(List<String> symbols);
}
