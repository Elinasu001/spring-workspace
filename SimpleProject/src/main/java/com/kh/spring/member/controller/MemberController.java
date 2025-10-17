package com.kh.spring.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.member.model.vo.Member;

// Controller를 bean으로 등록하기  메소드를 사용하려면 객체 생성을 해야되는데 ...
@Controller
public class MemberController {
		
	@RequestMapping("login")
	public void login(Member member) {
		// 1. 값 뽑기
		// 2. 데이터 가공
		System.out.println(member);
	}
	
}
