package com.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.model.ShippingRegion;

@Repository
public interface ShippingRegionRepository extends JpaRepository<ShippingRegion, Long> {

}
