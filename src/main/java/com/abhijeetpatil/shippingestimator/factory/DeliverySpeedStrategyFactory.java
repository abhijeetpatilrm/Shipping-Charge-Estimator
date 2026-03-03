package com.abhijeetpatil.shippingestimator.factory;

import com.abhijeetpatil.shippingestimator.strategy.delivery.DeliverySpeedStrategy;
import com.abhijeetpatil.shippingestimator.strategy.delivery.ExpressDeliveryStrategy;
import com.abhijeetpatil.shippingestimator.strategy.delivery.StandardDeliveryStrategy;
import org.springframework.stereotype.Component;

@Component
public class DeliverySpeedStrategyFactory {

    private final StandardDeliveryStrategy standardDeliveryStrategy;
    private final ExpressDeliveryStrategy expressDeliveryStrategy;

    public DeliverySpeedStrategyFactory(StandardDeliveryStrategy standardDeliveryStrategy, ExpressDeliveryStrategy expressDeliveryStrategy) {
        this.standardDeliveryStrategy = standardDeliveryStrategy;
        this.expressDeliveryStrategy = expressDeliveryStrategy;
    }

    public DeliverySpeedStrategy getStrategy(String deliverySpeed) {
        if (deliverySpeed == null || deliverySpeed.isBlank()) {
            throw new IllegalArgumentException("Delivery speed must not be null or blank");
        }

        String normalizedSpeed = deliverySpeed.trim().toLowerCase();

        switch (normalizedSpeed) {
            case "standard":
                return standardDeliveryStrategy;
            case "express":
                return expressDeliveryStrategy;
            default:
                throw new IllegalArgumentException("Invalid delivery speed: " + deliverySpeed);
        }
    }
}