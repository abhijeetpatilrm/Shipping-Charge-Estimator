package com.abhijeetpatil.shippingestimator.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for distance calculation using Haversine formula.
 */
@DisplayName("Distance Calculator Tests")
class DistanceCalculatorTest {

    @Test
    @DisplayName("Should calculate zero distance for same coordinates")
    void shouldCalculateZeroForSameLocation() {
        // Bangalore coordinates
        double lat = 12.9716;
        double lon = 77.5946;

        double distance = DistanceCalculator.calculateDistance(lat, lon, lat, lon);

        assertThat(distance).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should calculate distance between Bangalore and Mumbai")
    void shouldCalculateDistanceBangaloreMumbai() {
        // Bangalore
        double lat1 = 12.9716;
        double lon1 = 77.5946;

        // Mumbai
        double lat2 = 19.0760;
        double lon2 = 72.8777;

        double distance = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Actual distance is approximately 843 km
        assertThat(distance).isBetween(840.0, 850.0);
    }

    @Test
    @DisplayName("Should calculate distance between Delhi and Chennai")
    void shouldCalculateDistanceDelhiChennai() {
        // Delhi
        double lat1 = 28.6139;
        double lon1 = 77.2090;

        // Chennai
        double lat2 = 13.0827;
        double lon2 = 80.2707;

        double distance = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);

        // Actual distance is approximately 1750 km
        assertThat(distance).isBetween(1740.0, 1760.0);
    }

    @ParameterizedTest
    @DisplayName("Distance calculation should be symmetric")
    @CsvSource({
        "12.9716, 77.5946, 19.0760, 72.8777", // Bangalore to Mumbai
        "28.6139, 77.2090, 13.0827, 80.2707", // Delhi to Chennai
        "17.3850, 78.4867, 13.0827, 80.2707"  // Hyderabad to Chennai
    })
    void distanceShouldBeSymmetric(double lat1, double lon1, double lat2, double lon2) {
        double distance1to2 = DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2);
        double distance2to1 = DistanceCalculator.calculateDistance(lat2, lon2, lat1, lon1);

        assertThat(distance1to2).isEqualTo(distance2to1);
    }

    @Test
    @DisplayName("Should handle coordinates at equator")
    void shouldHandleEquatorCoordinates() {
        double distance = DistanceCalculator.calculateDistance(0, 0, 0, 1);

        // 1 degree longitude at equator is approximately 111 km
        assertThat(distance).isBetween(110.0, 112.0);
    }

    @Test
    @DisplayName("Should handle negative coordinates (Southern/Western hemisphere)")
    void shouldHandleNegativeCoordinates() {
        // Arbitrary southern hemisphere coordinates
        double distance = DistanceCalculator.calculateDistance(
            -23.5505, -46.6333,  // São Paulo
            -22.9068, -43.1729   // Rio de Janeiro
        );

        // Distance is approximately 360 km
        assertThat(distance).isPositive();
        assertThat(distance).isBetween(350.0, 370.0);
    }

    @Test
    @DisplayName("Should round result to 2 decimal places")
    void shouldRoundToTwoDecimalPlaces() {
        // Short distance to verify rounding
        double distance = DistanceCalculator.calculateDistance(
            12.9716, 77.5946,
            12.9800, 77.6000
        );

        // Check that result has at most 2 decimal places
        String distanceStr = String.valueOf(distance);
        int decimalIndex = distanceStr.indexOf('.');
        if (decimalIndex != -1) {
            int decimalPlaces = distanceStr.length() - decimalIndex - 1;
            assertThat(decimalPlaces).isLessThanOrEqualTo(2);
        }
    }

    @Test
    @DisplayName("Should calculate short distances accurately")
    void shouldCalculateShortDistances() {
        // Two nearby locations in the same city
        double distance = DistanceCalculator.calculateDistance(
            12.9716, 77.5946,  // Bangalore center
            12.9800, 77.6000   // ~1 km away
        );

        assertThat(distance).isPositive();
        assertThat(distance).isLessThan(2.0);
    }
}
