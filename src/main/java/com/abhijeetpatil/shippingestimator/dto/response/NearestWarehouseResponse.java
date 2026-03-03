package com.abhijeetpatil.shippingestimator.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NearestWarehouseResponse {

    private Long warehouseId;
    private LocationDTO warehouseLocation;
}
