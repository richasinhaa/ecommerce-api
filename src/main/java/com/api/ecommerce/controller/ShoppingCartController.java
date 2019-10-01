package com.api.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.manager.ShoppingCartManager;
import com.api.ecommerce.model.CreateOrderRequest;
import com.api.ecommerce.model.Customer;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.ShoppingCart;
import com.api.ecommerce.model.UpdateCartQuantityRequest;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.ShoppingCartRepository;

@RestController
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

	@Autowired
	ShoppingCartManager shoppingCartManager;

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	/**
	 * Returns a unique cart id
	 *
	 * @return - ResponseEntity<String>
	 */
	@GetMapping("/generateUniqueId")
	public ResponseEntity<String> createCart() throws JSONException {
		String uniqueCartId = shoppingCartManager.generateUniqueId();
		JSONObject json;
		try {
			json = new JSONObject();
			json.put("cart_id", uniqueCartId);
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	}

	/**
	 * Returns a shopping cart (add product to shopping cart)
	 *
	 * @param request - Create Order Request
	 * @return - ShoppingCart
	 */
	@PostMapping("/add")
	public ShoppingCart addProduct(@RequestBody CreateOrderRequest request) {
		return shoppingCartManager.addProduct(request.getCartId(), request.getProductId(), request.getAttributes());
	}

	/**
	 * Returns shopping cart by id
	 *
	 * @param cartId - Cart Id
	 * @return - List<ShoppingCart>
	 */
	@GetMapping("/{cart_id}")
	public List<ShoppingCart> getCartById(@PathVariable(value = "cart_id", required = true) String cartId) {
		List<ShoppingCart> cart = shoppingCartRepository.findByCartId(cartId);
		if (cart.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return cart;
	}

	/**
	 * Returns list of shopping cart (update quantity)
	 *
	 * @param cartId - Cart Id
	 * @return - List<ShoppingCart>
	 */
	@PutMapping("/update/{item_id}")
	public List<ShoppingCart> createReview(@PathVariable(value = "item_id", required = true) Long itemId,
			@Valid @RequestBody UpdateCartQuantityRequest request) {
		ShoppingCart cart = shoppingCartRepository.findById(itemId).get();
		Integer quantity = request.getQuantity();
		
		if (cart == null)
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		
		shoppingCartRepository.updateCart(itemId, quantity);
		cart.setQuantity(quantity);
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		list.add(cart);
		
		return list;
	}
	
	/**
	 * Returns list of shopping cart (delete cart)
	 *
	 * @param cartId - Cart Id
	 * @return - List<ShoppingCart>
	 */
	@DeleteMapping("/empty/{cart_id}")
	public List<ShoppingCart> deleteCartById(@PathVariable(value = "cart_id", required = true) String cartId) {
		try {
			shoppingCartRepository.deleteByCartId(cartId);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		List<ShoppingCart> result = shoppingCartRepository.findByCartId(cartId);
		
		return result;
	}
	
	/**
	 * Returns total amount by cart id
	 *
	 * @param cartId - Cart Id
	 * @return - ResponseEntity<String>
	 */
	@GetMapping("/totalAmount/{cart_id}")
	public ResponseEntity<String> getTotalAmountByCartId(@PathVariable(value = "cart_id", required = true) String cartId) {
		List<ShoppingCart> list;
		try {
			list = shoppingCartRepository.findByCartId(cartId);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		if(list.isEmpty() || list == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		Double totalAmount = 0.0;
		for (ShoppingCart cart : list) {
			Double amount = cart.getSubTotal();
			totalAmount = totalAmount + amount;
		}
		
		JSONObject json;
		try {
			json = new JSONObject();
			json.put("total_amount", totalAmount);
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
		
	}
	
	/**
	 * Saves a product for later
	 *
	 * @param itemId - Item Id
	 */
	@GetMapping("/saveForLater/{item_id}")
	public void saveAProductForLater(@PathVariable(value = "item_id", required = true) Long itemId) {
		try {
			shoppingCartRepository.updateBuyLater(itemId);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
	}
	
	/**
	 * Returns all saved products by cart id
	 *
	 * @param cartId - Cart Id
	 * @return - ResponseEntity<String>
	 */
	@GetMapping("/getSaved/{cart_id}")
	public ResponseEntity<String> getSavedForLater(@PathVariable(value = "cart_id", required = true) String cartId) {
		ShoppingCart cart = null;
		try {
			cart = shoppingCartRepository.findSavedForLaterByCartId(cartId);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		if(cart == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		Long productId = cart.getProductId();
		Product product = productRepository.findById(productId).get();
		if(product == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		Long itemId = cart.getItemId();
		String name = product.getName();
		String attributes = cart.getAttributes();
		Double price = product.getPrice();
		
		JSONObject json;
		try {
			json = new JSONObject();
			json.put("item_id", String.valueOf(itemId));
			json.put("name", name);
			json.put("attributes", attributes);
			json.put("price", String.valueOf(price));
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	}
	
	/**
	 * Removes an item from a cart by item id
	 *
	 * @param itemId - Item Id
	 * @return - ResponseEntity<String>
	 */
	@DeleteMapping("/removeProduct/{item_id}")
	public void removeProductFromCart(@PathVariable(value = "item_id", required = true) Long itemId) {
		try {
			shoppingCartRepository.deleteById(itemId);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
	}
}
