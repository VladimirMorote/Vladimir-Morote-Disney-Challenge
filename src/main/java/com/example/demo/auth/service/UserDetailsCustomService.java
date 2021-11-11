package com.example.demo.auth.service;

import java.util.Collections;

import javax.validation.Valid;

import com.example.demo.auth.dto.UserDTO;
import com.example.demo.auth.entity.UserEntity;
import com.example.demo.auth.repository.UserRepository;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsCustomService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailServ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		

		UserEntity foundUser = userRepo.findByUsername(username);
		
		// IF No User then Exception
		if (foundUser == null) {
			throw new UsernameNotFoundException("Username: " + username + " -> NOT FOUND");
		}
		
		// IF User: Let's create with data at User got
		return new User(
				foundUser.getUsername(),
				foundUser.getPassword(), 
				Collections.emptyList() // Roles
			);
	}
	
	// Signup New User
	public boolean signupUser(@Valid UserDTO userToCreate) {

		UserEntity newUser = new UserEntity();
		newUser.setUsername(userToCreate.getUsername());
		newUser.setPassword(userToCreate.getPassword());	
		// === 
		
		UserEntity matchingUser = userRepo.findByUsername(userToCreate.getUsername());			
		if(matchingUser != null && (matchingUser.getUsername().equals(newUser.getUsername()))) {
			// Already Exists
			// The Controller verify TRUE or FALSE and send ResponseEntity
			return false;
		}		
		newUser = userRepo.save(newUser);
		
		//Email Stuff:
		if(newUser != null) {
			emailServ.sendWelcomeEmail(newUser.getUsername());
		}
		
		return newUser != null;		
	}	

}
