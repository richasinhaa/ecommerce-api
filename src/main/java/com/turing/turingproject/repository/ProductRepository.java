package com.turing.turingproject.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.ProductLocation;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "SELECT p FROM product p where p.name = ?1", nativeQuery = true) 
    List<Product> findBySearchQueryAllWords(String query, Pageable pageable);
	
	@Query("SELECT p FROM Product p where p.name LIKE %?1%") 
    List<Product> findBySearchQuery(String query, Pageable pageable);

	@Query(value = "SELECT p.* FROM product p join product_category c on p.product_id = c.product_id where c.category_id = ?1", nativeQuery = true) 
	List<Product> findProductsOfCategory(Long categoryId);
	
	@Query(value = "SELECT p.* FROM product p join product_category c on p.product_id = c.product_id join category ct on ct.category_id = c.category_id where ct.department_id = ?1", nativeQuery = true) 
	List<Product> findProductsOfDepartment(Long departmentId);
	
	
	@Query("SELECT new com.turing.turingproject.model.ProductLocation("
			+ "ct.productId as productId, c.categoryId as categoryId,"
			+ "c.name as categoryName,c.departmentId as departmentId, "
			+ "d.name as departmentName"
			+ ") "
			+"FROM ProductCategory ct join Category c "
			+ "on ct.category.categoryId=c.categoryId "
			+ "join Department d "
			+ "on d.departmentId=c.departmentId "
			+ "where ct.productId= ?1")
	ProductLocation findLocationsForProduct(Long productId);

}