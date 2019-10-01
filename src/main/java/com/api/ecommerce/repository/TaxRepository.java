package com.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.model.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

}
