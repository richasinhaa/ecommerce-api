package com.turing.turingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
	
	@Query("SELECT c FROM Category c where c.departmentId = ?1") 
    List<Category> findByDepartmentId(Long id);
	
}
