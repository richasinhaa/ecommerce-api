package com.api.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.api.ecommerce.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByName(String name);
	Customer findByEmail(String email);
	
	/**
	 * Updates customer
	 *
	 * @param customerId - Customer Id
	 * @param name - Name
	 * @param email - Email id
	 * @param city - City
	 * @param address1 - Address1
	 * @param creditCard - Credit Card
	 * @return - List<Category>
	 */
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE Customer c SET c.customerId = ?1, c.name = ?2, c.email = ?3,"
			+ "c.city = ?4, c.address1 = ?5, c.creditCard = ?6  WHERE c.customerId = ?1")
	void updateCustomer(Long customerId, String name, String email, String city, String address1, String creditCard);

}
