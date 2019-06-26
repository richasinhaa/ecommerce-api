package com.turing.turingproject.security;

import static com.turing.turingproject.security.SecurityConstants.LOGIN_URL;
import static com.turing.turingproject.security.SecurityConstants.SIGN_UP_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.turing.turingproject.manager.CustomerManager;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	CustomerManager customerManager;

	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final ApplicationSecurityConfigurerParams configurerParams;
	
	@Autowired
	public WebSecurity(CustomerManager customerManager, BCryptPasswordEncoder bCryptPasswordEncoder, 
			ApplicationSecurityConfigurerParams configurerParams) {
		this.customerManager = customerManager;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.configurerParams = configurerParams;
	}
	
	/**
	 * Configure authorized/unauthorized endpoints and other settings
	 *
	 * @param http - Http Security
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js").permitAll()
				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
				.antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.PUT, "/customer/**").authenticated()
				.antMatchers(HttpMethod.GET, "/customer/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/customers/address/**").authenticated()
				.antMatchers(HttpMethod.PUT, "/customers/creditCard/**").authenticated()
				.antMatchers(HttpMethod.POST, "/orders").authenticated()
				.antMatchers(HttpMethod.POST, "/orders/**").authenticated()
				.antMatchers(HttpMethod.POST, "/products/**").authenticated()
				.antMatchers(HttpMethod.GET, "/orders/inCustomer").authenticated()
				.anyRequest().permitAll()
				.and()
				.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                .and()
				.addFilter(new JWTAuthenticationFilter()).addFilter(new JWTAuthorizationFilter(authenticationManager(), 
						configurerParams))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/**
	 * Configure
	 *
	 * @param auth - Authentication Manager Builder
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customerManager).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	@Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}
