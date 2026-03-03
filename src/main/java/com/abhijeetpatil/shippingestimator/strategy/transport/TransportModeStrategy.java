package com.abhijeetpatil.shippingestimator.strategy.transport;

public interface TransportModeStrategy {

    double calculateCharge(double distanceInKm, double weightInKg);

    String getTransportMode();
}
