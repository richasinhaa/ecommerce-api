package com.turing.turingproject.manager;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.ShoppingCart;
import com.turing.turingproject.repository.ProductRepository;
import com.turing.turingproject.repository.ShoppingCartRepository;

@Component
public class ShoppingCartManager {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	ProductRepository productRepository;

	public String generateUniqueId() {
		UUID uuid = UUID.randomUUID();
		String uniqueId = Long.toString(uuid.getMostSignificantBits(), 20) 
				+ Long.toString(uuid.getLeastSignificantBits(), 20);

		return uniqueId;
	}

	public ShoppingCart addProduct(String cartId, Long productId, String attributes) {
		ShoppingCart cart = new ShoppingCart();
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
		try {
			shoppingCartRepository.save(cart);
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return cart;

	}

}
