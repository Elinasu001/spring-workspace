package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.exception.TooLargeValueException;
import com.kh.spring.member.model.dao.MemberRepository;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public MemberDTO login(MemberDTO member) {
		
		// sqlSession은 root-context.xml 의 bean에 등록 해놔서 기존에 sql했던 방식은 x
		// SqlSession sqlSession = Template.getSession();
		// MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		// sqlSession.close();
		// return loginMember;
		
		log.info("나 불렀어?");
		
		// ver 0.1
		return memberRepository.login(sqlSession, member);
	}

	@Override
	public void singUp(MemberDTO member) {
		// 반환 타입 없는데
		// 꼼꼼하게 검증해보자
		// 유효값 검증
		// ver 0.2
		if(member == null) {
			return; // null 이니 뭐 할게 없으니 돌려 보내기
		}
		
		// ID 값이 20자가 넘으면 안됨.
		// 정규 표현식으로 만드는게 쉽지만 어렵게 가보자
		if(member.getUserId().length() > 20) {
			throw new TooLargeValueException("아이디 값이 너무 길어요"); // 객체로 생성해준다.
		}
		
		if(member.getUserId() == null || member.getUserId().trim().isEmpty() || member.getUserPwd() == null || member.getUserPwd().trim().isEmpty()) {
			return;
		}
		
	}

	@Override
	public void update(MemberDTO member) {
	}

	@Override
	public void delete(MemberDTO member) {
	}

}
