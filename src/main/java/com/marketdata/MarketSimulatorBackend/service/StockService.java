package com.marketdata.MarketSimulatorBackend.service;

import com.marketdata.MarketSimulatorBackend.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockService {
    private final StockServiceProvider stockPriceProvider;
    private final Map<String, List<StockPrice>> cache = new ConcurrentHashMap<>();
    private LocalDate lastUpdate = null; // Tracks when the cache was last updated

    @Autowired
    public StockService(StockServiceProvider stockPriceProvider) {
        this.stockPriceProvider = stockPriceProvider;
    }

    public List<StockPrice> getStockPrices(List<String> symbols) {
        // Check if the cache needs updating (once per day)
        if (lastUpdate == null || !lastUpdate.equals(LocalDate.now())) {
            updateCache(symbols); // Refresh the cache
        }

        // Serve data from the cache
        List<StockPrice> result = new ArrayList<>();
        for (String symbol : symbols) {
            if (cache.containsKey(symbol)) {
                result.addAll(cache.get(symbol));
            }
        }
        return result;
    }

    private void updateCache(List<String> symbols) {
        // Fetch data from the provider and update the cache
        for (String symbol : symbols) {
            List<StockPrice> fetchedData = stockPriceProvider.getStockPrices(List.of(symbol));
            cache.put(symbol, fetchedData);
        }

        // Update the last update time
        lastUpdate = LocalDate.now();
        System.out.println("Cache updated at: " + lastUpdate);
    }
}

