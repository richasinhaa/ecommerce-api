package com.turing.turingproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {
	
	private Customer customer;
	
	private String accessToken;
	
	@JsonProperty("expires_in")
	private String expiresIn;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(Customer customer, String accessToken, String expiresIn) {
		super();
		this.customer = customer;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessToken == null) ? 0 : accessToken.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((expiresIn == null) ? 0 : expiresIn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDTO other = (CustomerDTO) obj;
		if (accessToken == null) {
			if (other.accessToken != null)
				return false;
		} else if (!accessToken.equals(other.accessToken))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (expiresIn == null) {
			if (other.expiresIn != null)
				return false;
		} else if (!expiresIn.equals(other.expiresIn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerDTO [customer=" + customer + ", accessToken=" + accessToken + ", expiresIn=" + expiresIn + "]";
	}

}
