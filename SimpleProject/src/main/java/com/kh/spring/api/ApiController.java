package com.kh.spring.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.api.model.service.ApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="api", produces="application/json; charset=UTF-8")
public class ApiController {
	
	private final ApiService apiService;
	
	@GetMapping("beef")
	public String getBeef() {
		
//		String response = apiService.requestBeef();
//		AjaxResponse ar = AjaxResponse.builder().code("200").message("호호호호").data(response).build();
		return apiService.requestBeef();
	}
	
	
	@GetMapping("blog")
	public String getBlog(@RequestParam(name="query") String query) {
		return apiService.requestBlog(query);
	}
	
	@GetMapping("busan")
	public String getBusan(@RequestParam(name="pageNo") int pageNo) {
		return apiService.requestBusan(pageNo);
	}
	
	
	@GetMapping("busan/{num}")
	public String getBusanDetail(@PathVariable("num") int num) {
		System.out.println("ApiController");
	    return apiService.requestBusanDetail(num);
	}
	
}
