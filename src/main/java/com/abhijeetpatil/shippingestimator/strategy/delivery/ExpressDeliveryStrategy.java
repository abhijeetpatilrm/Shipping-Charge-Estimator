package com.abhijeetpatil.shippingestimator.strategy.delivery;

import com.abhijeetpatil.shippingestimator.constants.DeliveryConstants;
import org.springframework.stereotype.Component;

@Component
public class ExpressDeliveryStrategy implements DeliverySpeedStrategy {

    @Override
    public double calculateFinalCharge(double baseShippingCharge, double weightInKg) {
        //Base ₹10 + ₹1.2 per kg extra
        return baseShippingCharge + DeliveryConstants.BASE_COURIER_CHARGE + (weightInKg * DeliveryConstants.EXPRESS_EXTRA_PER_KG);
    }

    @Override
    public String getDeliverySpeed() {
        return "Express";
    }
}
