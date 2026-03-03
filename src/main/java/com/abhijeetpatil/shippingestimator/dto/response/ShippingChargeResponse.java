package com.abhijeetpatil.shippingestimator.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShippingChargeResponse {

    private Long productId;
    private Long customerId;
    private String deliverySpeed;
    private double shippingCharge;
}
