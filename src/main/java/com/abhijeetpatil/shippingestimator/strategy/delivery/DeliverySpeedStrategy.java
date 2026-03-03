package com.abhijeetpatil.shippingestimator.strategy.delivery;

public interface DeliverySpeedStrategy {

    double calculateFinalCharge(double baseShippingCharge, double weightInKg);

    String getDeliverySpeed();
}
