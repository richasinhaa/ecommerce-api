package com.turing.turingproject.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turing.turingproject.exception.ResourceNotFoundException;
import com.turing.turingproject.model.Customer;
import com.turing.turingproject.model.CustomerDTO;
import com.turing.turingproject.repository.CustomerRepository;
import com.turing.turingproject.security.ApplicationSecurityConfigurerParams;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	String jwtToken = "";

	@Autowired
	ApplicationSecurityConfigurerParams configurerParams;

	// Customer Registration
	@PostMapping("/customers")
	public ResponseEntity<CustomerDTO> signUp(@RequestBody Customer user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		try {
			customerRepository.save(user);
		} catch (Exception e) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		jwtToken = Jwts.builder().setSubject(user.getEmail()).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

		HttpHeaders headers = new HttpHeaders();
		headers.add("accessToken", jwtToken);

		CustomerDTO dto = new CustomerDTO();
		Customer customer = customerRepository.findByName(user.getName());
		dto.setCustomer(customer);
		dto.setAccessToken("Bearer " + jwtToken);
		dto.setExpiresIn("24h");

		return new ResponseEntity<CustomerDTO>(dto, headers, HttpStatus.OK);
	}

	// Customer Login
	@PostMapping("/customers/login")
	public ResponseEntity<CustomerDTO> login(@RequestBody Customer user) throws ResourceNotFoundException {

		String jwtToken = "";

		if (user.getEmail() == null || user.getPassword() == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		String email = user.getEmail();
		String password = user.getPassword();

		Customer customer = customerRepository.findByEmail(email);

		if (customer == null) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		String pwd = customer.getPassword();

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		if (!bCryptPasswordEncoder.matches(password, pwd)) {
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");
		}

		jwtToken = Jwts.builder().setSubject(customer.getEmail()).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, configurerParams.getSecret()).compact();

		HttpHeaders headers = new HttpHeaders();
		headers.add("accessToken", jwtToken);

		CustomerDTO dto = new CustomerDTO();
		dto.setCustomer(customer);
		dto.setAccessToken("Bearer " + jwtToken);
		dto.setExpiresIn("24h");

		return new ResponseEntity<CustomerDTO>(dto, headers, HttpStatus.OK);
	}

	// Update a Single Customer
	@PutMapping("/customer/{customer_id}")
	public Customer updateCustomer(@RequestBody Customer customer,
			@PathVariable(value = "customer_id", required = true) Long customerId) {

		Optional<Customer> cust = customerRepository.findById(customerId);

		if (!cust.isPresent())
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");

		customerRepository.updateCustomer(customerId, customer.getName(), customer.getEmail(), customer.getCity(),
				customer.getAddress1(), customer.getCreditCard());

		return customer;
	}

	// Get a Single Customer
	@GetMapping("/customer/{customer_id}")
	public Customer getCustomerById(@PathVariable(value = "customer_id", required = true) Long customerId) {
		return customerRepository.findById(customerId).orElseThrow(
				() -> new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500"));
	}

	// Update Customer Address
	@PutMapping("/customers/address/{customer_id}")
	public Customer updateCustomerAddress(@RequestBody Customer customer,
			@PathVariable(value = "customer_id", required = true) Long customerId) {

		Optional<Customer> cust = customerRepository.findById(customerId);

		if (!cust.isPresent())
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");

		customerRepository.updateCustomer(customerId, customer.getName(), customer.getEmail(), customer.getCity(),
				customer.getAddress1(), customer.getCreditCard());

		return customer;
	}

	// Update Customer Address
	@PutMapping("/customers/creditCard/{customer_id}")
	public Customer updateCustomerCreditCard(@RequestBody Customer customer,
			@PathVariable(value = "customer_id", required = true) Long customerId) {

		Optional<Customer> cust = customerRepository.findById(customerId);

		if (!cust.isPresent())
			throw new ResourceNotFoundException("USR_02", "The field example is empty.", "example", "500");

		customerRepository.updateCustomer(customerId, customer.getName(), customer.getEmail(), customer.getCity(),
				customer.getAddress1(), customer.getCreditCard());

		return customer;
	}

}
