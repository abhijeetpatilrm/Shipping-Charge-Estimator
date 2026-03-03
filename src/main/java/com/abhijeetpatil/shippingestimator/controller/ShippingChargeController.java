package com.abhijeetpatil.shippingestimator.controller;

import com.abhijeetpatil.shippingestimator.dto.response.SimpleShippingChargeResponse;
import com.abhijeetpatil.shippingestimator.service.ShippingService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Validated
public class ShippingChargeController {

    private final ShippingService shippingService;

    public ShippingChargeController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping("/shipping-charge")
    public ResponseEntity<SimpleShippingChargeResponse> calculateShippingCharge(
            @RequestParam @NotNull(message = "Warehouse ID is required") Long warehouseId,
            @RequestParam @NotNull(message = "Customer ID is required") Long customerId,
            @RequestParam @NotBlank(message = "Delivery speed is required") String deliverySpeed,
            @RequestParam(defaultValue = "1.0") @Positive(message = "Weight must be greater than zero") double weight
    ) {
        double shippingCharge = shippingService.calculateShippingChargeByWarehouse(
                warehouseId,
                customerId,
                deliverySpeed,
                weight
        );

        SimpleShippingChargeResponse response = SimpleShippingChargeResponse.builder()
                .shippingCharge(shippingCharge)
                .build();

        return ResponseEntity.ok(response);
    }
}
