package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.ShippingRegion;

@Repository
public interface ShippingRegionRepository extends JpaRepository<ShippingRegion, Long> {

}
