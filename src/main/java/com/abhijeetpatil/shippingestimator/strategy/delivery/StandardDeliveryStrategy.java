package com.abhijeetpatil.shippingestimator.strategy.delivery;

import com.abhijeetpatil.shippingestimator.constants.DeliveryConstants;
import org.springframework.stereotype.Component;

@Component
public class StandardDeliveryStrategy implements DeliverySpeedStrategy {

    @Override
    public double calculateFinalCharge(double baseShippingCharge, double weightInKg) {
        return baseShippingCharge + DeliveryConstants.BASE_COURIER_CHARGE;  // Base ₹10
    }

    @Override
    public String getDeliverySpeed() {
        return "Standard";
    }
}
