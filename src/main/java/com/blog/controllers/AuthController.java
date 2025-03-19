package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiException;
import com.blog.payloads.JwtAuthRequest;
import com.blog.payloads.JwtAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JwtTokenHelper;
import com.blog.service.UserService;

@RestController // to expose APIs
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	// a utility class that generates JWT token, used to create token for authenticated user

	@Autowired
	private UserDetailsService userDetailsService;
	// spring security service that loads user-specific data (username, password, role, etc)

	@Autowired
	private AuthenticationManager authenticationManager;
	// handles authentication logic

	@Autowired
	private UserService userService;

	// returns JWT auth response to the client
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			// generates the JWT token upon successful authentication
			@RequestBody JwtAuthRequest request
			) throws Exception
	{
		this.authenticate(request.getUsername(), request.getPassword());
		// verify that (username, password) exists in the database or not

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		// agar exist karta hai ek naya token create kardete hai
		
		// ab is token ko bhejna hai
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		// response me token set karre hai

		// return karderhe hai reponseEntity jisme token aur status code set karre hai
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		   
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		// creates a token for entered [username, password]

		try {
			this.authenticationManager.authenticate(authenticationToken);
			// authentication manager ke help se authentication check karre hai jo naya token create hua hai
		}catch(BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			// Exception handling karre hai yaha pe
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	// register new user in the system
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto  userDto)
	{
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.OK);
	}
}

/*
Workflow Summary

Login Flow (/login)
- User sends a POST request with credentials (username, password) in the request body.
- AuthController authenticates the user via AuthenticationManager.
- Upon successful authentication:
- Loads user details using UserDetailsService.
- Generates a JWT token using jwtTokenHelper.
- Sends the token back to the client in the response.

Registration Flow (/register)
- User sends a POST request with user details (UserDto) in the request body.
- AuthController delegates the request to UserService to handle the registration logic.
- Newly registered user details are sent back in the response.
 */
