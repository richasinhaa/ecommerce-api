package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.turing.turingproject.controller.CustomerController;
import com.turing.turingproject.model.Customer;
import com.turing.turingproject.model.Department;
import com.turing.turingproject.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	
	@Mock
    private CustomerRepository customerRepository;
	
	@InjectMocks
    CustomerController controller;
	
	@Test
    public void getCustomerByIdTest() {
		Long id = Long.valueOf(1);
		Customer c = new Customer();
		c.setCustomerId(Long.valueOf(1));
		c.setName("dummy");
		c.setEmail("dummy@email.com");
		Optional<Customer> mockCustomer = Optional.of(c);

		Mockito.when(customerRepository.findById(id))
				.thenReturn(mockCustomer);

		Customer response = controller.getCustomerById(id);

		assertEquals(response.getEmail(), "dummy@email.com");
		
    }

}
