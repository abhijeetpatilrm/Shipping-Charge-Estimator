package com.abhijeetpatil.shippingestimator.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleShippingChargeResponse {

    private double shippingCharge;
}
