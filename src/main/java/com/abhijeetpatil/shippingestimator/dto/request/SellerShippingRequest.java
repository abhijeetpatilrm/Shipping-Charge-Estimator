package com.abhijeetpatil.shippingestimator.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for calculating shipping charge from seller to customer.
 * Used for POST /api/v1/shipping-charge/calculate endpoint.
 * Weight is optional and defaults to 1.0 kg if not provided.
 */
@Getter
@Setter
public class SellerShippingRequest {

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Delivery speed is required")
    private String deliverySpeed;

    @Positive(message = "Weight must be greater than zero")
    private Double weight = 1.0; // Default weight: 1.0 kg
}
