package com.turing.turingproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.manager.OrderManager;
import com.turing.turingproject.model.Order;
import com.turing.turingproject.model.OrderRequest;
import com.turing.turingproject.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderManager orderManager;

	@Autowired
	OrderRepository orderRepository;

	/**
	 * Returns Order
	 *
	 * @param authentication - Authentication
	 * @param request        - Order request
	 * @return - ResponseEntity<String>
	 */
	@PostMapping("")
	public ResponseEntity<String> createOrder(Authentication authentication, @Valid @RequestBody OrderRequest request) {
		Long orderId = null;
		try {
			orderId = orderManager.createOrder(request, authentication);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		JSONObject json;
		try {
			json = new JSONObject();
			json.put("order_id", String.valueOf(orderId));
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	}

	/**
	 * Returns Order by order id
	 *
	 * @param orderId - Order Id
	 * @return - ResponseEntity<String>
	 */
	@GetMapping("/{order_id}")
	public ResponseEntity<String> getOrderById(@PathVariable(value = "order_id", required = true) Long orderId) {
		Order order;
		try {
			order = orderRepository.findById(orderId).get();
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		if (order == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		JSONObject json;
		try {
			json = new JSONObject();
			json.put("order_id", String.valueOf(orderId));
			json.put("product_id", String.valueOf(order.getDetail().getProductId()));
			json.put("attributes", String.valueOf(order.getDetail().getAttributes()));
			json.put("quantity", String.valueOf(order.getDetail().getQuantity()));
			json.put("subtotal", String.valueOf(order.getDetail().getQuantity() * order.getDetail().getUnitCost()));
			json.put("product_name", String.valueOf(order.getDetail().getProductName()));
			json.put("unit_cost", String.valueOf(order.getDetail().getUnitCost()));
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

	}

	/**
	 * Returns Short detail for order by order id
	 *
	 * @param orderId - Order Id
	 * @return - ResponseEntity<String>
	 */
	@GetMapping("/shortDetail/{order_id}")
	public ResponseEntity<String> getOrderDetailById(@PathVariable(value = "order_id", required = true) Long orderId) {
		Order order;
		try {
			order = orderRepository.findById(orderId).get();
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		if (order == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		String status = order.getStatus() == 0 ? "un-paid" : "paid";

		JSONObject json;
		try {
			json = new JSONObject();
			json.put("order_id", String.valueOf(orderId));
			json.put("total_amount", String.valueOf(order.getTotalAmount()));
			json.put("created_on", String.valueOf(order.getCreatedOn()));
			json.put("shipped_on", String.valueOf(order.getShippedOn()));
			json.put("status", status);
			json.put("name", "Test");
		} catch (JSONException e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

	}

	/**
	 * Returns list of orders for authenticated customer
	 *
	 * @param authentication - Authentication
	 * @return - List<Order>
	 */
	@GetMapping("/inCustomer")
	public List<Order> getOrderForCustomer(Authentication authentication) {
		List<Order> list = null;
		try {
			list = orderManager.getOrdersForCustomer(authentication);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		if (list == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		return list;
	}
}
