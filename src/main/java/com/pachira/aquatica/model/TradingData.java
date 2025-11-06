package com.pachira.aquatica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Trading data model representing real-time market data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradingData {
    
    private String symbol;
    private BigDecimal price;
    private BigDecimal volume;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal open;
    private BigDecimal close;
    private LocalDateTime timestamp;
    private String exchange;
    
    /**
     * Calculate price change percentage
     */
    public BigDecimal getChangePercent() {
        if (open == null || open.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return price.subtract(open)
                .divide(open, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"));
    }
}
