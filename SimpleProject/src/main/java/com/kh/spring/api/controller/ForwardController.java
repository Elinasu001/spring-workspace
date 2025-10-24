package com.kh.spring.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kh.spring.api.model.service.ApiService;

@Controller
public class ForwardController {
	
	private ApiService apiService;
	
	@GetMapping("beef")
	public String toBeef() {
		return "api/blog";
	}
	
	@GetMapping("blog")
	public String toBlog() {
		return "api/blog";
	}
	
	@GetMapping("map")
	public String toMap() {
		return "api/map";
	}
	
	@GetMapping("busan")
	public String toBusan() {
		return "api/busan";
	}
	
	@GetMapping("busan/detail/{num}")
	public String toDetail(Model model, @PathVariable(value="num") int num) {
		System.out.println("ForwardController");
		model.addAttribute("num", num);
		return "api/detail";
	}
}
