package com.abhijeetpatil.shippingestimator.service.impl;

import com.abhijeetpatil.shippingestimator.entity.Product;
import com.abhijeetpatil.shippingestimator.entity.Seller;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.exception.ProductNotFoundException;
import com.abhijeetpatil.shippingestimator.exception.SellerNotFoundException;
import com.abhijeetpatil.shippingestimator.exception.WarehouseNotFoundException;
import com.abhijeetpatil.shippingestimator.repository.ProductRepository;
import com.abhijeetpatil.shippingestimator.repository.SellerRepository;
import com.abhijeetpatil.shippingestimator.repository.WarehouseRepository;
import com.abhijeetpatil.shippingestimator.service.WarehouseService;
import com.abhijeetpatil.shippingestimator.util.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    public WarehouseServiceImpl(
            SellerRepository sellerRepository,
            WarehouseRepository warehouseRepository,
            ProductRepository productRepository
    ) {
        this.sellerRepository = sellerRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Warehouse findNearestWarehouseBySellerId(Long sellerId) {
        if (sellerId == null) {
            throw new IllegalArgumentException("Seller ID cannot be null");
        }

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));

        List<Warehouse> warehouses = warehouseRepository.findAll();

        if (warehouses.isEmpty()) {
            throw new WarehouseNotFoundException("No warehouses available in the system");
        }

        return warehouses.stream()
                .min(Comparator.comparingDouble(warehouse ->
                        DistanceCalculator.calculateDistance(
                                seller.getLatitude(),
                                seller.getLongitude(),
                                warehouse.getLatitude(),
                                warehouse.getLongitude()
                        )
                ))
                .orElseThrow(() -> new WarehouseNotFoundException("Could not determine nearest warehouse"));
    }

    @Override
    public Warehouse findNearestWarehouseForProduct(Long sellerId, Long productId) {
        if (sellerId == null) {
            throw new IllegalArgumentException("Seller ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new IllegalArgumentException("Product does not belong to seller");
        }

        List<Warehouse> warehouses = warehouseRepository.findAll();

        if (warehouses.isEmpty()) {
            throw new WarehouseNotFoundException("No warehouses available in the system");
        }

        return warehouses.stream()
                .min(Comparator.comparingDouble(warehouse ->
                        DistanceCalculator.calculateDistance(
                                seller.getLatitude(),
                                seller.getLongitude(),
                                warehouse.getLatitude(),
                                warehouse.getLongitude()
                        )
                ))
                .orElseThrow(() -> new WarehouseNotFoundException("Could not determine nearest warehouse"));
    }
}
