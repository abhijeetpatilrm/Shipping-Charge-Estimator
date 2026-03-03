package com.abhijeetpatil.shippingestimator.strategy.transport;

import com.abhijeetpatil.shippingestimator.constants.TransportConstants;
import org.springframework.stereotype.Component;

@Component
public class AeroplaneStrategy implements TransportModeStrategy {

    @Override
    public double calculateCharge(double distanceInKm, double weightInKg) {
        return distanceInKm * weightInKg * TransportConstants.AEROPLANE_RATE;  // ₹1 per km per kg
    }

    @Override
    public String getTransportMode() {
        return "Aeroplane";
    }
}
