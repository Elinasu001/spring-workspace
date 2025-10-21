package com.kh.spring.exception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.exception.UserIdNotFoundException;
import com.kh.spring.exception.handleGeneralException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	// sqlException 추가 해주면 더 좋음
	// 각가 다른 것들을 동일하게 사용하려면 RuntimeException 다 부모 타이ㅂㄹ로 상속받고 있으니 여기에 RuntimeException 타입을 넣어 공통으로 사용한다.
	private ModelAndView createErrorResponse(RuntimeException e) {
		//log.info("{}", e.getMessage());
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", e.getMessage()).setViewName("include/error_page");
		log.info("예외발생 : {}", e);
		return mv;
	}
	
	// 로그인 처리중 아이디 존재하지 않을 경우 NullPointerException 예외 처리
	@ExceptionHandler(UserIdNotFoundException.class)
	protected ModelAndView idNotFoundError(UserIdNotFoundException e) {
		return createErrorResponse(e);
	}
	
	// 회원가입 처리 중 아이디 길이 초과 예외 처리
	@ExceptionHandler(TooLargeValueException.class)
	protected ModelAndView largeValueError(TooLargeValueException e) {
		return createErrorResponse(e);
	}
	
	
	// 회원가입 처리 중 잘못된 인자 전달 예외 처리
	@ExceptionHandler(InvalidArgumentsException.class)
	protected ModelAndView invalidArgumentError(InvalidArgumentsException e) {
		return createErrorResponse(e);
	}
	
	// 회원가입 처리 중 NullPointerException 등 일반 예외 처리
	@ExceptionHandler(handleGeneralException.class)
	protected ModelAndView duplicateKeyError(handleGeneralException e) {
		return createErrorResponse(e);
	}
	
	// 마이페이지 인증/인가
	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView authenticationError(AuthenticationException e) {
		return createErrorResponse(e);
	}

}	
