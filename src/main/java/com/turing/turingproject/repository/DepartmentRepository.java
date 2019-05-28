package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
