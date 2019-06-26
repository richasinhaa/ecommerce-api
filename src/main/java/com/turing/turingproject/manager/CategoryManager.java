package com.turing.turingproject.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.model.Category;
import com.turing.turingproject.model.ProductCategory;
import com.turing.turingproject.repository.CategoryRepository;
import com.turing.turingproject.repository.ProductCategoryRepository;

@Component
public class CategoryManager {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	public Result getAllCategories(String order, String page, String limit) {
		int offset = 0;
		if (page != null && Integer.parseInt(page) != 1) {
			offset = Integer.parseInt(page) * 20; // PageSize = 20
		}

		/*if (order == null || order.equals("category_id")) {
			order = "categoryId";
		}*/

		limit = (limit == null) ? "20" : limit;
		order = (order == null) ? "categoryId" : order;

		Pageable pageable = PageRequest.of(Integer.valueOf(offset), Integer.valueOf(limit), Sort.by(order).ascending());
		List<Category> list = categoryRepository.findAll(pageable).getContent();

		Result categories = new Result();
		categories.setCount(list.size());
		categories.setRows(list);

		return categories;
	}

	public List<ProductCategory> getCategoriesOfProduct(Long productId) {
		ProductCategory cat = productCategoryRepository.findById(productId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));

		List<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(cat);

		return list;
	}

	public List<Category> getCategoriesOfDepartment(Long departmentId) {
		List<Category> list = categoryRepository.findByDepartmentId(departmentId);
		
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}
		
		return list;
	}

}
