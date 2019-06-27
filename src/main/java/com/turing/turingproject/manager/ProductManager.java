package com.turing.turingproject.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.CustomerReview;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.ProductLocation;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.repository.ProductRepository;
import com.turing.turingproject.repository.ReviewRepository;

@Component
public class ProductManager {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	/**
	 * Returns all products given description length, page and limit
	 *
	 * @param descriptionLength - Description Length
	 * @param page - Page
	 * @param limit - Limit
	 * @return - Result
	 */
	public Result getAllProducts(String descriptionLength, String page, String limit) {
		limit = (limit == null) ? "20" : limit;
		page = (page == null) ? "1" : page;
		
		Pageable pageable = PageRequest.of((Integer.valueOf(page) - 1), Integer.valueOf(limit));
		List<Product> list = productRepository.findAll(pageable).getContent();

		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		descriptionLength = descriptionLength == null ? "200" : descriptionLength;

		if (descriptionLength != null) {
			int length = Integer.parseInt(descriptionLength);
			for (Product p : list)
				if (p != null) {
					String des = p.getDescription();
					des = des.substring(0, Math.min(des.length(), length));
					p.setDescription(des);
				}
		}

		Result products = new Result();
		products.setCount(productRepository.getCountOfProducts());
		products.setRows(list);

		return products;
	}
	
	/**
	 * Searches products given description length, page, limit, search and allWords
	 *
	 * @param descriptionLength - Description Length
	 * @param page - Page
	 * @param limit - Limit
	 * @param search - Search String
	 * @param allWords - All Words or Substring
	 * @return - Result
	 */
	public Result searchProducts(String descriptionLength, String page, String limit, String search, String allWords) {
		limit = (limit == null) ? "20" : limit;
		page = (page == null) ? "1" : page;
		
		allWords = (limit == null) ? "on" : "off";
		
		descriptionLength = descriptionLength == null ? "200" : descriptionLength;

		Pageable pageable = PageRequest.of((Integer.valueOf(page) -1), Integer.valueOf(limit));
		
		List<Product> list = new ArrayList<Product>();
		
		if(allWords.equals("on")) {
			list = productRepository.findBySearchQueryAllWords(search, pageable);
		} else {
			list = productRepository.findBySearchQuery(search, pageable);
		}

		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		if (descriptionLength != null) {
			int length = Integer.parseInt(descriptionLength);
			for (Product p : list)
				if (p != null) {
					String des = p.getDescription();
					des = des.substring(0, Math.min(des.length(), length));
					p.setDescription(des);
				}
		}
		
		int count = 0;
		if(allWords.equals("on")) {
			count = productRepository.findCountAllWords(search);
		} else {
			count = productRepository.findCountLike(search);
		}

		Result products = new Result();
		products.setCount(count);
		products.setRows(list);

		return products;
	}
	
	/**
	 * Returns product of a given category id
	 *
	 * @param categoryId - Category Id
	 * @param limit 
	 * @param page 
	 * @param descriptionLength 
	 * @return - Result
	 */
	public Result getProductsOfCategory(Long categoryId, String descriptionLength, String page, String limit) {
		limit = (limit == null) ? "20" : limit;
		page = (page == null) ? "1" : page;
		
		descriptionLength = descriptionLength == null ? "200" : descriptionLength;

		Pageable pageable = PageRequest.of((Integer.valueOf(page) - 1), Integer.valueOf(limit));
		
		List<Product> list = new ArrayList<Product>();
		list = productRepository.findProductsOfCategory(categoryId, pageable);
		
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		int count = productRepository.findProductsOfCategoryCount(categoryId);
		
		Result products = new Result();
		products.setCount(count);
		products.setRows(list);

		return products;
		
	}
	
	/**
	 * Returns product of a given department id
	 *
	 * @param departmentId - Department Id
	 * @param limit 
	 * @param page 
	 * @param descriptionLength 
	 * @return - Result
	 */
	public Result getProductsOfDepartment(Long departmentId, String descriptionLength, String page, String limit) {
		limit = (limit == null) ? "20" : limit;
		page = (page == null) ? "1" : page;
		
		descriptionLength = descriptionLength == null ? "200" : descriptionLength;

		Pageable pageable = PageRequest.of((Integer.valueOf(page) - 1), Integer.valueOf(limit));
		
		List<Product> list = new ArrayList<Product>();
		list = productRepository.findProductsOfDepartment(departmentId, pageable);
		
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		int count = productRepository.findProductsOfDepartmentCount(departmentId);
		
		Result products = new Result();
		products.setCount(count);
		products.setRows(list);

		return products;
	}
	
	/**
	 * Returns locations of a given product id
	 *
	 * @param productId - Product Id
	 * @return - ProductLocation
	 */
	public ProductLocation getLocationsOfProduct(Long productId) {
		ProductLocation productLocation = productRepository.findLocationsForProduct(productId);
		if (productLocation == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return productLocation;
	}
	
	/**
	 * Returns reviews by product id
	 *
	 * @param productId - Product Id
	 * @return - List<CustomerReview>
	 */
	public List<CustomerReview> getReviewsByProductId(Long productId) {
		List<CustomerReview> customerReviews = reviewRepository.findByProductId(productId);
		if (customerReviews.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return customerReviews;
	}

}
