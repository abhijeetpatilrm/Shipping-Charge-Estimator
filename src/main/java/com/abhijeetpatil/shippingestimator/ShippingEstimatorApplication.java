package com.abhijeetpatil.shippingestimator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point for the Shipping Estimator Application.
 *
 * Enables:
 * - Spring Boot auto-configuration
 * - Component scanning
 * - Caching support
 */
@SpringBootApplication
@EnableCaching
public class ShippingEstimatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingEstimatorApplication.class, args);
    }

}
