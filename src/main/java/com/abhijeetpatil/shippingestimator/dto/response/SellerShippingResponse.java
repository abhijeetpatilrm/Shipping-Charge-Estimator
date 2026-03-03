package com.abhijeetpatil.shippingestimator.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Response DTO for shipping charge calculation including warehouse details.
 * Used for POST /api/v1/shipping-charge/calculate endpoint.
 */
@Getter
@Builder
public class SellerShippingResponse {

    private double shippingCharge;
    private Long warehouseId;
    private LocationDTO warehouseLocation;
}
