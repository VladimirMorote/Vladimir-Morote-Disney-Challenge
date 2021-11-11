package com.example.demo.auth.config;

import com.example.demo.auth.filter.JwtRequestFilter;
import com.example.demo.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;





@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	// LET'S IMPORT CustomService y CustomFilter.
	@Autowired
	private UserDetailsCustomService userDetailsCustomService;
	@Autowired
	private JwtRequestFilter jwtReqFilter;
	
	// --- MUST ---
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
			
	// PREVENT PASWORD ENCODING: THIS WAS USED JUST FOR THIS PROJECT. YOU SHOUDN'T USE IT AGAIN.
	// DEPRECATED NUNCA USAR


	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	// 1) DEFINA IN USER DETAILS -> CREATES IN SERVICE (UserDetailsCustomService)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsCustomService);
	}
	
	// 2) Main Configure:
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		// DISABLE CSRF
		httpSecurity.csrf().disable()
		// PERMIT All AROUND de "/auth/*"  THIS COUDT BE DANGEROUS
		.authorizeRequests().antMatchers("/auth/*").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling()
		.and().sessionManagement()
		// Policy = For Each Endpoint Called, NEW Headers. (Always Request Auth, stateless)
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// ADDING FILTER BEFORE PROCESSING REQUEST ALWAYS
		httpSecurity.addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
