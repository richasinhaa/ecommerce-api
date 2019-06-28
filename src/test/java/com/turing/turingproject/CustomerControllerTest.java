package com.turing.turingproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.turing.turingproject.controller.CustomerController;
import com.turing.turingproject.model.Customer;
import com.turing.turingproject.model.CustomerDTO;
import com.turing.turingproject.repository.CustomerRepository;
import com.turing.turingproject.security.ApplicationSecurityConfigurerParams;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerControllerTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	ApplicationSecurityConfigurerParams configurerParams;

	@InjectMocks
	CustomerController controller;

	@Test
	public void getCustomerByIdTest() {
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

		Customer response = controller.getCustomerById(authentication);

		assertEquals(response.getEmail(), "dummy@email.com");

	}
	
	@Test
	public void updateCustomerTest() {
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

		Mockito.doNothing().when(customerRepository).updateCustomer(Mockito.any(), Mockito.anyString(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

		Customer response = controller.updateCustomer(authentication, mockCustomer);

		assertEquals(response.getEmail(), "dummy@email.com");

	}
	
	@Test
	public void updateCustomerAddressTest() {
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

		Mockito.doNothing().when(customerRepository).updateCustomer(Mockito.any(), Mockito.anyString(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

		Customer response = controller.updateCustomerAddress(authentication, mockCustomer);

		assertEquals(response.getEmail(), "dummy@email.com");

	}
	
	@Test
	public void updateCustomerCreditCardTest() {
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

		Mockito.doNothing().when(customerRepository).updateCustomer(Mockito.any(), Mockito.anyString(), 
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

		Customer response = controller.updateCustomerCreditCard(authentication, mockCustomer);

		assertEquals(response.getEmail(), "dummy@email.com");

	}
	
	@Test
	public void signUpTest() {
		Customer mockCustomer = new Customer();
		mockCustomer.setEmail("dummy@email.com");
		mockCustomer.setPassword("dummy");
		
		Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("abc1-jh-yub-3-idhdjdgdj-");
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(mockCustomer);

		ResponseEntity<CustomerDTO> response = controller.signUp(mockCustomer);

		assertNotNull(response.getBody().getAccessToken());

	}
	
	@Test
	public void loginTest() {
		Customer mockCustomer = new Customer();
		mockCustomer.setEmail("dummy@email.com");
		mockCustomer.setPassword("dummy");
		
		Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(mockCustomer);
		Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn("abc1-jh-yub-3-idhdjdgdj-");
		Mockito.when(bCryptPasswordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(Boolean.TRUE);
		Mockito.when(configurerParams.getSecret()).thenReturn("dummyString");

		ResponseEntity<CustomerDTO> response = controller.login(mockCustomer);

		assertNotNull(response.getBody().getAccessToken());

	}

}
