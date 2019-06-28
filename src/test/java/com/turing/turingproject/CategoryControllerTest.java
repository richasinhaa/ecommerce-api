package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.turing.turingproject.controller.CategoryController;
import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.manager.CategoryManager;
import com.turing.turingproject.model.Category;
import com.turing.turingproject.model.ProductCategory;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
	@Mock
	CategoryRepository categoryRepository;
	
	@Mock
	CategoryManager categoryManager;

	@InjectMocks
	CategoryController controller;
	
	@Test
	public void getCategoryByIdSuccessTest() {
		Long id = Long.valueOf(1);
		Category cat = new Category();
		cat.setCategoryId(id);
		cat.setDepartmentId(Long.valueOf(2));
		cat.setDescription("some description");
		cat.setName("cat-1");
		Optional<Category> mockresponse = Optional.of(cat);

		Mockito.when(categoryRepository.findById(id))
				.thenReturn(mockresponse);

		Category response = controller.getCategoryById(id);

		assertEquals(response.getCategoryId(), Long.valueOf(1));

	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getCategoryByIdFailureTest() {
		Mockito.when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		controller.getCategoryById(Long.valueOf(1));
	}
	
	@Test
	public void getAllCategoriesSuccessTest() {
		Long id = Long.valueOf(1);
		Category cat = new Category();
		cat.setCategoryId(id);
		cat.setDepartmentId(Long.valueOf(2));
		cat.setDescription("some description");
		cat.setName("cat-1");
		List<Category> l = new ArrayList<Category>();
		l.add(cat);
		Result r = new Result();
		r.setCount(1);
		r.setRows(l);

		Mockito.when(categoryManager.getAllCategories(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(r);

		Result response = controller.getAllCategories(new HashMap<String, String>());

		assertEquals(response.getCount(), 1);

	}
	
	@Test
	public void getCategoriesOfProductSuccessTest() {
		ProductCategory pc = new ProductCategory();
		pc.setProductId(Long.valueOf(1));
		Category c = new Category();
		c.setCategoryId(Long.valueOf(1));
		c.setDepartmentId(Long.valueOf(2));
		c.setName("dummy_cat");
		pc.setCategory(c);
		List<ProductCategory> list = new ArrayList<ProductCategory>();
		list.add(pc);
		Mockito.when(categoryManager.getCategoriesOfProduct(Mockito.anyLong())).thenReturn(list);
		
		List<ProductCategory> res = controller.getCategoriesOfProduct(Long.valueOf(1));
		
		assertEquals(res.size(), 1);
	}
	
	@Test
	public void getCategoriesOfDepartmentSuccessTest() {
		Category c = new Category();
		c.setCategoryId(Long.valueOf(1));
		c.setDepartmentId(Long.valueOf(2));
		c.setName("dummy_cat");
		List<Category> list = new ArrayList<Category>();
		list.add(c);
		Mockito.when(categoryManager.getCategoriesOfDepartment(Mockito.anyLong())).thenReturn(list);
		
		List<Category> res = controller.getCategoriesOfDepartment(Long.valueOf(2));
		
		assertEquals(res.size(), 1);
	}
	
	
}
