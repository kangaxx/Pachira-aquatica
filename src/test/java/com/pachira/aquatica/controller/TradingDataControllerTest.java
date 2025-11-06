package com.pachira.aquatica.controller;

import com.pachira.aquatica.model.TradingData;
import com.pachira.aquatica.service.TradingDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradingDataController.class)
class TradingDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradingDataService tradingDataService;

    @Test
    void getAllTradingData_ShouldReturnListOfTradingData() throws Exception {
        TradingData data1 = new TradingData("AAPL", new BigDecimal("150.00"), 
            new BigDecimal("1000000"), new BigDecimal("155.00"), 
            new BigDecimal("148.00"), new BigDecimal("150.00"), 
            new BigDecimal("150.00"), LocalDateTime.now(), "NASDAQ");
        
        when(tradingDataService.getAllTradingData()).thenReturn(Arrays.asList(data1));

        mockMvc.perform(get("/api/v1/trading/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("AAPL"))
                .andExpect(jsonPath("$[0].price").value(150.00));
    }

    @Test
    void getTradingDataBySymbol_WhenExists_ShouldReturnData() throws Exception {
        TradingData data = new TradingData("GOOGL", new BigDecimal("2800.00"), 
            new BigDecimal("500000"), new BigDecimal("2850.00"), 
            new BigDecimal("2780.00"), new BigDecimal("2800.00"), 
            new BigDecimal("2800.00"), LocalDateTime.now(), "NASDAQ");
        
        when(tradingDataService.getTradingDataBySymbol("GOOGL")).thenReturn(Optional.of(data));

        mockMvc.perform(get("/api/v1/trading/data/GOOGL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol").value("GOOGL"))
                .andExpect(jsonPath("$.price").value(2800.00));
    }

    @Test
    void getTradingDataBySymbol_WhenNotExists_ShouldReturn404() throws Exception {
        when(tradingDataService.getTradingDataBySymbol("INVALID")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/trading/data/INVALID"))
                .andExpect(status().isNotFound());
    }

    @Test
    void healthCheck_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/trading/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pachira Aquatica Trading Service is running"));
    }
}
