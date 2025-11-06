package com.pachira.aquatica.controller;

import com.pachira.aquatica.model.MarketSummary;
import com.pachira.aquatica.model.TradingData;
import com.pachira.aquatica.service.TradingDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API controller for trading data
 * Provides endpoints for PC web, WeChat Mini Programs, and apps
 */
@RestController
@RequestMapping("/api/v1/trading")
@CrossOrigin(origins = "*")
public class TradingDataController {

    private final TradingDataService tradingDataService;

    public TradingDataController(TradingDataService tradingDataService) {
        this.tradingDataService = tradingDataService;
    }

    /**
     * Get all trading data
     * GET /api/v1/trading/data
     */
    @GetMapping("/data")
    public ResponseEntity<List<TradingData>> getAllTradingData() {
        return ResponseEntity.ok(tradingDataService.getAllTradingData());
    }

    /**
     * Get trading data by symbol
     * GET /api/v1/trading/data/{symbol}
     */
    @GetMapping("/data/{symbol}")
    public ResponseEntity<TradingData> getTradingDataBySymbol(@PathVariable String symbol) {
        return tradingDataService.getTradingDataBySymbol(symbol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get market summary
     * GET /api/v1/trading/summary
     */
    @GetMapping("/summary")
    public ResponseEntity<MarketSummary> getMarketSummary() {
        return ResponseEntity.ok(tradingDataService.getMarketSummary());
    }

    /**
     * Health check endpoint
     * GET /api/v1/trading/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Pachira Aquatica Trading Service is running");
    }
}
