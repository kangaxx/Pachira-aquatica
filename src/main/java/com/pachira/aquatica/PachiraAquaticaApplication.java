package com.pachira.aquatica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for Pachira Aquatica
 * Real-time quantitative trading data display platform
 */
@SpringBootApplication
@EnableScheduling
public class PachiraAquaticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PachiraAquaticaApplication.class, args);
    }
}
