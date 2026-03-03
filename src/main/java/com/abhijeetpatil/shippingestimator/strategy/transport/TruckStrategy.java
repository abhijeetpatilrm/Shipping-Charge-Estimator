package com.abhijeetpatil.shippingestimator.strategy.transport;

import com.abhijeetpatil.shippingestimator.constants.TransportConstants;
import org.springframework.stereotype.Component;

@Component
public class TruckStrategy implements TransportModeStrategy {

    @Override
    public double calculateCharge(double distanceInKm, double weightInKg) {
        return distanceInKm * weightInKg * TransportConstants.TRUCK_RATE;  // ₹2 per km per kg
    }

    @Override
    public String getTransportMode() {
        return "Truck";
    }
}
