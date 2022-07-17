package com.cts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {
	
	@GetMapping
	public String helloFunction() {
		return "Welcome in Auth service";
	}
}
