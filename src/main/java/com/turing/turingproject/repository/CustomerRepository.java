package com.turing.turingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.turing.turingproject.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByName(String name);
	Customer findByEmail(String email);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE Customer c SET c.customerId = ?1, c.name = ?2, c.email = ?3,"
			+ "c.city = ?4, c.address1 = ?5, c.creditCard = ?6  WHERE c.customerId = ?1")
	void updateCustomer(Long customerId, String name, String email, String city, String address1, String creditCard);

}
