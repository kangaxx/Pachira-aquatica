package com.pachira.aquatica.service;

import com.pachira.aquatica.model.MarketSummary;
import com.pachira.aquatica.model.TradingData;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for managing trading data
 * Simulates real-time market data updates
 */
@Service
public class TradingDataService {

    private final Map<String, TradingData> tradingDataStore = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final SimpMessagingTemplate messagingTemplate;
    
    private static final String[] SYMBOLS = {
        "AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", 
        "META", "NVDA", "JPM", "V", "WMT"
    };

    public TradingDataService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
        initializeTradingData();
    }

    /**
     * Initialize trading data with sample values
     */
    private void initializeTradingData() {
        for (String symbol : SYMBOLS) {
            BigDecimal basePrice = new BigDecimal(100 + random.nextInt(900));
            tradingDataStore.put(symbol, new TradingData(
                symbol,
                basePrice,
                new BigDecimal(random.nextInt(1000000)),
                basePrice.multiply(new BigDecimal("1.05")),
                basePrice.multiply(new BigDecimal("0.95")),
                basePrice,
                basePrice,
                LocalDateTime.now(),
                "NASDAQ"
            ));
        }
    }

    /**
     * Get all trading data
     */
    public List<TradingData> getAllTradingData() {
        return new ArrayList<>(tradingDataStore.values());
    }

    /**
     * Get trading data by symbol
     */
    public Optional<TradingData> getTradingDataBySymbol(String symbol) {
        return Optional.ofNullable(tradingDataStore.get(symbol));
    }

    /**
     * Get market summary
     */
    public MarketSummary getMarketSummary() {
        List<TradingData> allData = getAllTradingData();
        int advancers = 0;
        int decliners = 0;
        int unchanged = 0;
        BigDecimal totalVolume = BigDecimal.ZERO;

        for (TradingData data : allData) {
            totalVolume = totalVolume.add(data.getVolume());
            BigDecimal change = data.getPrice().subtract(data.getOpen());
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                advancers++;
            } else if (change.compareTo(BigDecimal.ZERO) < 0) {
                decliners++;
            } else {
                unchanged++;
            }
        }

        return new MarketSummary(
            allData.size(),
            advancers,
            decliners,
            unchanged,
            totalVolume,
            LocalDateTime.now()
        );
    }

    /**
     * Update trading data periodically (every 2 seconds)
     * Simulates real-time market data updates
     */
    @Scheduled(fixedRate = 2000)
    public void updateTradingData() {
        tradingDataStore.forEach((symbol, data) -> {
            // Simulate price changes
            BigDecimal priceChange = new BigDecimal(random.nextDouble() * 10 - 5);
            BigDecimal newPrice = data.getPrice().add(priceChange);
            
            // Ensure price stays positive
            if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
                newPrice = data.getPrice();
            }
            
            data.setPrice(newPrice);
            data.setVolume(new BigDecimal(random.nextInt(1000000)));
            data.setTimestamp(LocalDateTime.now());
            
            // Update high/low
            if (newPrice.compareTo(data.getHigh()) > 0) {
                data.setHigh(newPrice);
            }
            if (newPrice.compareTo(data.getLow()) < 0) {
                data.setLow(newPrice);
            }
            
            // Send update via WebSocket
            messagingTemplate.convertAndSend("/topic/trading/" + symbol, data);
        });
        
        // Send market summary update
        messagingTemplate.convertAndSend("/topic/market-summary", getMarketSummary());
    }
}
