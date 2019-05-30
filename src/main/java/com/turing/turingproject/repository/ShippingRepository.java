package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Shipping;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

}
