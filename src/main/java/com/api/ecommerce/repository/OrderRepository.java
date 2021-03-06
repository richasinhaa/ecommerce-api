package com.api.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.ecommerce.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	/**
	 * Returns order by given customer id
	 *
	 * @param customerId - Customer Id
	 * @return - List<Order>
	 */
	List<Order> findByCustomerId(Long customerId);

}
