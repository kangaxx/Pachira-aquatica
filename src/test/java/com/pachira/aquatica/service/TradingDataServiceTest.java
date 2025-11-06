package com.pachira.aquatica.service;

import com.pachira.aquatica.model.MarketSummary;
import com.pachira.aquatica.model.TradingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TradingDataServiceTest {

    private TradingDataService tradingDataService;
    private SimpMessagingTemplate messagingTemplate;

    @BeforeEach
    void setUp() {
        messagingTemplate = mock(SimpMessagingTemplate.class);
        tradingDataService = new TradingDataService(messagingTemplate);
    }

    @Test
    void getAllTradingData_ShouldReturnNonEmptyList() {
        List<TradingData> allData = tradingDataService.getAllTradingData();
        
        assertNotNull(allData);
        assertFalse(allData.isEmpty());
    }

    @Test
    void getTradingDataBySymbol_WhenExists_ShouldReturnData() {
        Optional<TradingData> data = tradingDataService.getTradingDataBySymbol("AAPL");
        
        assertTrue(data.isPresent());
        assertEquals("AAPL", data.get().getSymbol());
        assertNotNull(data.get().getPrice());
    }

    @Test
    void getTradingDataBySymbol_WhenNotExists_ShouldReturnEmpty() {
        Optional<TradingData> data = tradingDataService.getTradingDataBySymbol("INVALID_SYMBOL");
        
        assertFalse(data.isPresent());
    }

    @Test
    void getMarketSummary_ShouldReturnValidSummary() {
        MarketSummary summary = tradingDataService.getMarketSummary();
        
        assertNotNull(summary);
        assertTrue(summary.getTotalSymbols() > 0);
        assertEquals(summary.getTotalSymbols(), 
            summary.getAdvancers() + summary.getDecliners() + summary.getUnchanged());
        assertNotNull(summary.getTotalVolume());
        assertNotNull(summary.getTimestamp());
    }
}
