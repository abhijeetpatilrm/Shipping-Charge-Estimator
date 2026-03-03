package com.abhijeetpatil.shippingestimator.controller;

import com.abhijeetpatil.shippingestimator.dto.request.SellerShippingRequest;
import com.abhijeetpatil.shippingestimator.dto.response.ApiResponse;
import com.abhijeetpatil.shippingestimator.dto.response.LocationDTO;
import com.abhijeetpatil.shippingestimator.dto.response.SellerShippingResponse;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.service.ShippingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shipping-charge")
public class ShippingController {

    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<SellerShippingResponse>> calculateShippingCharge(
            @Valid @RequestBody SellerShippingRequest request
    ) {
        // Use default weight of 1.0 kg if not provided
        double weight = request.getWeight() != null ? request.getWeight() : 1.0;
        
        // Call service method
        Map<String, Object> result = shippingService.calculateShippingChargeFromSeller(
                request.getSellerId(),
                request.getCustomerId(),
                request.getDeliverySpeed(),
                weight
        );

        // Extract data from result
        double shippingCharge = (Double) result.get("shippingCharge");
        Warehouse warehouse = (Warehouse) result.get("warehouse");

        // Build location DTO
        LocationDTO warehouseLocation = LocationDTO.builder()
                .latitude(warehouse.getLatitude())
                .longitude(warehouse.getLongitude())
                .build();

        // Build response DTO
        SellerShippingResponse response = SellerShippingResponse.builder()
                .shippingCharge(shippingCharge)
                .warehouseId(warehouse.getId())
                .warehouseLocation(warehouseLocation)
                .build();

        // Build API response wrapper
        ApiResponse<SellerShippingResponse> apiResponse = ApiResponse.<SellerShippingResponse>builder()
                .timestamp(LocalDateTime.now())
                .status("success")
                .data(response)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
