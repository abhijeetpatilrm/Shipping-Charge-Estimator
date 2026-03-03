package com.abhijeetpatil.shippingestimator.service;

import com.abhijeetpatil.shippingestimator.entity.Warehouse;

public interface WarehouseService {

    Warehouse findNearestWarehouseBySellerId(Long sellerId);

    Warehouse findNearestWarehouseForProduct(Long sellerId, Long productId);
}
