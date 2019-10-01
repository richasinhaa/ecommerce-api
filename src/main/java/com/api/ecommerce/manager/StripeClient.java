package com.api.ecommerce.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.model.Charge;

@Component
public class StripeClient {
	
	@Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;
	
	/**
	 * Returns Charge
	 *
	 * @param token - Token
	 * @param amount - Amount
	 * @param orderId - Order Id
	 * @param description - Description
	 * @param currency - Currency
	 * @return - Charge
	 */
	public Charge chargeCreditCard(String token, Integer amount, Long orderId, String description, String currency)
			throws Exception {
		Charge charge = null;
		try {
			Stripe.apiKey = API_SECRET_KEY;
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", amount);
			chargeParams.put("currency", "USD");
			chargeParams.put("source", token);
			Map<String, String> metadata = new HashMap<>();
			metadata.put("order_id", String.valueOf(orderId));
			chargeParams.put("metadata", metadata);
			charge = Charge.create(chargeParams);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		return charge;
	}
}
