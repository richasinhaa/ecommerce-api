package com.api.ecommerce.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.model.Category;
import com.api.ecommerce.model.ProductCategory;
import com.api.ecommerce.model.Result;
import com.api.ecommerce.repository.CategoryRepository;
import com.api.ecommerce.repository.ProductCategoryRepository;

@Component
public class CategoryManager {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	/**
	 * Returns all categories given order, page and limit
	 *
	 * @param order - Order
	 * @param page - Page
	 * @param limit - Limit
	 * @return - Result
	 */
	public Result getAllCategories(String order, String page, String limit) {
		limit = (limit == null) ? "20" : limit;
		page = (page == null) ? "1" : page;
		order = (order == null) ? "categoryId" : order;

		Pageable pageable = PageRequest.of((Integer.valueOf(page) - 1), Integer.valueOf(limit), Sort.by(order).ascending());
		List<Category> list = categoryRepository.findAll(pageable).getContent();

		Result categories = new Result();
		categories.setCount(categoryRepository.getCountOfCategories());
		categories.setRows(list);

		return categories;
	}
	
	/**
	 * Returns list of product category for a given product id
	 *
	 * @param productId - Product Id
	 * @return - List<ProductCategory>
	 */
	public List<ProductCategory> getCategoriesOfProduct(Long productId) {
		ProductCategory cat = productCategoryRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));

		List<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(cat);

		return list;
	}
	
	/**
	 * Returns list of category for a given department id
	 *
	 * @param departmentId - Department Id
	 * @return - List<Category>
	 */
	public List<Category> getCategoriesOfDepartment(Long departmentId) {
		List<Category> list = categoryRepository.findByDepartmentId(departmentId);
		
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return list;
	}

}
