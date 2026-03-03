package com.abhijeetpatil.shippingestimator.exception;

import com.abhijeetpatil.shippingestimator.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for global exception handler to verify proper error responses.
 */
@DisplayName("Global Exception Handler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle ProductNotFoundException with 404")
    void shouldHandleProductNotFoundException() {
        // Arrange
        ProductNotFoundException exception = new ProductNotFoundException(1L);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("error");
        assertThat(response.getBody().getMessage()).contains("Product not found");
    }

    @Test
    @DisplayName("Should handle CustomerNotFoundException with 404")
    void shouldHandleCustomerNotFoundException() {
        // Arrange
        CustomerNotFoundException exception = new CustomerNotFoundException(99L);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("Customer not found");
    }

    @Test
    @DisplayName("Should handle SellerNotFoundException with 404")
    void shouldHandleSellerNotFoundException() {
        // Arrange
        SellerNotFoundException exception = new SellerNotFoundException(5L);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).contains("Seller not found");
    }

    @Test
    @DisplayName("Should handle WarehouseNotFoundException with 404")
    void shouldHandleWarehouseNotFoundException() {
        // Arrange
        WarehouseNotFoundException exception = new WarehouseNotFoundException("No warehouses available");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).contains("No warehouses available");
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException with 400")
    void shouldHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Weight must be positive");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgument(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("Weight must be positive");
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with field errors")
    void shouldHandleMethodArgumentNotValidException() {
        // Arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        
        FieldError fieldError1 = new FieldError("sellerShippingRequest", "sellerId", "Seller ID is required");
        FieldError fieldError2 = new FieldError("sellerShippingRequest", "customerId", "Customer ID is required");
        
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleValidationException(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Validation failed");
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody().getErrors();
        assertThat(errors).containsKeys("sellerId", "customerId");
    }

    @Test
    @DisplayName("Should handle ConstraintViolationException with parameter errors")
    void shouldHandleConstraintViolationException() {
        // Arrange
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleConstraintViolation(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Validation failed");
    }

    @Test
    @DisplayName("Should handle generic Exception with 500")
    void shouldHandleGenericException() {
        // Arrange
        Exception exception = new Exception("Unexpected error occurred");

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleGenericException(exception);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("An unexpected error occurred");
    }

    @Test
    @DisplayName("Error response should include timestamp")
    void errorResponseShouldIncludeTimestamp() {
        // Arrange
        ProductNotFoundException exception = new ProductNotFoundException(1L);

        // Act
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNotFound(exception);

        // Assert
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTimestamp()).isNotNull();
    }

    @Test
    @DisplayName("Should handle all entity not found exceptions uniformly")
    void shouldHandleAllNotFoundExceptionsUniformly() {
        // Test all 4 not found exceptions return same status code
        assertThat(exceptionHandler.handleNotFound(new ProductNotFoundException(1L)).getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);
        
        assertThat(exceptionHandler.handleNotFound(new CustomerNotFoundException(1L)).getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);
        
        assertThat(exceptionHandler.handleNotFound(new SellerNotFoundException(1L)).getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);
        
        assertThat(exceptionHandler.handleNotFound(new WarehouseNotFoundException("test")).getStatusCode())
            .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
