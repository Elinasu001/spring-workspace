package com.kh.spring.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	//요청받아서 처리해주는 RequestHandler
	/*
	 * 응답할 데이터를 문자열로 반환
	 * ModelAndView의 viewName필드에 return 한 문자열값이 대입
	 * => DispatcherServlet
	 * => ViewResolver
	 * 
	 * 반환하는 String타입의 값이 View의 정보가 아닌 응답데이터라는 것을 명시해서
	 * => MessageConverter라는 빈으로 이동하게끔
	 * 
	 * @ResponseBody
	 */
	
	@ResponseBody
	@GetMapping(value="test", produces="text/html; charset=UTF-8") // ??? ?? ?? => produces
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("잘넘어옴? {}", value);
		// db에 잘 다녀왔다고 가정
		// 오늘 점심은 짬뽕이다! -> 조회해옴
		String lunchMenu = "오늘 점심은 짬뽕이당";
		return lunchMenu; // JSP?? => @ResponseBody
	}
	
}
