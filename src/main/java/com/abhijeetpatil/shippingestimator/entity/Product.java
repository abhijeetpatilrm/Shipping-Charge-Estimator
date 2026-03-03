package com.abhijeetpatil.shippingestimator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "products",
    indexes = {
        @Index(name = "idx_seller_id", columnList = "seller_id"),
        @Index(name = "idx_product_category", columnList = "category"),
        @Index(name = "idx_product_sku", columnList = "sku")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "seller")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @Column(length = 50, unique = true)
    private String sku;

    @Column(length = 1000)
    private String description;

    @Column(length = 50)
    @Builder.Default
    private String category = "GENERAL";

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Positive(message = "Weight must be positive")
    @Column(nullable = false)
    private double weight;

    @Positive(message = "Length must be positive")
    @Column(nullable = false)
    private double length;

    @Positive(message = "Width must be positive")
    @Column(nullable = false)
    private double width;

    @Positive(message = "Height must be positive")
    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    @Builder.Default
    private Integer stockQuantity = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer minStockLevel = 10;

    private LocalDate expiryDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean perishable = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}