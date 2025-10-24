package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

// Controller를 bean으로 등록하기  메소드를 사용하려면 객체 생성을 해야되는데 ...
@Slf4j
@Controller
//@RequestMapping("/member"); // 보통 여기에 둔다.
public class MemberController {
	
	/*
	@RequestMapping("login")
	public void login(Member member) {
		// 1. 값 뽑기
		// 2. 데이터 가공
		System.out.println(member);
	}
	*/
	
	/*
	// 방법 1 . 잘 사용하지는 않음
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		System.out.printf("id : %s, pw : %s", userId, userPwd);
		
		return "main";
	}
	*/
	
	
	/*
	// 방법 2. @ 써야 값이 들어감 => 여러개 들어갈 수도 있으니 (value="userId"), defaultValue 앞단에서 안넘어 올 경우
	@RequestMapping("login")
	public String login(@RequestParam(value="userId", defaultValue="fffff") String id, @RequestParam(value="userPwd") String pwd) {
		System.out.printf("id : %s, pwd : %s", id, pwd);
		
		return "main";
	}
	*/
	
	
	// 방법 3.String userId, String userPwd 적었을 때 null로 됐는데 이 방법은 앞단에서 넘긴 값이 잘 들어옴.(즉, 키값 위치가 동일하다면 spring 에서 알아서 @RequestParam(value="userId") 붙여준다. 
//	@RequestMapping("login")
//	public String login(/*@RequestParam(value="userId")*/String userId, /*@RequestParam(value="userPwd")*/String userPwd) {
//		System.out.println("id : " + userId + ", pwd : " + userPwd);
//		return "main";
//	}
//	
	
	/*
	 * HandlerAdapter의 판단 방법 : 
	 * 1. 매개변수 자리에 기본타입(int, boolean, String, Date ...) 이 있거나
	 * 	  @RequestParam애노테이션이 존재하는 경우 == RequestParam으로 인식
	 * 
	 * 2. 매개변수 자리에 사용자 정의 클래스(MemberDTO, Board, Reply ...)이 있거나
	 *    @ModelAttribute애노테이션이 존재하는 경우 == 커맨드 객체 방식으로 인식
	 * 
	 * 커멘드 객체 방식
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 이용해서 생성한 후 내부적으로 setter메서드를 찾아서
	 * 요청 시 전달값을 해당 필드에 대입해줌
	 * 
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키 값과 객체의 필드명이 동일할 것
	 * 3. setter메서드가 반드시 존재할 것
	 * 
	 */
	
	//  : 의존성 주입 용이
	
	// 방법 1. Dependency Injection 중 한가지 방법으로 @Autowired가 있다.
	//@Autowired  == Field Dependency Injection
	//private MemberService memberService; // = new MemberService();
	
	// 방법 2. setter메서드가 반드시 존재할 것
	//private MemberService memberService; // = new MemberService();
	/*
	@Autowired == Setter Dependency Injection
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	} 방법1 . 방법 2. 둘다 안씀
	*/
	
	// 방법 3. Constructor Dependency Injection
	private MemberService memberService; // = new MemberService();
	
	// MemberService 객체를 MemberController에 주입하는 구문
	@Autowired /*★★★★★★★★★★권장 방법 ★★★★★★★★★★*/
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
//	@RequestMapping("login") // bean - HandlerMapping
// 						/*@ModelAttribute*/, /*HttpServletRequest request*/
//	public String login(MemberDTO member, HttpSession session, Model model) {
//		
//		//System.out.println("로그인 시 입력한 정보 : " + member);
//		
//		log.info("Member객체 필드값 확인~ {}", member);
//		
//		MemberDTO loginMember = memberService.login(member);
//		/*
//		if(loginMember != null) {
//			log.info("로그인 성공");
//		} else {
//			log.info("실패");
//		}
//		*/
//		
//		if(loginMember != null) {
//			
//			// 로그인 성공
//			// sessionScope 로그인된 사용자의 정보를 담아줌
//			// HttpServlet
//			//HttpSession session = request.getSession();
//			
//			session.setAttribute("loginMember", loginMember);
//			// 포워딩 방식 보다 -> senRedirect
//			// localhost/spring 
//			return "redirect:/";
//					
//		} else {
//			
//			// 로그인 실패
//			//error_page 포워딩
//			// requestScope에 msg 라는 키 값으로 로그인 실패입니다 ! 담아서 포워딩 
//			// model를 사용하여 반환한다.(requestScope 값을 반환하기 위한 대체제)
//			// spring 에서는 model 타입을 이용해서 requestScope에 값을 담음
//			model.addAttribute("msg", "로그인 실패"); 
//			
//			// Forwarding
//			// prefix : /WEB-INF/views/
//			// subfix : .jsp
//			// /WEB-INF/view/include/error_page.jsp
//			return "include/error_page";
//		}
//		
//		//return "main";
//	}
	
	// 두 번째 방법 : 반환 타입 ModelAndVIEW타입으로 반환
	@PostMapping("/login")
	public ModelAndView login(MemberDTO member, HttpSession session, ModelAndView mv) { // 객체의 필드명과 앞단에서 넘기는 키값이 동일해야 이 것을 기본 생성자로 객체 생성하고 키값과 동일한 필드명을 찾아 setter 메소드를 호출하여 대입해준다.
		
		MemberDTO loginMember = memberService.login(member);
		// ModelAndView mv = new ModelAndView();
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("msg", "로그인실패!").setViewName("include/error_page");
		}
		return mv;
	}
	
	//CRUD
	//INSERT 	->POST	-----> /member --> @PostMapping
	//SELECT 	-> GET	-----> 		   --> @GetMapping
	
	
	//UPDATE
	//DELETE
	
	// session에서 값 지우는 거니깐 session 으로 진행
	// 자바에서는 모든 건 객체로 이루어져있고 중요한건 type이다.
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginMember");
		return "redirect:/";
	}
	
	//회원가입 페이지
	@GetMapping("join")
	public String joinForm() {
		// 포워딩할 JSP파일의 논리적인 경로 필요
		// /WEB-INF/views/member/signup.jsp
		return "member/signup";
	}
	
	//회원가입 핸들러~
	@PostMapping("signup")
	public String signup(MemberDTO member) {	//  userName=?¹??¸¸???,   post 은 encoding 해줘야 하는데 getparamter 하기 전에 해야된다. -> FILTER -> SPRING의 filter 패키지 사용
		
		// 아이디, 비밀번호, 이름, 이메일
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		log.info("{}", member); // 개발 - info 운영 - debug
		
		memberService.singup(member); // 반환 타입이 없으니 Member~impl 에서 검증 해보기
		
		return "main";
	}
	
	
	@GetMapping("mypage")
	public String myPage(ModelAndView mv) {
		return "member/my_page";
	}
	
	@PostMapping("edit")
	public String edit(MemberDTO member, HttpSession session) {
		/*
		 * 1_1) 404발생 : mapping값 확인하기
		 * org.springframework.web.servlet.PageNotFound
		 * 
		 * 1_2) 405발생 : mapping값 잘씀 GET/POST를 잘못적었을 때
		 * 
		 * 
		 * 1_3) 필드에 값이 잘 들어왔나?? - KEY값 확인
		 * log.info("값 찍어보기 : {}", member);
		 */
		log.info("값 찍어보기 : {}", member);
		
		/*
		 * 2. SQL문
		 * UPDATE => MEMBER (특정 한명의 회원을 UPDATE) => PK? (WHERE 조건에 어떤 조건을 달아야 하지?) 
		 * ID(PK) PWD NAME(UPDATE가능) EMAIL(UPDATE가능) ENROLLDATE => 그럼 PK WHERE 조건으로 사용해야겠다.
		 * 
		 * 2_1) 매개변수 MemberDTO타입의 memberId필드값 조건
		 * UPDATE MEMBER SET USER_NAME = 입력한 값, EMAIL = 입력한 값 WHERE USER_ID = 넘어온 아이디
		 * 
		 */
		
		/*
		 * Best Practice
		 * 
		 * 실무 권장
		 * 
		 * 컨트롤러에서 세션관리를 담당
		 * 서비스에는 순수 비즈니스 로직만 구현
		 * 서비스에서 HttpSession이 필요하다면 인자로 전달
		 * //권장 : CONTROLLER에서 SESSION을 받아와서 SERVICE로 넘기는걸 선호 : ... Service는 비지니스로직은 아니라서 Controller에서 하는걸 권장
		 * 
		 */
		
		memberService.update(member, session);
		
		return "redirect:mypage";
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam(value="userPwd") String userPwd, HttpSession session) {
		
		memberService.delete(userPwd, session);
		
		return "redirect:/";
	}
	
}
