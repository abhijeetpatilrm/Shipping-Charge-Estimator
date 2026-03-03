package com.abhijeetpatil.shippingestimator.strategy.transport;

import com.abhijeetpatil.shippingestimator.constants.TransportConstants;
import org.springframework.stereotype.Component;

@Component
public class MiniVanStrategy implements TransportModeStrategy {

    @Override
    public double calculateCharge(double distanceInKm, double weightInKg) {
        return distanceInKm * weightInKg * TransportConstants.MINI_VAN_RATE;  // ₹3 per km per kg
    }

    @Override
    public String getTransportMode() {
        return "Mini Van";
    }
}
