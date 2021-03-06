package com.api.ecommerce.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.ecommerce.model.Customer;
import com.api.ecommerce.repository.CustomerRepository;

import static java.util.Collections.emptyList;

@Service
public class CustomerManager implements UserDetailsService {
	@Autowired
	CustomerRepository customerRepository;
	
	/**
	 * Returns user details for a given username
	 *
	 * @param username - User Name
	 * @return - UserDetails
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByName(username);
		if (customer == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(customer.getName(), customer.getPassword(), emptyList());
	}

}
