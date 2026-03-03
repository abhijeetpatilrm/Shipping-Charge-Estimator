package com.abhijeetpatil.shippingestimator.service;

import com.abhijeetpatil.shippingestimator.entity.Customer;
import com.abhijeetpatil.shippingestimator.entity.Product;
import com.abhijeetpatil.shippingestimator.entity.Seller;
import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import com.abhijeetpatil.shippingestimator.exception.CustomerNotFoundException;
import com.abhijeetpatil.shippingestimator.factory.DeliverySpeedStrategyFactory;
import com.abhijeetpatil.shippingestimator.factory.TransportModeStrategyFactory;
import com.abhijeetpatil.shippingestimator.repository.CustomerRepository;
import com.abhijeetpatil.shippingestimator.repository.ProductRepository;
import com.abhijeetpatil.shippingestimator.repository.WarehouseRepository;
import com.abhijeetpatil.shippingestimator.service.impl.ShippingServiceImpl;
import com.abhijeetpatil.shippingestimator.strategy.delivery.StandardDeliveryStrategy;
import com.abhijeetpatil.shippingestimator.strategy.transport.MiniVanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for ShippingService to verify charge calculation with different strategies.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Shipping Service Tests")
class ShippingServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private TransportModeStrategyFactory transportFactory;

    @Mock
    private DeliverySpeedStrategyFactory deliveryFactory;

    @InjectMocks
    private ShippingServiceImpl shippingService;

    private Product testProduct;
    private Customer testCustomer;
    private Warehouse testWarehouse;
    private Seller testSeller;

    @BeforeEach
    void setUp() {
        testSeller = Seller.builder()
                .id(1L)
                .name("Test Seller")
                .latitude(12.9716)
                .longitude(77.5946)
                .build();

        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .weight(10.0)
                .price(BigDecimal.valueOf(500))
                .seller(testSeller)
                .build();

        testCustomer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .latitude(13.0827)
                .longitude(80.2707)
                .build();

        testWarehouse = Warehouse.builder()
                .id(1L)
                .name("Test Warehouse")
                .latitude(12.9716)
                .longitude(77.5946)
                .build();
    }

    @Test
    @DisplayName("Should calculate shipping charge with warehouse ID")
    void shouldCalculateShippingChargeByWarehouse() {
        // Arrange
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        
        MiniVanStrategy transportStrategy = new MiniVanStrategy();
        StandardDeliveryStrategy deliveryStrategy = new StandardDeliveryStrategy();
        
        when(transportFactory.getStrategy(anyDouble())).thenReturn(transportStrategy);
        when(deliveryFactory.getStrategy(anyString())).thenReturn(deliveryStrategy);

        // Act
        Double result = shippingService.calculateShippingChargeByWarehouse(1L, 1L, "STANDARD", 10.0);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isPositive();
        
        verify(warehouseRepository).findById(1L);
        verify(customerRepository).findById(1L);
        verify(transportFactory).getStrategy(anyDouble());
        verify(deliveryFactory).getStrategy("STANDARD");
    }

    @Test
    @DisplayName("Should throw exception for invalid weight")
    void shouldThrowExceptionForInvalidWeight() {
        // Act & Assert
        assertThatThrownBy(() -> 
            shippingService.calculateShippingChargeByWarehouse(1L, 1L, "STANDARD", -5.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Weight must be positive");
    }

    @Test
    @DisplayName("Should throw exception when customer not found")
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(testWarehouse));
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> 
            shippingService.calculateShippingChargeByWarehouse(1L, 999L, "STANDARD", 10.0))
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    @DisplayName("Should calculate shipping from seller with warehouse info")
    void shouldCalculateShippingFromSeller() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(warehouseService.findNearestWarehouseBySellerId(1L)).thenReturn(testWarehouse);
        
        MiniVanStrategy transportStrategy = new MiniVanStrategy();
        StandardDeliveryStrategy deliveryStrategy = new StandardDeliveryStrategy();
        
        when(transportFactory.getStrategy(anyDouble())).thenReturn(transportStrategy);
        when(deliveryFactory.getStrategy(anyString())).thenReturn(deliveryStrategy);

        // Act
        Map<String, Object> result = shippingService.calculateShippingChargeFromSeller(
            1L, 1L, "STANDARD", 10.0);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).containsKeys("shippingCharge", "warehouse");
        assertThat(result.get("shippingCharge")).isInstanceOf(Double.class);
        assertThat(result.get("warehouse")).isInstanceOf(Warehouse.class);
        
        Double charge = (Double) result.get("shippingCharge");
        assertThat(charge).isPositive();
    }

    @Test
    @DisplayName("Should use default weight when null provided")
    void shouldUseDefaultWeightWhenNull() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(warehouseService.findNearestWarehouseBySellerId(1L)).thenReturn(testWarehouse);
        
        MiniVanStrategy transportStrategy = new MiniVanStrategy();
        StandardDeliveryStrategy deliveryStrategy = new StandardDeliveryStrategy();
        
        when(transportFactory.getStrategy(anyDouble())).thenReturn(transportStrategy);
        when(deliveryFactory.getStrategy(anyString())).thenReturn(deliveryStrategy);

        // Act
        Map<String, Object> result = shippingService.calculateShippingChargeFromSeller(
            1L, 1L, "EXPRESS", null);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.get("shippingCharge")).isNotNull();
    }
}
