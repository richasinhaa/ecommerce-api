package com.turing.turingproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.turing.turingproject.manager.CustomerManager;
import static com.turing.turingproject.security.SecurityConstants.SIGN_UP_URL;
import static com.turing.turingproject.security.SecurityConstants.LOGIN_URL;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	CustomerManager customerManager;

	BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(CustomerManager customerManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customerManager = customerManager;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js").permitAll()
				.antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
				.antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.GET).permitAll()
				.antMatchers(HttpMethod.PUT).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter()).addFilter(new JWTAuthorizationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

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

}
