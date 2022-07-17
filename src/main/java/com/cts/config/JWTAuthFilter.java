package com.cts.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthFilter extends OncePerRequestFilter{
	
	private UserDetailsService userDetailsService;
	private JWTTokenHelper jwtTokenHelper;
	
	public JWTAuthFilter(UserDetailsService userDetailsService,JWTTokenHelper jwtTokenHelper) {
		this.userDetailsService=userDetailsService;
		this.jwtTokenHelper=jwtTokenHelper;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)			//It will execute on each and every request
			throws ServletException, IOException {
		
		String authToken=jwtTokenHelper.getToken(request);																		//It hold our token
		
		if(authToken != null) {			
			String userName=jwtTokenHelper.getUsernameFromToken(authToken);														
			
			if(null!=userName) {				
				UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
				
				if(jwtTokenHelper.validateToken(authToken, userDetails)) {					
					UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}	
			}			
		}
		
		filterChain.doFilter(request, response);
		
	}

	
}
