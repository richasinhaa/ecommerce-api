package com.turing.turingproject.manager;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Customer;
import com.turing.turingproject.model.Order;
import com.turing.turingproject.model.OrderDetail;
import com.turing.turingproject.model.OrderRequest;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.Shipping;
import com.turing.turingproject.model.ShoppingCart;
import com.turing.turingproject.repository.CustomerRepository;
import com.turing.turingproject.repository.OrderDetailRepository;
import com.turing.turingproject.repository.OrderRepository;
import com.turing.turingproject.repository.ProductRepository;
import com.turing.turingproject.repository.ShippingRepository;
import com.turing.turingproject.repository.ShoppingCartRepository;

@Component
public class OrderManager {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ShoppingCartRepository cartRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ShippingRepository shippingRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	/**
	 * Returns order id after creating an order
	 *
	 * @param request - Order request
	 * @param authentication - Authentication
	 * @return - Long
	 */
	public Long createOrder(@Valid OrderRequest request, Authentication authentication) {
		if(request == null || request.getCartId() == null || request.getShippingId() == null || request.getTaxId() == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		//get cart details from cart id
		ShoppingCart cart;
		try {
			cart = cartRepository.findByCartId(request.getCartId()).get(0);
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "700");
		}
		if(cart == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "800");
		}
		
		//get product details from cart.productId
		Product product;
		try {
			product = productRepository.findById(cart.getProductId()).get();
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "900");
		}
		if(product == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "1000");
		}
		
		//get shipping details from shippingId
		Shipping shipping;
		try {
			shipping = shippingRepository.findById(request.getShippingId()).get();
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "100");
		}
		if(shipping == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "200");
		}
		
		//Get Authenticated user email for fetching customer id
		String email = authentication.getPrincipal().toString();
		
		Order order = new Order();
		order.setShippingId(request.getShippingId());
		order.setTaxId(request.getTaxId());
		order.setCreatedOn(new Date());
		order.setTaxId(request.getTaxId());
		if(email != null) {
			Customer user = customerRepository.findByEmail(email);
			if(user != null) {
				order.setCustomerId(user.getCustomerId());
			}
			
		}
		
		OrderDetail detail = new OrderDetail();
		detail.setItemId(cart.getItemId());
		detail.setAttributes(cart.getAttributes());
		detail.setProductId(cart.getProductId());
		detail.setProductName(product.getName());
		detail.setQuantity(cart.getQuantity());
		detail.setUnitCost(product.getPrice());
		order.setDetail(detail);
		order.setTotalAmount(cart.getSubTotal());
		
		Order response;
		try {
			response = orderRepository.save(order);
			detail.setOrderId(response.getOrderId());
			orderDetailRepository.save(detail);
		} catch(Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "300");
		}
		
		/**
		 * Delete cart after successful order creation
		 */
		try {
			cartRepository.deleteByCartId(cart.getCartId());
		} catch(Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return response.getOrderId();
	}

	/**
	 * Returns list of orders for a customer
	 *
	 * @param authentication - Authentication
	 * @return - List<Order>
	 */
	public List<Order> getOrdersForCustomer(Authentication authentication) {
		String email = authentication.getPrincipal().toString();
		Long customerId = null;
		if(email != null) {
			Customer user = customerRepository.findByEmail(email);
			if(user != null) {
				customerId = user.getCustomerId();
			}
		} else {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "300");
		}
		
		List<Order> list = orderRepository.findByCustomerId(customerId);
		
		return list;
	}

}
