package com.abhijeetpatil.shippingestimator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "warehouses", indexes = {
    @Index(name = "idx_warehouse_location", columnList = "latitude, longitude"),
    @Index(name = "idx_warehouse_operational", columnList = "operational")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Warehouse name is required")
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(length = 500)
    private String address;

    @Column(length = 15)
    private String contactPhone;

    @Column(length = 100)
    private String managerName;

    @Column(precision = 10, scale = 2)
    private Double capacityInCubicMeters;

    @Column(precision = 5, scale = 2)
    @Builder.Default
    private Double utilizationPercentage = 0.0;

    @Column(length = 50)
    @Builder.Default
    private String warehouseType = "REGIONAL_HUB";

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(nullable = false)
    @Builder.Default
    private Boolean hasClimateControl = false;

    @Column(length = 500)
    private String supportedCategories;

    @Column(nullable = false)
    @Builder.Default
    private Boolean operational = true;
}
