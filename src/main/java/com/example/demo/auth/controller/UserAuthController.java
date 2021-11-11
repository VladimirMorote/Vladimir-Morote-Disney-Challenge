package com.example.demo.auth.controller;

import javax.validation.Valid;

import com.example.demo.auth.dto.AuthenticationRequest;
import com.example.demo.auth.dto.AuthenticationResponse;
import com.example.demo.auth.dto.UserDTO;
import com.example.demo.auth.service.JwtUtils;
import com.example.demo.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class UserAuthController {
	
	@Autowired
	private UserDetailsCustomService userServ;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authManager;
	
	// Signup
	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponse> signUp(@Valid @RequestBody UserDTO userToCreate) throws Exception {
		boolean isCreated = userServ.signupUser(userToCreate);
		if(isCreated) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
	}
	
	// SignIn
	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest authReq) throws Exception {

		// THIS SHOULD BE IN A SIGNIN SERVICE

		UserDetails userDetails;
		try {

			Authentication auth = authManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								authReq.getUsername(),
								authReq.getPassword()
								)						
					);	
		

			userDetails = (UserDetails) auth.getPrincipal();
			
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);			
		} 
		
		// ONCE GET userDetails, LET'S VERIFY Authentification
		// LET'S GENERATE A TOKEN WITH EACH USER.
		final String jwt = jwtUtils.generateToken(userDetails);
		
		// LET'S SEND JWT AS AUTHENTICATION RESPONSE
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
