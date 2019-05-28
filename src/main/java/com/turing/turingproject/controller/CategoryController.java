package com.turing.turingproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.manager.CategoryManager;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.model.Category;
import com.turing.turingproject.model.ProductCategory;
import com.turing.turingproject.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryManager categoryManager;

	@Autowired
	CategoryRepository categoryRepository;

	// Get All Categories
	@GetMapping("")
	public Result getAllCategories(@RequestParam Map<String, String> requestParams) {
		String order = requestParams.get("order");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");

		return categoryManager.getAllCategories(order, page, limit);
	}

	// Get a Single Category
	@GetMapping("/{category_id}")
	public Category getCategoryById(@PathVariable(value = "category_id", required = true) Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	// Get categories of a product
	@GetMapping("/inProduct/{product_id}")
	public List<ProductCategory> getCategoriesOfProduct(@PathVariable(value = "product_id", required = true) Long productId) {
		return categoryManager.getCategoriesOfProduct(productId);
	}
	
	// Get categories from a Department ID
		@GetMapping("/inDepartment/{department_id}")
		public List<Category> getCategoriesOfDepartment(@PathVariable(value = "department_id", required = true) Long departmentId) {
			return categoryManager.getCategoriesOfDepartment(departmentId);
		}
}
