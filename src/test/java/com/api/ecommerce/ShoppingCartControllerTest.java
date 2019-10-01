package com.api.ecommerce;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.ecommerce.controller.ShoppingCartController;
import com.api.ecommerce.exception.ResourceNotFoundException;
import com.api.ecommerce.manager.ShoppingCartManager;
import com.api.ecommerce.model.CreateOrderRequest;
import com.api.ecommerce.model.Product;
import com.api.ecommerce.model.ShoppingCart;
import com.api.ecommerce.model.UpdateCartQuantityRequest;
import com.api.ecommerce.repository.CustomerRepository;
import com.api.ecommerce.repository.ProductRepository;
import com.api.ecommerce.repository.ShoppingCartRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ShoppingCartControllerTest {

	@Mock
	ShoppingCartManager shoppingCartManager;

	@Mock
	ShoppingCartRepository shoppingCartRepository;

	@Mock
	ProductRepository productRepository;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	ShoppingCartController controller;

	@Test
	public void createCartTest() throws JSONException {
		Mockito.when(shoppingCartManager.generateUniqueId()).thenReturn("some-key");
		ResponseEntity<String> res = controller.createCart();
		assertEquals(res.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void addProductTest() throws JSONException {
		CreateOrderRequest req = new CreateOrderRequest();
		req.setAttributes("some-attr");
		req.setCartId("some-id");
		req.setProductId(Long.valueOf(1));
		
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		Mockito.when(shoppingCartManager.addProduct(Mockito.anyString(), Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(c);
		ShoppingCart res = controller.addProduct(req);
		assertEquals(res.getCartId(), "some-id");

	}
	
	@Test
	public void getCartByIdSuccessTest() throws JSONException {
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		list.add(c);
		
		Mockito.when(shoppingCartRepository.findByCartId(Mockito.anyString()))
				.thenReturn(list);
		List<ShoppingCart> res = controller.getCartById("some-id");
		assertEquals(res.get(0).getCartId(), "some-id");
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getCartByIdFailureTest() throws JSONException {
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		
		Mockito.when(shoppingCartRepository.findByCartId(Mockito.anyString()))
				.thenReturn(list);
		controller.getCartById("some-id");
	}
	
	@Test
	public void updateCartSuccessTest() throws JSONException {
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		
		UpdateCartQuantityRequest r = new UpdateCartQuantityRequest();
		r.setQuantity(10);
		
		Mockito.when(shoppingCartRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(c));
		List<ShoppingCart> res = controller.createReview(Long.valueOf(1), r);
		assertEquals(res.get(0).getQuantity(), Integer.valueOf(10));
	}
	
	@Test
	public void deleteCartByIdSuccessTest() {
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		List<ShoppingCart> l = new ArrayList<ShoppingCart>();
		l.add(c);
		
		Mockito.doNothing().when(shoppingCartRepository).deleteByCartId(Mockito.anyString());
		Mockito.when(shoppingCartRepository.findByCartId(Mockito.anyString())).thenReturn(l);
		
		List<ShoppingCart> res = controller.deleteCartById("some-id");
		
		assertEquals(res.get(0).getItemId(), Long.valueOf(1));
	}
	
	@Test
	public void getTotalAmountByCartIdTest() {
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		c.setSubTotal(Double.valueOf(10.99));
		List<ShoppingCart> l = new ArrayList<ShoppingCart>();
		l.add(c);
		
		Mockito.when(shoppingCartRepository.findByCartId(Mockito.anyString())).thenReturn(l);
		
		ResponseEntity<String> res = controller.getTotalAmountByCartId("some-id");
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
		
	}
	
	@Test
	public void saveAProductForLaterTest() {
		Mockito.doNothing().when(shoppingCartRepository).updateBuyLater(Mockito.anyLong());
		controller.saveAProductForLater(Long.valueOf(1));
	}
	
	@Test
	public void getSavedForLaterSuccessTest() {
		ShoppingCart c = new ShoppingCart();
		c.setCartId("some-id");
		c.setItemId(Long.valueOf(1));
		c.setProductId(Long.valueOf(1));
		c.setQuantity(1);
		c.setSubTotal(Double.valueOf(10.99));
		
		Product p = new Product();
		p.setProductId(Long.valueOf(1));
		p.setName("p1");
		p.setPrice(Double.valueOf(10.99));
		
		Mockito.when(shoppingCartRepository.findSavedForLaterByCartId(Mockito.anyString())).thenReturn(c);
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(p));
		
		ResponseEntity<String> res = controller.getSavedForLater("some-id");
		
		assertEquals(res.getStatusCode(), HttpStatus.OK);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void getSavedForLaterNullCartTest() {
		Mockito.when(shoppingCartRepository.findSavedForLaterByCartId(Mockito.anyString())).thenReturn(null);
		
		controller.getSavedForLater("some-id");
	}
	
	@Test
	public void removeProductFromCartTest() {
		Mockito.doNothing().when(shoppingCartRepository).deleteById(Mockito.anyLong());
		controller.removeProductFromCart(Long.valueOf(1));
	}

}
