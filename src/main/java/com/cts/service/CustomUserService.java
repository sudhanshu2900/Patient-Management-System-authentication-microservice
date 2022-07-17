package com.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.entity.User;
import com.cts.repository.UserDetailsRepository;

@Service
public class CustomUserService implements UserDetailsService{

	@Autowired
	UserDetailsRepository userDetailsRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDetailsRepository.findByUserName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("UserName Not Found with username "+username);
		}
		return user;
	}
	
}
