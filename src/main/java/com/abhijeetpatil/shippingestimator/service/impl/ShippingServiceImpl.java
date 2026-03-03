package com.abhijeetpatil.shippingestimator.service.impl;

import com.abhijeetpatil.shippingestimator.entity.Customer;
import com.abhijeetpatil.shippingestimator.entity.Product;
import com.abhijeetpatil.shippingestimator.entity.Seller;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.exception.CustomerNotFoundException;
import com.abhijeetpatil.shippingestimator.exception.ProductNotFoundException;
import com.abhijeetpatil.shippingestimator.exception.WarehouseNotFoundException;
import com.abhijeetpatil.shippingestimator.factory.DeliverySpeedStrategyFactory;
import com.abhijeetpatil.shippingestimator.factory.TransportModeStrategyFactory;
import com.abhijeetpatil.shippingestimator.repository.CustomerRepository;
import com.abhijeetpatil.shippingestimator.repository.ProductRepository;
import com.abhijeetpatil.shippingestimator.repository.WarehouseRepository;
import com.abhijeetpatil.shippingestimator.service.ShippingService;
import com.abhijeetpatil.shippingestimator.service.WarehouseService;
import com.abhijeetpatil.shippingestimator.strategy.delivery.DeliverySpeedStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.TransportModeStrategy;
import com.abhijeetpatil.shippingestimator.util.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseService warehouseService;
    private final TransportModeStrategyFactory transportModeStrategyFactory;
    private final DeliverySpeedStrategyFactory deliverySpeedStrategyFactory;

    public ShippingServiceImpl(
            ProductRepository productRepository,
            CustomerRepository customerRepository,
            WarehouseRepository warehouseRepository,
            WarehouseService warehouseService,
            TransportModeStrategyFactory transportModeStrategyFactory,
            DeliverySpeedStrategyFactory deliverySpeedStrategyFactory
    ) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
        this.warehouseService = warehouseService;
        this.transportModeStrategyFactory = transportModeStrategyFactory;
        this.deliverySpeedStrategyFactory = deliverySpeedStrategyFactory;
    }

    @Override
    public double calculateShippingCharge(Long productId, Long customerId, String deliverySpeed) {
        // Step 1: Validate inputs
        if (productId == null || customerId == null) {
            throw new IllegalArgumentException("Product ID and Customer ID cannot be null");
        }

        // Step 2: Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        // Step 3: Get seller from product
        Seller seller = product.getSeller();

        // Step 4: Fetch customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Step 5: Find nearest warehouse
        Warehouse warehouse = warehouseService.findNearestWarehouseBySellerId(seller.getId());

        // Step 6: Calculate distance (warehouse → customer)
        double distance = DistanceCalculator.calculateDistance(
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                customer.getLatitude(),
                customer.getLongitude()
        );

        // Step 7: Get transport strategy
        TransportModeStrategy transportStrategy = transportModeStrategyFactory.getStrategy(distance);

        // Step 8: Calculate base shipping charge
        double baseShippingCharge = transportStrategy.calculateCharge(distance, product.getWeight());

        // Step 9: Get delivery strategy
        DeliverySpeedStrategy deliveryStrategy = deliverySpeedStrategyFactory.getStrategy(deliverySpeed);

        // Step 10: Calculate final charge
        double finalCharge = deliveryStrategy.calculateFinalCharge(baseShippingCharge, product.getWeight());

        // Step 11: Return final charge
        return finalCharge;
    }

    @Override
    public double calculateShippingChargeByWarehouse(Long warehouseId, Long customerId, String deliverySpeed, double weight) {
        // Step 1: Validate inputs
        if (warehouseId == null || customerId == null) {
            throw new IllegalArgumentException("Warehouse ID and Customer ID cannot be null");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }

        // Step 2: Fetch warehouse
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + warehouseId));

        // Step 3: Fetch customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        // Step 4: Calculate distance (warehouse → customer)
        double distance = DistanceCalculator.calculateDistance(
                warehouse.getLatitude(),
                warehouse.getLongitude(),
                customer.getLatitude(),
                customer.getLongitude()
        );

        // Step 5: Get transport strategy
        TransportModeStrategy transportStrategy = transportModeStrategyFactory.getStrategy(distance);

        // Step 6: Calculate base shipping charge
        double baseShippingCharge = transportStrategy.calculateCharge(distance, weight);

        // Step 7: Get delivery strategy
        DeliverySpeedStrategy deliveryStrategy = deliverySpeedStrategyFactory.getStrategy(deliverySpeed);

        // Step 8: Calculate final charge
        double finalCharge = deliveryStrategy.calculateFinalCharge(baseShippingCharge, weight);

        // Step 9: Return final charge
        return finalCharge;
    }

    @Override
    public Map<String, Object> calculateShippingChargeFromSeller(Long sellerId, Long customerId, 
                                                                   String deliverySpeed, double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0");
        }

        // Step 2: Fetch seller
        Seller seller = productRepository.findById(sellerId)
                .map(Product::getSeller)
                .orElseThrow(() -> new com.abhijeetpatil.shippingestimator.exception.SellerNotFoundException(
                        "Seller not found with ID: " + sellerId));

        // Step 3: Fetch customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

        // Step 4: Find nearest warehouse to seller
        Warehouse nearestWarehouse = warehouseService.findNearestWarehouseBySellerId(sellerId);

        // Step 5: Calculate distance from warehouse to customer
        double distance = DistanceCalculator.calculateDistance(
                nearestWarehouse.getLatitude(),
                nearestWarehouse.getLongitude(),
                customer.getLatitude(),
                customer.getLongitude()
        );

        // Step 6: Get transport strategy based on distance
        TransportModeStrategy transportStrategy = transportModeStrategyFactory.getStrategy(distance);

        // Step 7: Calculate base shipping charge
        double baseShippingCharge = transportStrategy.calculateCharge(distance, weight);

        // Step 8: Get delivery strategy
        DeliverySpeedStrategy deliveryStrategy = deliverySpeedStrategyFactory.getStrategy(deliverySpeed);

        // Step 9: Calculate final charge
        double finalCharge = deliveryStrategy.calculateFinalCharge(baseShippingCharge, weight);

        // Step 10: Prepare result map
        Map<String, Object> result = new HashMap<>();
        result.put("shippingCharge", finalCharge);
        result.put("warehouse", nearestWarehouse);

        // Step 11: Return result
        return result;
    }
}
