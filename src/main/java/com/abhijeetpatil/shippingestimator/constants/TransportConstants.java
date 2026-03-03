package com.abhijeetpatil.shippingestimator.constants;

/**
 * Constants for transport mode pricing and distance thresholds.
 * Contains rate per km per kg for each transport mode and maximum distance ranges.
 */
public final class TransportConstants {

    // Transport rates (₹ per km per kg)
    public static final double MINI_VAN_RATE = 3.0;
    public static final double TRUCK_RATE = 2.0;
    public static final double AEROPLANE_RATE = 1.0;

    // Distance thresholds (km)
    public static final double MINI_VAN_MAX_DISTANCE = 100.0;
    public static final double TRUCK_MAX_DISTANCE = 500.0;

    private TransportConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}
