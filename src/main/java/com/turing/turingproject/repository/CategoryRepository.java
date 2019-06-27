package com.turing.turingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
	
	/**
	 * Returns list category
	 *
	 * @param id - Id
	 * @return - List<Category>
	 */
	@Query("SELECT c FROM Category c where c.departmentId = ?1") 
    List<Category> findByDepartmentId(Long id);
	
	/**
	 * Returns count of category
	 *
	 * @return - Integer
	 */
	@Query("SELECT COUNT(*) FROM Category c") 
	int getCountOfCategories();
	
}
