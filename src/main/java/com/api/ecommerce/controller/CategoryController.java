package com.api.ecommerce.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.manager.CategoryManager;
import com.api.ecommerce.model.Category;
import com.api.ecommerce.model.ProductCategory;
import com.api.ecommerce.model.Result;
import com.api.ecommerce.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryManager categoryManager;

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * Returns all categories
	 *
	 * @param requestParams - Request Param
	 * @return - Result
	 */
	@GetMapping("")
	public Result getAllCategories(@RequestParam Map<String, String> requestParams) {
		String order = requestParams.get("order");
		String page = requestParams.get("page");
		String limit = requestParams.get("limit");

		return categoryManager.getAllCategories(order, page, limit);
	}

	/**
	 * Returns a Single category
	 *
	 * @param categoryId - Category Id
	 * @return - Category
	 */
	@GetMapping("/{category_id}")
	public Category getCategoryById(@PathVariable(value = "category_id", required = true) Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	/**
	 * Returns all categories of a product id
	 *
	 * @param productId - Product Id
	 * @return - List<ProductCategory>
	 */
	@GetMapping("/inProduct/{product_id}")
	public List<ProductCategory> getCategoriesOfProduct(
			@PathVariable(value = "product_id", required = true) Long productId) {
		return categoryManager.getCategoriesOfProduct(productId);
	}

	/**
	 * Returns all categories of a department id
	 *
	 * @param departmentId - Department Id
	 * @return - List<Category>
	 */
	@GetMapping("/inDepartment/{department_id}")
	public List<Category> getCategoriesOfDepartment(
			@PathVariable(value = "department_id", required = true) Long departmentId) {
		return categoryManager.getCategoriesOfDepartment(departmentId);
	}
}
