package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberValidator {
	
	private final MemberMapper memberMapper;
	
	
	// 방법 1.(정석) validator 필드가 늘어날 때마다  매개변수에 추가 해야되는게 늘어나니, 좀 더 편하게 할 수 있는 방법은
	// 방법 2.(lombak) 생성자 직접 생성이 아닌 @RequiredArgsConstructor 써준다(lombak 필요)
	/*@Autowired
	public MemberValidator(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}*/
	
	
	// 하나의 메소드에는 하나의 기능만 수행
	private void checkNull(MemberDTO member) {
		if(member == null) {
			throw new NullPointerException("잘못된 접근입니다.");
		}
	}
	
	private void checkLength(MemberDTO member) {
		if(member.getUserId().length() > 20) {
			throw new TooLargeValueException("아이디 값이 너무 길어요");
		}
	}
	
	private void checkBlank(MemberDTO member) {
		if(member.getUserId() == null || member.getUserId().trim().isEmpty() || member.getUserPwd() == null || member.getUserPwd().trim().isEmpty()) {
			throw new InvalidArgumentsException("유효하지 않은 값입니다.");
		}
	}
	
	public void validatedMember(MemberDTO member) {
		checkNull(member);
		checkLength(member);
		checkBlank(member);
	}
	
	public void validatedUpdateMember(MemberDTO member, MemberDTO sessionMember) {
		checkNull(member);
		checkNull(sessionMember);
		
		// 딱히 다른 곳에서 쓸 일이 없으니 여기서 작업
		if(!member.getUserId().equals(sessionMember.getUserId())){
			// 의도한 상황이 안어서 억지로 예외 발생 시킨다.
			
			// 인증(권한이 없는가)/ 인가(권한이 있는가) sercurity framework core있지만 만들어 사용해보기
			throw new AuthenticationException("권한없는 접근입니다.");
		}
		
		checkNull(memberMapper.login(member)); // member 든 sessionMember 넘기든 상관 없다. 체크만 하는거라서
		
	}
	
}
