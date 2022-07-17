package com.cts.controller;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.config.JWTTokenHelper;
import com.cts.entity.User;
import com.cts.request.AuthenticationRequest;
import com.cts.response.LoginResponse;
import com.cts.response.UserInfo;

/**
 * CrossOrigin is require for connection of frontend and backend so that data
 * display on frontend. RestController is an annotation for RESTful web
 * services.
 * 
 * @author POD4
 * 
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	/**
	 * Autowired is used for creating object of a class and from this we can use its
	 * members.
	 */

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	JWTTokenHelper jWTTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * This api takes USERNAME and PASSWORD and after submit these details we got a
	 * bearer token if the credentials are correct other wise give error.
	 * 
	 * @param authenticationRequest
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */

	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
						authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		String jwtToken = jWTTokenHelper.generateToken(user.getUsername());

		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);

		return ResponseEntity.ok(response);
	}

	/**
	 * This api consume token and give access to the user.
	 * 
	 * @param user
	 * @return
	 */

	@GetMapping("/auth/userinfo")
	public ResponseEntity<?> getUserInfo(Principal user) {
		User userObj = (User) userDetailsService.loadUserByUsername(user.getName());

		UserInfo userInfo = new UserInfo();
		userInfo.setFirstName(userObj.getFirstName());
		userInfo.setLastName(userObj.getLastName());
		userInfo.setRoles(userObj.getAuthorities().toArray());

		return ResponseEntity.ok(userInfo);

	}
}
