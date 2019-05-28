package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
