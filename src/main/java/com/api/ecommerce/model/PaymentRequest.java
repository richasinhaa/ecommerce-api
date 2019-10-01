package com.api.ecommerce.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequest implements Serializable {
	@JsonProperty("stripeToken")
	String stripeToken;
	
	@JsonProperty("order_id")
	Long orderId;
	
	@JsonProperty("description")
	String description;
	
	@JsonProperty("amount")
	Integer amount;
	
	@JsonProperty("currency")
	String currency = "USD";

	public String getStripeToken() {
		return stripeToken;
	}

	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
