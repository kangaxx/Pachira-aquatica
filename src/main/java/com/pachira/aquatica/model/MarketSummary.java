package com.pachira.aquatica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Market summary data model for overview statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketSummary {
    
    private int totalSymbols;
    private int advancers;
    private int decliners;
    private int unchanged;
    private BigDecimal totalVolume;
    private LocalDateTime timestamp;
}
