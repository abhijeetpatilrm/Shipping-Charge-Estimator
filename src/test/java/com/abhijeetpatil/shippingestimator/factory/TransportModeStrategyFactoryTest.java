package com.abhijeetpatil.shippingestimator.factory;

import com.abhijeetpatil.shippingestimator.strategy.transport.AeroplaneStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.MiniVanStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TransportModeStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TruckStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for transport mode factory to verify correct strategy selection based on distance.
 */
@DisplayName("Transport Mode Factory Tests")
class TransportModeStrategyFactoryTest {

    private TransportModeStrategyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TransportModeStrategyFactory(
            new MiniVanStrategy(),
            new TruckStrategy(),
            new AeroplaneStrategy()
        );
    }

    @Test
    @DisplayName("Should select MiniVan for short distance (0-100 km)")
    void shouldSelectMiniVanForShortDistance() {
        TransportModeStrategy strategy = factory.getStrategy(50.0);
        
        assertThat(strategy).isInstanceOf(MiniVanStrategy.class);
        assertThat(strategy.getTransportMode()).isEqualTo("Mini Van");
    }

    @Test
    @DisplayName("Should select MiniVan for exactly 100 km")
    void shouldSelectMiniVanForBoundary100Km() {
        TransportModeStrategy strategy = factory.getStrategy(100.0);
        
        assertThat(strategy).isInstanceOf(MiniVanStrategy.class);
    }

    @Test
    @DisplayName("Should select Truck for medium distance (100-500 km)")
    void shouldSelectTruckForMediumDistance() {
        TransportModeStrategy strategy = factory.getStrategy(250.0);
        
        assertThat(strategy).isInstanceOf(TruckStrategy.class);
        assertThat(strategy.getTransportMode()).isEqualTo("Truck");
    }

    @Test
    @DisplayName("Should select Truck for exactly 500 km")
    void shouldSelectTruckForBoundary500Km() {
        TransportModeStrategy strategy = factory.getStrategy(500.0);
        
        assertThat(strategy).isInstanceOf(TruckStrategy.class);
    }

    @Test
    @DisplayName("Should select Aeroplane for long distance (>500 km)")
    void shouldSelectAeroplaneForLongDistance() {
        TransportModeStrategy strategy = factory.getStrategy(750.0);
        
        assertThat(strategy).isInstanceOf(AeroplaneStrategy.class);
        assertThat(strategy.getTransportMode()).isEqualTo("Aeroplane");
    }

    @Test
    @DisplayName("Should select Aeroplane for very long distance")
    void shouldSelectAeroplaneForVeryLongDistance() {
        TransportModeStrategy strategy = factory.getStrategy(2000.0);
        
        assertThat(strategy).isInstanceOf(AeroplaneStrategy.class);
    }

    @Test
    @DisplayName("Should handle zero distance (MiniVan)")
    void shouldHandleZeroDistance() {
        TransportModeStrategy strategy = factory.getStrategy(0.0);
        
        assertThat(strategy).isInstanceOf(MiniVanStrategy.class);
    }

    @Test
    @DisplayName("Should handle fractional distances")
    void shouldHandleFractionalDistances() {
        assertThat(factory.getStrategy(99.9)).isInstanceOf(MiniVanStrategy.class);
        assertThat(factory.getStrategy(100.1)).isInstanceOf(TruckStrategy.class);
        assertThat(factory.getStrategy(500.01)).isInstanceOf(AeroplaneStrategy.class);
    }
}
