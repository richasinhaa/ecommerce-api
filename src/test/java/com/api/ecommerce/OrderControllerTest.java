package com.api.ecommerce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.api.ecommerce.controller.OrderController;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.manager.OrderManager;
import com.api.ecommerce.model.Customer;
import com.api.ecommerce.model.Order;
import com.api.ecommerce.model.OrderDetail;
import com.api.ecommerce.model.OrderRequest;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.OrderRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderControllerTest {

	@Mock
	private OrderRepository orderRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	OrderManager orderManager;

	@InjectMocks
	OrderController controller;

	@Test
	public void createOrderSuccessTest() {
		Customer applicationUser = new Customer("dummy", "dummy@email.com", "www-eee-12jk-dk", null, null, null, null,
				null, null, 0, null, null, null);
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(applicationUser);

		OrderRequest req = new OrderRequest();
		req.setCartId("we-fhfj-fdjw9jd");
		req.setShippingId(Long.valueOf(1));
		req.setTaxId(Long.valueOf(1));

		Long mockOrderId = Long.valueOf(1);

		Mockito.when(orderManager.createOrder(Mockito.any(OrderRequest.class), Mockito.any(Authentication.class)))
				.thenReturn(mockOrderId);

		ResponseEntity<String> res = controller.createOrder(authentication, req);
		assertEquals(res.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void getOrderByIdSuccessTest() {
		Order mockResponse = new Order();
		mockResponse.setOrderId(Long.valueOf(15));
		mockResponse.setCustomerId(Long.valueOf(10));
		mockResponse.setTotalAmount(Double.valueOf(10.0));
		OrderDetail detail = new OrderDetail();
		detail.setAttributes(new String());
		detail.setItemId(Long.valueOf(5));
		detail.setProductId(Long.valueOf(1));
		detail.setProductName("dummy_product");
		detail.setQuantity(1);
		detail.setUnitCost(Double.valueOf(3.0));
		mockResponse.setDetail(detail);

		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockResponse));

		ResponseEntity<String> res = controller.getOrderById(Long.valueOf(15));

		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getOrderByIdFailureTest() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(null);

		controller.getOrderById(Long.valueOf(15));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getOrderByIdNullTest() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		controller.getOrderById(Long.valueOf(15));
	}

	@Test
	public void getOrderDetailByIdSuccessTest() {
		Order mockResponse = new Order();
		mockResponse.setOrderId(Long.valueOf(15));
		mockResponse.setCustomerId(Long.valueOf(10));
		mockResponse.setTotalAmount(Double.valueOf(10.0));
		OrderDetail detail = new OrderDetail();
		detail.setAttributes(new String());
		detail.setItemId(Long.valueOf(5));
		detail.setProductId(Long.valueOf(1));
		detail.setProductName("dummy_product");
		detail.setQuantity(1);
		detail.setUnitCost(Double.valueOf(3.0));
		mockResponse.setDetail(detail);

		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockResponse));

		ResponseEntity<String> res = controller.getOrderDetailById(Long.valueOf(15));

		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getOrderDetailByIdFailureTest() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(null);

		controller.getOrderDetailById(Long.valueOf(15));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getOrderDetailByIdNullTest() {
		Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		controller.getOrderDetailById(Long.valueOf(15));
	}

	@Test
	public void getOrderForCustomerSuccessTest() {
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

		Order mockResponse = new Order();
		mockResponse.setOrderId(Long.valueOf(15));
		mockResponse.setCustomerId(Long.valueOf(10));
		mockResponse.setTotalAmount(Double.valueOf(10.0));
		OrderDetail detail = new OrderDetail();
		detail.setAttributes(new String());
		detail.setItemId(Long.valueOf(5));
		detail.setProductId(Long.valueOf(1));
		detail.setProductName("dummy_product");
		detail.setQuantity(1);
		detail.setUnitCost(Double.valueOf(3.0));
		mockResponse.setDetail(detail);
		List<Order> list = new ArrayList<Order>();
		list.add(mockResponse);

		Mockito.when(orderManager.getOrdersForCustomer(Mockito.any(Authentication.class))).thenReturn(list);

		List<Order> response = controller.getOrderForCustomer(authentication);
		assertEquals(response.size(), 1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void getOrderForCustomerEmptyListTest() {
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
		
		Mockito.when(orderManager.getOrdersForCustomer(Mockito.any(Authentication.class)))
				.thenReturn(null);
		
		controller.getOrderForCustomer(authentication);
	}
}
