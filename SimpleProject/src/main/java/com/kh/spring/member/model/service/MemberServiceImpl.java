package com.kh.spring.member.model.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.UserIdNotFoundException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	/*
	 * SRP(Single Responsibility Principle)
	 * 단 일 책 임 원 칙 위반
	 * 
	 * 하나의 클래스는(메소드) 하나의 책임만을 가져야함 == 얘가 수정되는 이유는 딱 하나여야함
	 * 
	 * 위반 시,
	 * 책임 분리하면 끝
	 */
	
	
	
	// 실무에서 잘 지켜지지 않을 수 있지만 권장사항이다., lombak 애노테이션(@RequiredArgsConstructor) 사용할 수 있으니 생성자 주석 처리 가능하다.
	//@Autowired
	//private final SqlSessionTemplate sqlSession;
	
	//@Autowired
	//private final MemberRepository memberRepository;
	
	//@Autowired  
	// 책임 분리
	private final PasswordEncoder passwordEncoder;
	private final MemberValidator validator;
	private final MemberMapper mapper;
	
	
	/*
	@Autowired
	public MemberServiceImpl(SqlSessionTemplate sqlSession, MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
		this.sqlSession = sqlSession;
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}
	*/
	
	@Override
	public MemberDTO login(MemberDTO member) {
		//log.info("나 불렀어?");
		
		// sqlSession은 root-context.xml 의 bean에 등록 해놔서 기존에 sql했던 방식은 x
		// SqlSession sqlSession = Template.getSession();
		// MemberDTO loginMember = new MemberDAO().login(sqlSession, member);
		// sqlSession.close();
		// return loginMember;
		
		// ver 0.1
		//return memberRepository.login(sqlSession, member);
		
		// 사용자는 평문을 입력하지만 db컬럼에는 암호문이 들어있기 때문에
		// 비밀번호 비교하는 SELECT문을 사용할 수 없음
		MemberDTO loginMember = mapper.login(member);
		/*
		if(loginMember == null) {
			// 정확하게 해주는 것보다 아이디 또는 비밀번호가 잘못됐습니다.라고 모르게 알려주는 게 보안적으로 좋다. 
			throw new UserIdNotFoundException(" 아이디 또는 비밀번호가 잘못됐습니다.");
		}
		
		// 1절
		log.info("사용자가 입력한 비밀번호 평문 : {}", member.getUserPwd());
		log.info("DB에 저장된 암호화된 암호문 : {}", loginMember.getUserPwd());
		
		// 아이디만 가지고 조회를 하기 때문에
		// 비밀번호를 검증 후 
		// 비밀번호가 유효한다면 ㅇㅋㅇㅋ
		// 비밀번호가 유효하지 않다면 이상함.    // 평문			// 암호문
		if(passwordEncoder.matches(member.getUserPwd(), loginMember.getUserPwd())) {
			// true
			return loginMember;
		} 
		*/
		return validateLoginMember(loginMember, member.getUserPwd());
		
	}
	// 실질적으로 셀렉트 하는 로직과  유효성검사하는 로직 책임 분리 하여  유지보수 용이
	// 유효성 검사 : 서비스에서 돌거니 private
	private MemberDTO validateLoginMember(MemberDTO loginMember, String userPwd) {
		
		if(loginMember == null) {
			// 정확하게 해주는 것보다 아이디 또는 비밀번호가 잘못됐습니다.라고 모르게 알려주는 게 보안적으로 좋다. 
			throw new UserIdNotFoundException(" 아이디 또는 비밀번호가 잘못됐습니다.");
		}
		if(passwordEncoder.matches(userPwd, loginMember.getUserPwd())) {
			// true
			return loginMember;
		} 
		return null;
		
	}
	// 아이디 중복 체크 , 비밀번호 분리 해야됨. - 알아서 해오기
	

	@Override
	public void singup(MemberDTO member) {
		// 반환 타입 없는데
		// 꼼꼼하게 검증해보자
		
		// 유효값 검증
		// ver 0.2
		
		/*
		if(member == null) {
			throw new NullPointerException("잘못된 접근입니다."); // null 이니 뭐 할게 없으니 돌려 보내기
		}
		
		// ID 값이 20자가 넘으면 안됨.
		// 정규 표현식으로 만드는게 쉽지만 어렵게 가보자
		if(member.getUserId().length() > 20) {
			throw new TooLargeValueException("아이디 값이 너무 길어요"); // 객체로 생성해준다.
		}
		
		if(member.getUserId() == null || member.getUserId().trim().isEmpty() || member.getUserPwd() == null || member.getUserPwd().trim().isEmpty()) {
			//throw new InvalidException;
			throw new InvalidArgumentsException("유효하지 않은 값입니다.");
		}
		*/
		
		// 아이디 중복체크 생략
		
		// DAO로 가서 INSERT하기 전에 비밀번호 암호화하기
		
		//log.info("사용자가 입력한 비밀번호 평문 : {} ", member.getUserPwd());
		// 암호화하기 == 인코더가지고 .encode()호출
		//log.info("암호화한 후 : {}", passwordEncoder.encode(member.getUserPwd()));
		
		// vo에서 하는게 정석인데 일단 dto로 진행
		validator.validatedMember(member);
		String encPwd = passwordEncoder.encode(member.getUserPwd()); // 얘도 빼야됨 나중에 혼자서 해보기
		member.setUserPwd(encPwd);
		mapper.signup(member);
	}

	@Override
	public void update(MemberDTO member, HttpSession session) {
		MemberDTO sessionMember = ((MemberDTO)session.getAttribute("loginMember"));
		// 본격적인 개발자의 영역
		// readonly지만 나쁜 사람이 맘만 먹으면 변경 가능해서 앞단에서 넘어온 아이디 값과 사용자의 id값이 일치한다는 보장이 없음.
		// 앞단에서 넘어온 ID과 현재 로그인된 사용자의 ID값이 일치하는가?
		
		// 실제 DB에 ID값이 존재하는 회원인가?
		
		// USERNAME커럼에 넣을 값이 USERNAME컬럼 크기보다 크지 않은가?
		
		// EMAIL컬럼에 넣을 값이 EMAIL컬럼 크기보다 크지 않은가?
		
		// (EMAIL컬럼에 넣을 값이 실제 EMAIL형식과 일치하는가?)
		validator.validatedUpdateMember(member, sessionMember);
		// 성공 한다면, DB가서 UPDATE (service가 db가는게 아닌 mappe로 간다.) -> MemberMapper에서 sql 추가해준다.
		int result = mapper.update(member);
		
		if(result != 1) {
			throw new AuthenticationException("문제가 발생했습니다. 관리자에게 문의하세요.");
		}
		// 성공 한다면, 성공한 회원의 정보로 SessionScope에 존재하는 loginMember키값의 Member 객체 필드값 
		// 갱신해주기
		sessionMember.setUserName(member.getUserName());
		sessionMember.setEmail(member.getEmail());
		
		
	}// 제약조건도 넣으면 음 
	
	@Override
	public void delete(String userPwd, HttpSession session) {
		
		// 제 1 원칙 : 기능 동작해야함.
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginMember");
		
		if(sessionMember == null) {
			throw new AuthenticationException("로그인부터 해라 ~");
		}
		
		// 분리 했음 또 안써도 됐었음
		if(!passwordEncoder.matches(userPwd, sessionMember.getUserPwd())) {
			throw new AuthenticationException("비밀버호가 일치하지 않습니다.");
		}
		// DELETE FROM MEMBER WHERE USER_ID = 현재 로그인된 사용자의 아이디
		
		int result = mapper.delete(sessionMember.getUserId());
		
		// 책임 분리
		if(result != 1 ) {
			throw new AuthenticationException("관리자에게 문의하세요.");
		}
		
		 session.invalidate();
		
		// 리팩토링
		/*
		 * 쉬운데 어려움
		 * 
		 * 스프링을 이용한 기능 구현 ==> 숙제(주말까지)
		 * 
		 * DynamicWebProject 회원파트 ==> Spring버전으로 다시 만들기
		 * 
		 * 화면 다있음 테이블 다있음 SQL문 다있음 ==> Service단을 신경써보자
		 * 
		 * 
		 * setting 은 있는거 가져다 쓰고 실제 업무에 도움 되기 위해 service !! 잘 해보자
		 * 
		 */
	}

}
