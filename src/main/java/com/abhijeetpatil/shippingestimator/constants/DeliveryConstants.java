package com.abhijeetpatil.shippingestimator.constants;

/**
 * Constants for delivery speed pricing.
 * Contains base courier charges and additional fees for express delivery.
 */
public final class DeliveryConstants {

    // Base courier charge (₹) - applied to all deliveries
    public static final double BASE_COURIER_CHARGE = 10.0;

    // Express delivery extra charge (₹ per kg)
    public static final double EXPRESS_EXTRA_PER_KG = 1.2;

    private DeliveryConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }
}
