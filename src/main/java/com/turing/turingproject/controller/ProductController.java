package com.turing.turingproject.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.manager.ProductManager;
import com.turing.turingproject.model.Customer;
import com.turing.turingproject.model.CustomerReview;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.ProductLocation;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.model.Review;
import com.turing.turingproject.repository.CustomerRepository;
import com.turing.turingproject.repository.ProductRepository;
import com.turing.turingproject.repository.ReviewRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductManager productManager;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	// Get All Products
	@GetMapping("")
	public Result getAllProducts(@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");

		return productManager.getAllProducts(descriptionLength, page, limit);
	}

	// Search Product
	@GetMapping("/search")
	public Result searchProducts(@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");
		String search = requestParams.get("query_string");
		String allWords = requestParams.get("all_words");

		return productManager.searchProducts(descriptionLength, page, limit, search, allWords);
	}

	// Get a Single Product
	@GetMapping("/{product_id}")
	public Product getProductById(@PathVariable(value = "product_id", required = true) Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	// Get products of a category
	@GetMapping("/inCategory/{category_id}")
	public Result getProductsOfCategory(@PathVariable(value = "category_id", required = true) Long categoryId) {
		return productManager.getProductsOfCategory(categoryId);
	}

	// Get products of a department
	@GetMapping("/inDepartment/{department_id}")
	public Result getProductsOfDepartment(@PathVariable(value = "department_id", required = true) Long departmentId) {
		return productManager.getProductsOfDepartment(departmentId);
	}

	// Get Details of a Single Product
	@GetMapping("/details/{product_id}")
	public Product getProductDetailsById(@PathVariable(value = "product_id", required = true) Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	// Get Locations of a Single Product
	@GetMapping("/{product_id}/locations")
	public ProductLocation getProductLocationsById(
			@PathVariable(value = "product_id", required = true) Long productId) {
		return productManager.getLocationsOfProduct(productId);
	}

	// Get Reviews For A Product
	@GetMapping("/{product_id}/reviews")
	public List<CustomerReview> getReviewsByProductId(
			@PathVariable(value = "product_id", required = true) Long productId) {
		return productManager.getReviewsByProductId(productId);
	}

	// Create a new Review
	@PostMapping("/{product_id}/reviews")
	public void createReview(@PathVariable(value = "product_id", required = true) Long productId,
			@Valid @RequestBody Review review, Authentication authentication) {
		review.setProductId(productId);
		review.setCreatedOn(new Date());
		
		//Fetch Authenticated User Id
		try {
			String email = authentication.getPrincipal().toString();
			Customer customer = customerRepository.findByEmail(email);
			review.setCustomerId(customer.getCustomerId());
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			reviewRepository.save(review);
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
	}

}
