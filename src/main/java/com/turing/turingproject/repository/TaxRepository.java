package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

}
