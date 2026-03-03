package com.abhijeetpatil.shippingestimator.factory;

import com.abhijeetpatil.shippingestimator.strategy.delivery.DeliverySpeedStrategy;
import com.abhijeetpatil.shippingestimator.strategy.delivery.ExpressDeliveryStrategy;
import com.abhijeetpatil.shippingestimator.strategy.delivery.StandardDeliveryStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for delivery speed factory to verify correct strategy selection.
 */
@DisplayName("Delivery Speed Factory Tests")
class DeliverySpeedStrategyFactoryTest {

    private DeliverySpeedStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new DeliverySpeedStrategyFactory(
            new StandardDeliveryStrategy(),
            new ExpressDeliveryStrategy()
        );
    }

    @Test
    @DisplayName("Should select Standard strategy for 'standard' input")
    void shouldSelectStandardStrategy() {
        DeliverySpeedStrategy strategy = factory.getStrategy("standard");
        
        assertThat(strategy).isInstanceOf(StandardDeliveryStrategy.class);
        assertThat(strategy.getDeliverySpeed()).isEqualTo("Standard");
    }

    @Test
    @DisplayName("Should select Express strategy for 'express' input")
    void shouldSelectExpressStrategy() {
        DeliverySpeedStrategy strategy = factory.getStrategy("express");
        
        assertThat(strategy).isInstanceOf(ExpressDeliveryStrategy.class);
        assertThat(strategy.getDeliverySpeed()).isEqualTo("Express");
    }

    @ParameterizedTest
    @DisplayName("Should handle case-insensitive input")
    @ValueSource(strings = {"STANDARD", "Standard", "sTaNdArD", "STANDARD "})
    void shouldHandleCaseInsensitiveStandard(String input) {
        DeliverySpeedStrategy strategy = factory.getStrategy(input);
        
        assertThat(strategy).isInstanceOf(StandardDeliveryStrategy.class);
    }

    @ParameterizedTest
    @DisplayName("Should handle case-insensitive express input")
    @ValueSource(strings = {"EXPRESS", "Express", "eXpReSs", " EXPRESS"})
    void shouldHandleCaseInsensitiveExpress(String input) {
        DeliverySpeedStrategy strategy = factory.getStrategy(input);
        
        assertThat(strategy).isInstanceOf(ExpressDeliveryStrategy.class);
    }

    @Test
    @DisplayName("Should throw exception for invalid delivery speed")
    void shouldThrowExceptionForInvalidSpeed() {
        assertThatThrownBy(() -> factory.getStrategy("overnight"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Invalid delivery speed");
    }

    @Test
    @DisplayName("Should throw exception for null input")
    void shouldThrowExceptionForNullInput() {
        assertThatThrownBy(() -> factory.getStrategy(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should throw exception for empty string")
    void shouldThrowExceptionForEmptyString() {
        assertThatThrownBy(() -> factory.getStrategy(""))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Should throw exception for whitespace only")
    void shouldThrowExceptionForWhitespaceOnly() {
        assertThatThrownBy(() -> factory.getStrategy("   "))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
