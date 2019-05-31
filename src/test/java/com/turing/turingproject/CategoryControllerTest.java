package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.turing.turingproject.controller.CategoryController;
import com.turing.turingproject.model.Category;
import com.turing.turingproject.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {
	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryController controller;
	
	@Test
	public void getCategoryByIdTest() {
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
}
