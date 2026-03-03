package com.abhijeetpatil.shippingestimator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sellers", indexes = {
    @Index(name = "idx_seller_location", columnList = "latitude, longitude"),
    @Index(name = "idx_seller_active", columnList = "active")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Seller name is required")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(length = 15)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Column(length = 500)
    private String businessAddress;

    @Column(length = 15, unique = true)
    private String gstNumber;

    @Column(length = 50)
    @Builder.Default
    private String businessType = "WHOLESALER";

    @Column(precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal rating = BigDecimal.ZERO;

    @Column(nullable = false)
    @Builder.Default
    private Integer totalOrders = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
