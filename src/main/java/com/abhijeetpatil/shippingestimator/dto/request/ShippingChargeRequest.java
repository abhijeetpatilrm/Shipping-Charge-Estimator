package com.abhijeetpatil.shippingestimator.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingChargeRequest {

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Delivery speed is required")
    private String deliverySpeed;
}
