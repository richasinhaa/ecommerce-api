package com.turing.turingproject;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;

import com.stripe.Stripe;
import com.turing.turingproject.controller.PaymentController;
import com.turing.turingproject.manager.StripeClient;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class PaymentControllerTest {

	@Mock
	StripeClient stripeClient;

	@InjectMocks
	PaymentController controller;

	@Test
	public void chargeCardTest() throws Exception {
		/*PaymentRequest r = new PaymentRequest();
		r.setAmount(10);
		r.setCurrency("USD");
		
		Charge c = new Charge();
		c.setAmount(Long.valueOf(10));
		
		Mockito.when(stripeClient.chargeCreditCard(Mockito.anyString(), Mockito.anyInt(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyString())).thenReturn(c);
		
		Charge res = controller.chargeCard(r);
		
		assertEquals(res.getAmount(), Long.valueOf(10));*/
	}
	
	@Test
	public void createWebhookTest() throws Exception {
		Stripe.apiKey = "sk_test_ih4Q7uSGf0loISCMP4jI3siQ001TPSo467";
		String res = controller.createWebhook();
		assertEquals(res, "This endpoint is used by Stripe.");
	}

}
