package com.api.ecommerce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.api.ecommerce.controller.ProductController;
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

@RunWith(MockitoJUnitRunner.Silent.class)
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

	@Test
	public void searchProductsTest() {
		Map<String, String> mockRequest = new HashMap<String, String>();
		mockRequest.put("description_length", "10");
		mockRequest.put("page", "1");
		mockRequest.put("limit", "20");
		mockRequest.put("query_string", "a");
		mockRequest.put("all_words", "off");

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

		Mockito.when(productManager.searchProducts(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())).thenReturn(mockresponse);

		Result response = controller.searchProducts(mockRequest);

		assertEquals(response.getCount(), 1);
	}

	@Test
	public void getProductByIdSuccessTest() {
		Product p = new Product();
		p.setProductId(Long.valueOf(1));
		p.setName("p1");
		p.setPrice(Double.valueOf(10.0));

		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(p));

		Product res = controller.getProductById(Long.valueOf(1));

		assertEquals(res.getProductId(), Long.valueOf(1));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getProductByIdFailureTest() {
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		controller.getProductById(Long.valueOf(1));
	}

	@Test
	public void getProductsOfCategoryTest() {
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

		Mockito.when(productManager.getProductsOfCategory(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(mockresponse);
		
		Result response = controller.getProductsOfCategory(Long.valueOf(1), mockRequest);

		assertEquals(response.getCount(), 1);
	}
	
	@Test
	public void getProductsOfDepartmentTest() {
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

		Mockito.when(productManager.getProductsOfDepartment(Mockito.anyLong(), Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(mockresponse);
		
		Result response = controller.getProductsOfDepartment(Long.valueOf(1), mockRequest);

		assertEquals(response.getCount(), 1);
	}
	
	@Test
	public void getProductDetailsByIdSuccessTest() {
		Product p = new Product();
		p.setProductId(Long.valueOf(1));
		p.setName("p1");
		p.setPrice(Double.valueOf(10.0));

		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(p));

		Product res = controller.getProductDetailsById(Long.valueOf(1));

		assertEquals(res.getProductId(), Long.valueOf(1));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getProductDetailsByIdFailureTest() {
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		controller.getProductDetailsById(Long.valueOf(1));
	}
	
	@Test
	public void getProductLocationsByIdTest() {
		ProductLocation l = new ProductLocation(Long.valueOf(1), Long.valueOf(1), "cat-1", Long.valueOf(1), "dept-1");
		
		Mockito.when(productManager.getLocationsOfProduct(Mockito.anyLong())).thenReturn(l);

		ProductLocation res = controller.getProductLocationsById(Long.valueOf(1));

		assertEquals(res.getProductId(), Long.valueOf(1));
	}
	
	@Test
	public void getReviewsByProductIdTest() {
		CustomerReview r = new CustomerReview("dummy", "dummy-review", 4, new Date());
		List<CustomerReview> l = new ArrayList<CustomerReview>();
		l.add(r);
		
		Mockito.when(productManager.getReviewsByProductId(Mockito.anyLong())).thenReturn(l);

		controller.getReviewsByProductId(Long.valueOf(1));
	}
	
	@Test
	public void createReviewTest() {
		Review r = new Review();
		r.setRating(4);
		
		Customer mockCustomer = new Customer();
		mockCustomer.setCustomerId(Long.valueOf(1));
		mockCustomer.setName("dummy");
		mockCustomer.setEmail("dummy@email.com");
		
		Customer applicationUser = new Customer("dummy", "dummy@email.com", "www-eee-12jk-dk", null, null, null, null,
				null, null, 0, null, null, null);
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(applicationUser);
		
		Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(mockCustomer);
		
		Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(r);
		
		controller.createReview(Long.valueOf(1), r, authentication);

	}

}
