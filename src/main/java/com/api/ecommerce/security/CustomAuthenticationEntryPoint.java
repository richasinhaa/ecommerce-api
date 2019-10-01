package com.api.ecommerce.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	/**
	 * Throws error message for unauthorised requests
	 *
	 * @param req - Http Servlet Request
	 * @param res - Http Servlet Response
	 * @param authException - Authentication Exception
	 */
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        try {
			res.getWriter().write(new JSONObject()
			        .put("code", "AUT_02")
			        .put("message", "The apikey is invalid.")
			        .put("field", "USER-KEY")
			        .toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
