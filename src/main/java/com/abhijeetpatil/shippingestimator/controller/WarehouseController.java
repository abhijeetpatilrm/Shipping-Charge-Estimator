package com.abhijeetpatil.shippingestimator.controller;

import com.abhijeetpatil.shippingestimator.dto.response.LocationDTO;
import com.abhijeetpatil.shippingestimator.dto.response.NearestWarehouseResponse;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.service.WarehouseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/warehouse")
@Validated
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<NearestWarehouseResponse> getNearestWarehouse(
            @RequestParam @NotNull(message = "Seller ID is required") Long sellerId,
            @RequestParam @NotNull(message = "Product ID is required") Long productId
    ) {
        Warehouse warehouse = warehouseService.findNearestWarehouseForProduct(sellerId, productId);

        LocationDTO location = LocationDTO.builder()
                .latitude(warehouse.getLatitude())
                .longitude(warehouse.getLongitude())
                .build();

        NearestWarehouseResponse response = NearestWarehouseResponse.builder()
                .warehouseId(warehouse.getId())
                .warehouseLocation(location)
                .build();

        return ResponseEntity.ok(response);
    }
}
