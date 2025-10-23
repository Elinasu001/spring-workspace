package com.kh.spring.ajax.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;
import com.kh.spring.board.model.service.BoardService;

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
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// @ResponseBody jsp 가는거 아님 데이터 속성 가는 거임
	@ResponseBody
	@PostMapping(value="replies", produces="text/html; charset=UTF-8")
	public String insertReply(ReplyDTO reply, HttpSession session) {
		
		//log.info("{}", reply);
		int result = boardService.insertReply(reply, session);
		
		// String.valueOf(result); 명확하게 성공실패 여부 전달하는게 좋음.0 과 1,,, 나중에 알려드림
		// 일단 문자열로 가자 // 이런 성공 실패는 영어로 적어주는게 좋음  // 실제 게시판의 번호를 작성해야 console에 나옴
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@GetMapping(value="board/{num}", produces="application/json; charset=UTF-8") // ★★★★★★★★★★
	public BoardDTO detail(@PathVariable(value="num") Long boardNo) {
		log.info("게시글 번호 : {}", boardNo);
		BoardDTO board = boardService.findByBoardNo(boardNo);
		log.info("혹시볼라 : {}", board);
		/*  편리 편리 편리
		 *  JSON 형태로 넘겨줌
		 *
		 * {
		 * 	"boardNo" : 19,
		 * "boardTitle: "첨부파일있어",
		 * ...
		 * replies:[
		 * 
		 * 		{
		 * 			"replyNo" : 5,
		 * 			"replyContent" : "ㅎㅎ"
		 * 			...
		 * 
		 * 		},
		 * 		{
		 * 			...
		 * 		}
		 * 
		 * ]
		 * 
		 * }
		 * 
		 */
		//return new Gson().toJson(board);
		return board; // Gson == spring은 알아서 똑같이 json 형태로 넘겨준다 (물론 라이브러리 추가해야 가능)★★★★★★★★★★ 응답 데이터 : 문자열(json) , 자바스크립트로 열어본건 파싱 한 것
	}
	
	// @ResponseBody 공통으로 들어갈 경우 따로 뺴는게 좋음
	
}
