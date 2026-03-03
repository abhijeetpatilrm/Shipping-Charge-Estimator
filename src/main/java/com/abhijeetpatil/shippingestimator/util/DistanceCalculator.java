package com.abhijeetpatil.shippingestimator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    private DistanceCalculator() {}

    // Haversine formula for geographic distance
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate distance
        double distance = EARTH_RADIUS_KM * c;

        // Round to 2 decimal places
        BigDecimal roundedDistance = BigDecimal.valueOf(distance)
                .setScale(2, RoundingMode.HALF_UP);

        return roundedDistance.doubleValue();
    }
}
