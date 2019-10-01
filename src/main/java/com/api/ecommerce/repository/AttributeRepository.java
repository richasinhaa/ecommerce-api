package com.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
