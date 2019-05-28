package com.turing.turingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turing.turingproject.model.CustomerReview;

@Repository
public interface ReviewRepository extends JpaRepository<CustomerReview, Long> {
	
	@Query("SELECT new com.turing.turingproject.model.CustomerReview("
			+ "c.name as customerName, r.review as review,"
			+ "r.rating as rating,r.createdOn as createdOn"
			+ ") "
			+"FROM Customer c join Review r "
			+ "on c.customerId=r.customerId "
			+ "where r.productId= ?1")
    List<CustomerReview> findByProductId(Long productId);

}
