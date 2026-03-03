package com.abhijeetpatil.shippingestimator.strategy;

import com.abhijeetpatil.shippingestimator.strategy.transport.AeroplaneStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.MiniVanStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TruckStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for transport mode strategies to verify rate calculations.
 */
@DisplayName("Transport Mode Strategy Tests")
class TransportModeStrategyTest {

    @Test
    @DisplayName("MiniVan should calculate charge at ₹3 per km per kg")
    void miniVanShouldCalculateCorrectCharge() {
        // Arrange
        MiniVanStrategy strategy = new MiniVanStrategy();
        double distance = 50.0; // km
        double weight = 10.0;   // kg

        // Act
        double charge = strategy.calculateCharge(distance, weight);

        // Assert - 50 km * 10 kg * ₹3 = ₹1,500
        assertThat(charge).isEqualTo(1500.0);
    }

    @Test
    @DisplayName("Truck should calculate charge at ₹2 per km per kg")
    void truckShouldCalculateCorrectCharge() {
        // Arrange
        TruckStrategy strategy = new TruckStrategy();
        double distance = 250.0;
        double weight = 25.0;

        // Act
        double charge = strategy.calculateCharge(distance, weight);

        // Assert - 250 km * 25 kg * ₹2 = ₹12,500
        assertThat(charge).isEqualTo(12500.0);
    }

    @Test
    @DisplayName("Aeroplane should calculate charge at ₹1 per km per kg")
    void aeroplaneShouldCalculateCorrectCharge() {
        // Arrange
        AeroplaneStrategy strategy = new AeroplaneStrategy();
        double distance = 1000.0;
        double weight = 5.0;

        // Act
        double charge = strategy.calculateCharge(distance, weight);

        // Assert - 1000 km * 5 kg * ₹1 = ₹5,000
        assertThat(charge).isEqualTo(5000.0);
    }

    @ParameterizedTest
    @DisplayName("All transport modes should handle fractional values")
    @CsvSource({
        "50.5, 10.5, 1590.75",  // MiniVan: 50.5 * 10.5 * 3
        "100.25, 15.5, 3107.75", // Truck: 100.25 * 15.5 * 2
        "500.75, 2.5, 1251.875"  // Aeroplane: 500.75 * 2.5 * 1
    })
    void shouldHandleFractionalValues(double distance, double weight, double expected) {
        // Test all strategies with fractional inputs
        assertThat(new MiniVanStrategy().calculateCharge(50.5, 10.5)).isEqualTo(1590.75);
        assertThat(new TruckStrategy().calculateCharge(100.25, 15.5)).isEqualTo(3107.75);
        assertThat(new AeroplaneStrategy().calculateCharge(500.75, 2.5)).isEqualTo(1251.875);
    }

    @Test
    @DisplayName("Transport modes should return correct mode names")
    void shouldReturnCorrectModeNames() {
        assertThat(new MiniVanStrategy().getTransportMode()).isEqualTo("Mini Van");
        assertThat(new TruckStrategy().getTransportMode()).isEqualTo("Truck");
        assertThat(new AeroplaneStrategy().getTransportMode()).isEqualTo("Aeroplane");
    }

    @Test
    @DisplayName("Should calculate zero charge for zero distance or weight")
    void shouldHandleZeroValues() {
        MiniVanStrategy strategy = new MiniVanStrategy();
        
        assertThat(strategy.calculateCharge(0, 10)).isEqualTo(0);
        assertThat(strategy.calculateCharge(50, 0)).isEqualTo(0);
        assertThat(strategy.calculateCharge(0, 0)).isEqualTo(0);
    }
}
