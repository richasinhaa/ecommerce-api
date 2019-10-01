package com.api.ecommerce.security;

import static com.api.ecommerce.security.SecurityConstants.EXPIRATION_TIME;
import static com.api.ecommerce.security.SecurityConstants.HEADER_STRING;
import static com.api.ecommerce.security.SecurityConstants.SECRET;
import static com.api.ecommerce.security.SecurityConstants.TOKEN_PREFIX;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.ecommerce.model.Customer;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	/**
	 * Returns authentication
	 *
	 * @param req - Http Servlet Request
	 * @param res - Http Servlet Response
	 * @return - Authentication
	 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationServiceException {
        try {
            Customer creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Customer.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getName(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
	 * Successful Authentication
	 *
	 * @param req - Http Servlet Request
	 * @param res - Http Servlet Response
	 * @param chain - Filter Chain
	 * @param auth - Authentication
	 */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}
