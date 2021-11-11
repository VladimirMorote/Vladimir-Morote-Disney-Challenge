package com.example.demo.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.auth.service.JwtUtils;
import com.example.demo.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsCustomService userDetailsCS;
	
	@Autowired
	private JwtUtils jwtUtils; // EXTRACT AND VALIDATE.
	
	@Autowired
	private AuthenticationManager authManager;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Get Headers for Authorization:
		final String authHeader = request.getHeader("Authorization");
		
		String username = null;
		String jwt = null;
		
		// Extraemos TOKEN del HEADER
		// Extraemos el USERNAME usando JwtUtils.
		if (authHeader != null && authHeader.startsWith("Bearer ")) {			
			jwt = authHeader.substring(7); // 7 char before Token.
			username = jwtUtils.extractUsername(jwt); // let's get Username.
		}
		
		// && If USERNAME is NOT AUTHENTICATED YET (NOT in Context)
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			// 1)
			// Let's get UserDetails: from this username besidas Pass
			UserDetails userDetails = userDetailsCS.loadUserByUsername(username);
			
			// 2)
			// Validation: Jwt and Object, let's chack user created this is user y password
			if(jwtUtils.validateToken(jwt, userDetails)) {
				
				// Creamos la AuthRequest con UserDetails
				UsernamePasswordAuthenticationToken authReq =
						new UsernamePasswordAuthenticationToken(
								userDetails.getUsername(),
								userDetails.getPassword()								
								);	
				
				// Let's keep AuthResponse for Setting in cpntext:
				Authentication auth = authManager.authenticate(authReq);
				
				// Let's set AUTH at CONTEXT
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);		
	}

}
