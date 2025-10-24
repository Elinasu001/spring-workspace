package com.kh.spring.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.api.model.service.ApiService;

@Controller
public class ForwardController {
	
	private ApiService apiService;
	
	@GetMapping("beef")
	public String toBeef() {
		return "api/blog";
	}
	
	
	
}
