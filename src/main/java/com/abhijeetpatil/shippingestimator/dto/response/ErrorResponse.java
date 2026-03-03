package com.abhijeetpatil.shippingestimator.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO for error responses.
 */
@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String status;
    private String message;
    private Object errors;
}
