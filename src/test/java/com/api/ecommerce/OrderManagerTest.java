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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.api.ecommerce.manager.OrderManager;
import com.api.ecommerce.model.Customer;
import com.api.ecommerce.model.Order;
import com.api.ecommerce.model.OrderDetail;
import com.api.ecommerce.model.OrderRequest;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.Shipping;
import com.api.ecommerce.model.ShoppingCart;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.OrderDetailRepository;
import com.api.ecommerce.repository.OrderRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.ShippingRepository;
import com.api.ecommerce.repository.ShoppingCartRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderManagerTest {
	
	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	OrderDetailRepository orderDetailRepository;
	
	@Mock
	ShoppingCartRepository cartRepository;
	
	@Mock
	ProductRepository productRepository;
	
	@Mock
	ShippingRepository shippingRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	@InjectMocks
	OrderManager manager;
	
	@Test
	public void createOrderTest() {
		
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
		
		ShoppingCart mockCart = new ShoppingCart();
		mockCart.setCartId("we-fhfj-fdjw9jd");
		mockCart.setItemId(Long.valueOf(1));
		mockCart.setProductId(Long.valueOf(10));
		mockCart.setQuantity(1);
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		list.add(mockCart);
		Mockito.when(cartRepository.findByCartId(Mockito.anyString())).thenReturn(list);
		
		Product mockProduct = new Product();
		mockProduct.setProductId(Long.valueOf(10));
		mockProduct.setName("dummy_product");
		mockProduct.setPrice(Double.valueOf(20.0));
		Optional<Product> op = Optional.of(mockProduct);
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(op);
		
		Shipping mockShipping = new Shipping();
		mockShipping.setShippingId(Long.valueOf(1));
		mockShipping.setShippingRegionId(Long.valueOf(1));
		mockShipping.setShippingCost(Double.valueOf(100.0));
		Mockito.when(shippingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockShipping));
		
		Customer mockCustomer = new Customer();
		mockCustomer.setEmail("dummy@email.com");
		mockCustomer.setPassword("dummy");
		Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(mockCustomer);
		
		Order mockResponse = new Order();
		mockResponse.setOrderId(Long.valueOf(15));
		Mockito.when(orderRepository.save(Mockito.any())).thenReturn(mockResponse);
		
		Long res = manager.createOrder(req, authentication);
		assertEquals(res, Long.valueOf(15));
		
	}
	
	@Test
	public void getOrderForCustomerTest() {
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
		
		Mockito.when(orderRepository.findByCustomerId(Mockito.anyLong())).thenReturn(list);
		
		List<Order> response = manager.getOrdersForCustomer(authentication);
		assertEquals(response.size(), 1);
	}

}
