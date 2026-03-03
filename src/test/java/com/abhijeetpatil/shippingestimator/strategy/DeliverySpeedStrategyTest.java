package com.abhijeetpatil.shippingestimator.strategy;

import com.abhijeetpatil.shippingestimator.strategy.delivery.ExpressDeliveryStrategy;
import com.abhijeetpatil.shippingestimator.strategy.delivery.StandardDeliveryStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for delivery speed strategies to verify final charge calculations.
 */
@DisplayName("Delivery Speed Strategy Tests")
class DeliverySpeedStrategyTest {

    @Test
    @DisplayName("Standard delivery should add base ₹10 courier charge")
    void standardDeliveryShouldAddBaseCourierCharge() {
        // Arrange
        StandardDeliveryStrategy strategy = new StandardDeliveryStrategy();
        double baseCharge = 1500.0;
        double weight = 10.0;

        // Act
        double finalCharge = strategy.calculateFinalCharge(baseCharge, weight);

        // Assert - ₹1,500 + ₹10 = ₹1,510
        assertThat(finalCharge).isEqualTo(1510.0);
    }

    @Test
    @DisplayName("Express delivery should add base ₹10 + ₹1.2 per kg")
    void expressDeliveryShouldAddExtraCharge() {
        // Arrange
        ExpressDeliveryStrategy strategy = new ExpressDeliveryStrategy();
        double baseCharge = 1500.0;
        double weight = 10.0;

        // Act
        double finalCharge = strategy.calculateFinalCharge(baseCharge, weight);

        // Assert - ₹1,500 + ₹10 + (10 * ₹1.2) = ₹1,522
        assertThat(finalCharge).isEqualTo(1522.0);
    }

    @Test
    @DisplayName("Express delivery extra charge should scale with weight")
    void expressChargeScalesWithWeight() {
        ExpressDeliveryStrategy strategy = new ExpressDeliveryStrategy();
        
        // Test with different weights
        double result1 = strategy.calculateFinalCharge(1000, 5);  // 1000 + 10 + 6 = 1016
        double result2 = strategy.calculateFinalCharge(1000, 25); // 1000 + 10 + 30 = 1040
        
        assertThat(result1).isEqualTo(1016.0);
        assertThat(result2).isEqualTo(1040.0);
    }

    @Test
    @DisplayName("Should return correct speed names")
    void shouldReturnCorrectSpeedNames() {
        assertThat(new StandardDeliveryStrategy().getDeliverySpeed()).isEqualTo("Standard");
        assertThat(new ExpressDeliveryStrategy().getDeliverySpeed()).isEqualTo("Express");
    }

    @Test
    @DisplayName("Standard delivery charge should be independent of weight")
    void standardDeliveryIndependentOfWeight() {
        StandardDeliveryStrategy strategy = new StandardDeliveryStrategy();
        double baseCharge = 2000.0;
        
        // Courier charge should be same regardless of weight
        assertThat(strategy.calculateFinalCharge(baseCharge, 5))
            .isEqualTo(strategy.calculateFinalCharge(baseCharge, 50));
    }

    @Test
    @DisplayName("Should handle fractional weights in express delivery")
    void shouldHandleFractionalWeights() {
        ExpressDeliveryStrategy strategy = new ExpressDeliveryStrategy();
        
        // 1000 + 10 + (7.5 * 1.2) = 1019
        double result = strategy.calculateFinalCharge(1000, 7.5);
        
        assertThat(result).isEqualTo(1019.0);
    }
}
