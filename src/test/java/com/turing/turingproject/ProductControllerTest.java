package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.turing.turingproject.controller.ProductController;
import com.turing.turingproject.manager.ProductManager;
import com.turing.turingproject.model.Product;
import com.turing.turingproject.model.Result;
import com.turing.turingproject.repository.CustomerRepository;
import com.turing.turingproject.repository.ProductRepository;
import com.turing.turingproject.repository.ReviewRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@Mock
	ProductManager productManager;

	@Mock
	ProductRepository productRepository;

	@Mock
	ReviewRepository reviewRepository;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	ProductController controller;

	@Test
	public void getAllProductsTest() {
		Map<String, String> mockRequest = new HashMap<String, String>();
		mockRequest.put("description_length", "10");
		mockRequest.put("page", "1");
		mockRequest.put("limit", "20");

		List<Product> list = new ArrayList<Product>();
		Product p = new Product();
		p.setProductId(Long.valueOf(1));
		p.setName("p1");
		p.setDescription("some random description of product p1");
		p.setPrice(10.10);
		list.add(p);

		Result mockresponse = new Result();
		mockresponse.setCount(list.size());
		mockresponse.setRows(list);

		Mockito.when(productManager.getAllProducts(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(mockresponse);

		Result response = controller.getAllProducts(mockRequest);

		assertEquals(response.getCount(), 1);

	}

}
