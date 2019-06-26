package com.turing.turingproject.security;

public class SecurityConstants {
	
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864000000; 
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/customers";
    public static final String LOGIN_URL = "/customers/login";

}
