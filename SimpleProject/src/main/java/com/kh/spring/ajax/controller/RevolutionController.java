package com.kh.spring.ajax.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.ajax.dto.AjaxResponse;
import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;


/**
 * Made By 로이필딩
 * 
 * @RestController 은 무엇인가?
 * RestController == @controller + @ResponseBody 
 * 
 * REST(REpresentational State Transfer) - style - 통신 http
 * 
 * HTTP프로토콜을 활용한 아키텍처 스타일 중 하나 ==> 제일 잘나감
 * 
 * 중요 하게 여기는 것 REST 방식으로 아키텍쳐를 만들때,
 * 자원(Resource)중심의 URL구조 + 상태없음(Stateless) 통신 : 즉, 서버 분산 + 앞에 로드밸런서LB개념(실제 컴퓨터 장치)이 붙어야한다. LB먼저 요청 받는다. 그다음 서버 순서대로 요청 하나의 서버로 요청 하는 것보다 부하가 줄어드는데 만약에 사용자가 1번째 사용자 정보를 가지고 있는데 다른 서버로 요청하면 로그인 되어있는걸 모름 그래서 연결해야되는데 병렬 무슨 의미? ->
 * 
 * GET		/boards		 		==> 게시글 목록 조회
 * GET 		/boards/19  		==> 게시글들 중 19번 게시글 조회
 * GET      /boards/photo/19 	==> 게시글들 중 사진게시글들 중 19번 게시글 조회 
 * 
 * POST	    /boards				==> 새 게시글 생성 요청
 * 
 * PUT      /boards/19			==> 19번 게시글 전체 수정 (title, content 싹 갈아 엎겠다) [v] 보편적으로 많이 多
 * FATCH    /boards/19			==> 19번 게시글 부분 수정 (ex, 비밀번호만 바꿀 경우)
 * 
 * DELETE   /boards/19 			==> 19번 게시글 삭제
 * 
 * HTTP 상태 코드 활용
 * 
 * 200 OK						==> 요청이 성공적으로 잘 이루어졌음 (GET, DELETE)
 * 201 Created					==> 요청에 의해 데이터가 잘 만들어짐(POST, PUT, FATCH)
 * 400 Bad Request				==> 잘못된 요청 (By 클라이언트)
 * 401 Unauthorized 			==> 인증 실패 (Login x)
 * 404 Not Found				==> 없음
 * 500 Internal Error			==> 서버 터짐
 * 
 * 유행한 이유? : 통신 장치의 다양화 
 * 옛날에는 웹서비스, 메일 서비스, 파일FTP서비스 이용하고 싶을 경우 컴퓨터로 이용할 수 밖에 없었는데 시대가 흐르면서 핸드폰, 테블릿 등 인터넷 환경에서의 서비스가 무궁무진해짐 -> 그럼 저기 GET/POST/PUT/FATCH 이런식으로 사용하는건 비효율적이다.
 * C# JAVA...클라이언트(핸드폰, 테블릿 등등 다양해졌는데, 서버에서는 JSON 방식으로 똑같이 보내고 싶다. WHY? 어는 언어에서든 파싱해서 사용할 수 있어 범용성이 좋다.)
 * 그래서 REST API를 사용한다.
 * 
 * 
 * 만약에, 핸들러가 각각 다르다면? dto/AjaxResponse에서 참고
 * 
 **/

// 그럼 비동기 컨트롤러 핸들러를 분리해서 관리하면 굳이 각가 @Responsebody 붙일 필요없다.
@RestController // @ResponseBody 생략 가능 무조건 반환이 ResponseBody 로 들어간다.
@RequestMapping(value="revol", produces="application/json; charset=UTF-8") // requestmapping으로 revol 및 produces 동일하니 추출 가능
public class RevolutionController {
// ajax : 비동기 담당 controller handler
	
	@GetMapping("/a")
	public BoardDTO a() {
		BoardDTO a = new BoardDTO();
		a.setBoardTitle("a임");
		return a;
	}
	
	@GetMapping("/b")
	public BoardDTO b() {
		BoardDTO b = new BoardDTO();
		b.setBoardTitle("b임");
		return b;
	}
	
	/**
	 * AjaxController으로 통일하기
	 * **/
	@PostMapping("/c") 
	public AjaxResponse c() {
	    String str = "클라이언트에게 응답 : c당";
	    AjaxResponse ar = new AjaxResponse();
	    ar.setCode("201");
	    ar.setData(str);
	    ar.setMessage("데이터 생성에 성공했습니다.");
	    return ar;  // JSON 자동 변환 (RestController 덕분에)
	}
	

	@GetMapping("/d") // application/json
	public AjaxResponse d() {
		ReplyDTO reply = new ReplyDTO();
		AjaxResponse ar = new AjaxResponse();
		ar.setCode("201");
		ar.setData(reply);
		ar.setMessage("조회에 성공했습니다.");
		return ar;
		
	}
	
	
	
}
