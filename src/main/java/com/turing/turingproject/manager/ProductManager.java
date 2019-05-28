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

	public Result getAllProducts(String descriptionLength, String page, String limit) {
		int offset = 0;
		if (page != null && Integer.parseInt(page) != 1) {
			offset = Integer.parseInt(page) * 20; // PageSize = 20
		}

		limit = (limit == null) ? "20" : limit;

		Pageable pageable = PageRequest.of(Integer.valueOf(offset), Integer.valueOf(limit));
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
		products.setCount(list.size());
		products.setRows(list);

		return products;
	}

	public Result searchProducts(String descriptionLength, String page, String limit, String search, String allWords) {
		int offset = 0;
		if (page != null && Integer.parseInt(page) != 1) {
			offset = Integer.parseInt(page) * 20; // PageSize = 20
		}

		limit = (limit == null) ? "20" : limit;
		
		allWords = (limit == null) ? "on" : "off";
		
		descriptionLength = descriptionLength == null ? "200" : descriptionLength;

		Pageable pageable = PageRequest.of(Integer.valueOf(offset), Integer.valueOf(limit));
		
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

		Result products = new Result();
		products.setCount(list.size());
		products.setRows(list);

		return products;
	}

	public Result getProductsOfCategory(Long categoryId) {
		List<Product> list = new ArrayList<Product>();
		list = productRepository.findProductsOfCategory(categoryId);
		
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		Result products = new Result();
		products.setCount(list.size());
		products.setRows(list);

		return products;
		
	}

	public Result getProductsOfDepartment(Long departmentId) {
		List<Product> list = new ArrayList<Product>();
		list = productRepository.findProductsOfDepartment(departmentId);
		
		if (list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		Result products = new Result();
		products.setCount(list.size());
		products.setRows(list);

		return products;
	}

	public ProductLocation getLocationsOfProduct(Long productId) {
		ProductLocation productLocation = productRepository.findLocationsForProduct(productId);
		if (productLocation == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return productLocation;
	}
	
	public List<CustomerReview> getReviewsByProductId(Long productId) {
		List<CustomerReview> customerReviews = reviewRepository.findByProductId(productId);
		if (customerReviews.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return customerReviews;
	}

}
