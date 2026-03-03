package com.abhijeetpatil.shippingestimator.repository;

import com.abhijeetpatil.shippingestimator.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
