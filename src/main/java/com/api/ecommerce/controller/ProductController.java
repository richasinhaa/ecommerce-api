package com.api.ecommerce.controller;

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

import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.manager.ProductManager;
import com.api.ecommerce.model.Customer;
import com.api.ecommerce.model.CustomerReview;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.ProductLocation;
import com.api.ecommerce.model.Result;
import com.api.ecommerce.model.Review;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.ReviewRepository;

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

	/**
	 * Returns all products
	 *
	 * @param requestParams - Request parameters
	 * @return - Result
	 */
	@GetMapping("")
	public Result getAllProducts(@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");

		return productManager.getAllProducts(descriptionLength, page, limit);
	}

	/**
	 * Searches for products
	 *
	 * @param requestParams - Request parameters
	 * @return - Result
	 */
	@GetMapping("/search")
	public Result searchProducts(@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");
		String search = requestParams.get("query_string");
		String allWords = requestParams.get("all_words");

		return productManager.searchProducts(descriptionLength, page, limit, search, allWords);
	}

	/**
	 * Returns a single product by product id
	 *
	 * @param productId - Product Id
	 * @return - Product
	 */
	@GetMapping("/{product_id}")
	public Product getProductById(@PathVariable(value = "product_id", required = true) Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	/**
	 * Returns a single product for a category id
	 *
	 * @param categoryId - Category Id
	 * @param requestParams - Request parameters
	 * @return - Result
	 */
	@GetMapping("/inCategory/{category_id}")
	public Result getProductsOfCategory(@PathVariable(value = "category_id", required = true) Long categoryId, 
			@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");
		
		return productManager.getProductsOfCategory(categoryId, descriptionLength, page, limit);
	}

	/**
	 * Returns a single product for a department id
	 *
	 * @param departmentId - Department Id
	 * @param requestParams - Request parameters
	 * @return - Result
	 */
	@GetMapping("/inDepartment/{department_id}")
	public Result getProductsOfDepartment(@PathVariable(value = "department_id", required = true) Long departmentId,
			@RequestParam Map<String, String> requestParams) {
		String descriptionLength = requestParams.get("description_length");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");
		
		return productManager.getProductsOfDepartment(departmentId, descriptionLength, page, limit);
	}

	/**
	 * Returns details of a single product by product id
	 *
	 * @param productId - Product Id
	 * @return - Product
	 */
	@GetMapping("/details/{product_id}")
	public Product getProductDetailsById(@PathVariable(value = "product_id", required = true) Long productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	/**
	 * Returns location of a single product by product id
	 *
	 * @param productId - Product Id
	 * @return - ProductLocation
	 */
	@GetMapping("/{product_id}/locations")
	public ProductLocation getProductLocationsById(
			@PathVariable(value = "product_id", required = true) Long productId) {
		return productManager.getLocationsOfProduct(productId);
	}

	/**
	 * Returns reviews of a single product by product id
	 *
	 * @param productId - Product Id
	 * @return - List<CustomerReview>
	 */
	@GetMapping("/{product_id}/reviews")
	public List<CustomerReview> getReviewsByProductId(
			@PathVariable(value = "product_id", required = true) Long productId) {
		return productManager.getReviewsByProductId(productId);
	}

	/**
	 * Creates review for a product id
	 *
	 * @param productId - Product Id
	 */
	@PostMapping("/{product_id}/reviews")
	public void createReview(@PathVariable(value = "product_id", required = true) Long productId,
			@Valid @RequestBody Review review, Authentication authentication) {
		review.setProductId(productId);
		review.setCreatedOn(new Date());

		// Fetch Authenticated User Id
		try {
			String email = authentication.getPrincipal().toString();
			Customer customer = customerRepository.findByEmail(email);
			review.setCustomerId(customer.getCustomerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			reviewRepository.save(review);
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
	}

}
