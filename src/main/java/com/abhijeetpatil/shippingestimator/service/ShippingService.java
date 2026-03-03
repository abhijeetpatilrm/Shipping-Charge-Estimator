package com.abhijeetpatil.shippingestimator.service;

public interface ShippingService {

    double calculateShippingCharge(Long productId, Long customerId, String deliverySpeed);

    double calculateShippingChargeByWarehouse(Long warehouseId, Long customerId, String deliverySpeed, double weight);

    java.util.Map<String, Object> calculateShippingChargeFromSeller(Long sellerId, Long customerId, String deliverySpeed, double weight);
}
