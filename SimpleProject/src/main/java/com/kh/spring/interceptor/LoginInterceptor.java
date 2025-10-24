package com.kh.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kh.spring.exception.AuthenticationException;

/**
 * Interceptor
 * 
 * RequestHandler 가 호출되기 전 또는 수행후 실행할 내용을 만들어줄 수 있음
 * 
 * preHandler(전처리) : 핸들러 수행전 낚아챔
 * postHandler(후처리): 핸들러 수행후 낚아챔
 * 
 * 
 * **/
public class LoginInterceptor extends HandlerInterceptorAdapter{//  : 인증인가 Spring Seruity로 바뀌어 - 그어짐
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginMember") != null) {
			return true; // 로그인 o
		} else {
			 // 세션이 없으면 로그인 페이지로 리다이렉트
			response.sendRedirect(request.getContextPath());
			return false; // 로그인 x
		}
		
		//throw new AuthenticationException("로그인해라!");  // 상단에 예외처리 필요
	}
	
}
