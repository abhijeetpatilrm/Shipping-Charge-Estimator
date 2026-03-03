package com.abhijeetpatil.shippingestimator.service;

import com.abhijeetpatil.shippingestimator.entity.Product;
import com.abhijeetpatil.shippingestimator.entity.Seller;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.exception.SellerNotFoundException;
import com.abhijeetpatil.shippingestimator.exception.WarehouseNotFoundException;
import com.abhijeetpatil.shippingestimator.repository.ProductRepository;
import com.abhijeetpatil.shippingestimator.repository.SellerRepository;
import com.abhijeetpatil.shippingestimator.repository.WarehouseRepository;
import com.abhijeetpatil.shippingestimator.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Tests for WarehouseService to verify nearest warehouse calculation logic.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Warehouse Service Tests")
class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    private Seller testSeller;
    private Warehouse nearWarehouse;
    private Warehouse farWarehouse;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Seller in Bangalore
        testSeller = Seller.builder()
                .id(1L)
                .name("Test Seller")
                .latitude(12.9716)
                .longitude(77.5946)
                .build();

        // Warehouse 1: Same location as seller (0 km)
        nearWarehouse = Warehouse.builder()
                .id(1L)
                .name("Near Warehouse")
                .latitude(12.9716)
                .longitude(77.5946)
                .build();

        // Warehouse 2: Mumbai (~850 km away)
        farWarehouse = Warehouse.builder()
                .id(2L)
                .name("Far Warehouse")
                .latitude(19.0760)
                .longitude(72.8777)
                .build();

        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .seller(testSeller)
                .build();
    }

    @Test
    @DisplayName("Should find nearest warehouse by seller ID")
    void shouldFindNearestWarehouseBySellerId() {
        // Arrange
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(testSeller));
        when(warehouseRepository.findAll()).thenReturn(Arrays.asList(nearWarehouse, farWarehouse));

        // Act
        Warehouse result = warehouseService.findNearestWarehouseBySellerId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Near Warehouse");
        
        verify(sellerRepository).findById(1L);
        verify(warehouseRepository).findAll();
    }

    @Test
    @DisplayName("Should throw exception when seller not found")
    void shouldThrowExceptionWhenSellerNotFound() {
        // Arrange
        when(sellerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> warehouseService.findNearestWarehouseBySellerId(999L))
                .isInstanceOf(SellerNotFoundException.class)
                .hasMessageContaining("Seller not found with id: 999");
        
        verify(sellerRepository).findById(999L);
        verifyNoInteractions(warehouseRepository);
    }

    @Test
    @DisplayName("Should throw exception when no warehouses exist")
    void shouldThrowExceptionWhenNoWarehouses() {
        // Arrange
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(testSeller));
        when(warehouseRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> warehouseService.findNearestWarehouseBySellerId(1L))
                .isInstanceOf(WarehouseNotFoundException.class)
                .hasMessageContaining("No warehouses available");
    }

    @Test
    @DisplayName("Should find nearest warehouse for product")
    void shouldFindNearestWarehouseForProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(testSeller));
        when(warehouseRepository.findAll()).thenReturn(Arrays.asList(farWarehouse, nearWarehouse));

        // Act
        Warehouse result = warehouseService.findNearestWarehouseForProduct(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should select warehouse with minimum distance")
    void shouldSelectWarehouseWithMinimumDistance() {
        // Arrange - Three warehouses at different distances
        Warehouse warehouse1 = Warehouse.builder()
                .id(1L).name("W1").latitude(12.9716).longitude(77.5946).build();
        
        Warehouse warehouse2 = Warehouse.builder()
                .id(2L).name("W2").latitude(13.0827).longitude(80.2707).build(); // ~290 km
        
        Warehouse warehouse3 = Warehouse.builder()
                .id(3L).name("W3").latitude(19.0760).longitude(72.8777).build(); // ~850 km

        when(sellerRepository.findById(1L)).thenReturn(Optional.of(testSeller));
        when(warehouseRepository.findAll()).thenReturn(Arrays.asList(warehouse3, warehouse2, warehouse1));

        // Act
        Warehouse result = warehouseService.findNearestWarehouseBySellerId(1L);

        // Assert - Should select W1 (closest)
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("W1");
    }
}
