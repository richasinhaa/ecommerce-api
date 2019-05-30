package com.turing.turingproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.turing.turingproject.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	List<ShoppingCart> findByCartId(String cartId);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE ShoppingCart set quantity = ?2 where itemId = ?1")
	void updateCart(Long itemId, Integer quantity);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("DELETE FROM ShoppingCart where cartId = ?1")
	void deleteByCartId(String cartId);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE ShoppingCart set buyNow = 0 where itemId = ?1")
	void updateBuyLater(Long itemId);
	
	@Query("SELECT s FROM ShoppingCart s WHERE buyNow = 0 and cartId = ?1")
	ShoppingCart findSavedForLaterByCartId(String cartId);

}
