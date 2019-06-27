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
	
	/**
	 * Updates quantity for an item id
	 *
	 * @param itemId - Item Id
	 * @param quantity - Quantity
	 */
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE ShoppingCart set quantity = ?2 where itemId = ?1")
	void updateCart(Long itemId, Integer quantity);
	
	/**
	 * Deletes a cart by id
	 *
	 * @param cartId - Cart Id
	 */
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("DELETE FROM ShoppingCart where cartId = ?1")
	void deleteByCartId(String cartId);
	
	/**
	 * Updates buy later for an item id
	 *
	 * @param itemId - Item Id
	 */
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("UPDATE ShoppingCart set buyNow = 0 where itemId = ?1")
	void updateBuyLater(Long itemId);
	
	/**
	 * Returns list of customer review by product id
	 *
	 * @param productId - Product Id
	 * @return - List<CustomerReview>
	 */
	@Query("SELECT s FROM ShoppingCart s WHERE buyNow = 0 and cartId = ?1")
	ShoppingCart findSavedForLaterByCartId(String cartId);
	
	/**
	 * Returns ShoppingCart
	 *
	 * @param cartId - Cart Id
	 * @param productId - Product Id
	 * @return - ShoppingCart
	 */
	@Query("SELECT s FROM ShoppingCart s WHERE cartId = ?1 and productId = ?2")
	ShoppingCart findByCartIdAndProductId(String cartId, Long productId);

}
