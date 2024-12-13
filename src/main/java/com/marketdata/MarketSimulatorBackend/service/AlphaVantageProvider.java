package com.marketdata.MarketSimulatorBackend.service;

import com.marketdata.MarketSimulatorBackend.model.StockPrice;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlphaVantageProvider implements StockServiceProvider {

    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final String API_KEY = "OYPLC5RZ9VIXXVG5"; // Replace with your actual key

    @Override
    public List<StockPrice> getStockPrices(List<String> symbols) {
        List<StockPrice> stockPrices = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        for (String symbol : symbols) {
            try {
                String url = String.format(
                        "%s?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s",
                        ALPHA_VANTAGE_URL, symbol, API_KEY
                );

                String response = restTemplate.getForObject(url, String.class);
                JSONObject json = new JSONObject(response);
                JSONObject timeSeries = json.getJSONObject("Time Series (Daily)");

                // Extract the last 7 days of data
                timeSeries.keys().forEachRemaining(date -> {
                    JSONObject dailyData = timeSeries.getJSONObject(date);
                    stockPrices.add(new StockPrice(
                            symbol,
                            dailyData.getDouble("4. close"), // Closing price
                            date
                    ));
                });

            } catch (Exception e) {
                System.err.println("Error fetching data for symbol " + symbol + ": " + e.getMessage());
            }
        }
        return stockPrices;
    }
}
