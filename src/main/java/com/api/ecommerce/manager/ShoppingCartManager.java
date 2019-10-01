package com.api.ecommerce.manager;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.ShoppingCart;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.ShoppingCartRepository;

@Component
public class ShoppingCartManager {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	/**
	 * Returns unique id
	 *
	 * @return - String
	 */
	public String generateUniqueId() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = Long.toString(uuid.getMostSignificantBits(), 20) 
				+ Long.toString(uuid.getLeastSignificantBits(), 20);

		return uniqueId;
	}
	
	/**
	 * Returns Shopping Cart after adding product to a cart
	 *
	 * @param cartId - Cart Id
	 * @param productId - Product Id
	 * @param attributes - Attributes
	 * @return - ShoppingCart
	 */
	public ShoppingCart addProduct(String cartId, Long productId, String attributes) {
		/*check if product_id exists in cart
		 * if exists :: increase the quantity
		 * if not exists :: create a new row in shopping_cart
		 */
		ShoppingCart cart = new ShoppingCart();
		ShoppingCart existingItemInCart = shoppingCartRepository.findByCartIdAndProductId(cartId, productId);
		if(existingItemInCart != null) {
			int quantity = existingItemInCart.getQuantity();
			quantity = quantity + 1;
			existingItemInCart.setQuantity(quantity);
			cart = existingItemInCart;
		} else {
			cart.setCartId(cartId);
			cart.setAddedOn(new Date());
			cart.setAttributes(attributes);
			cart.setProductId(productId);
			cart.setQuantity(1);
			String image = null;
			Double subtotal = 0.0;
			Product p = productRepository.findById(productId).get();
			if(p == null) {
				throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
			} else {
				image = p.getImage();
				Double price = p.getPrice();
				if(price != null) {
					subtotal = price*cart.getQuantity();
				}
				cart.setSubTotal(subtotal);
				cart.setImage(image);
			}
		}
		
		try {
			shoppingCartRepository.save(cart);
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return cart;

	}
}
