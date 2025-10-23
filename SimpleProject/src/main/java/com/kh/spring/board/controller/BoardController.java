package com.kh.spring.board.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	/*
	 * url 에다가 무슨 자원을 요청 하는가 ? mapping 값이...? / boards로 통일 시키기 /
	 * 
	 * mapping
	 * 
	 * SELECT == GET / INSERT == POST
	 * 
	 * 어떤 자원인지 ???
	 * 전체조회 		   == boards								== GET
	 * 상세조회(단일조회)   == boards/boardNo  (== PK(1/2/3/4/))
	 * 작성 			   == boards								== POST
	 * 
	 * mapping값이 동일 해도 방식 get/post 방식이 다르면 동작 가능 ==> 편함 ==> why?뽑을 수 있음 ==> controller단으로
	 * == > @RequestMapping("boards") 그럼 각 메소드에 있던 @GetMapping("boards")를 => @GetMapping로 날릴 수 있다.
	 * 
	 * 
	 * UPDATE는 ? @DeleteMapping  
	 * DELETE는 ? @PutMapping    // 또는 @FetchMapping
	 * 
	 * 
	 */
	
	/**앞으로 배워야 할 것**/
	@DeleteMapping  // delete  
	public void a() {};
	
	@PutMapping    // 또는 @FetchMapping // update
	public void b() {};
	/**--------------**/
	
	
	// 게시글 리스트
	@GetMapping
	public String findAll(@RequestParam(name="page", defaultValue="1") Long page, Model model) {
		
		log.info("앞에서 넘어온 페이지 값: {}", page); // 앞에서 넘어온 페이지 값: 1
		// 페이징 처리
		// 게시글 몇개야
		// 한 페이지에 몇개 보여주지?
		// 버튼은 몇개 보여주지?
		// 위에 하려면 여기서 하는게 아니라 서비스(BoardServiceImpl)에서 해야된다.
		Map<String, Object> map = boardService.findAll(page);
		
		model.addAttribute("map", map);
		
		return "board/list";
	}
	
	// 게시글 등록하기 폼
	@GetMapping("/form")
	public String toForm() {
		return "board/form";
	}
	
	// 게시글 글쓰기
	@PostMapping
	public String save(BoardDTO board, MultipartFile upfile, HttpSession session) { // 파일 용량
		
		log.info("보드 잘나오아? {}, 파일 정보 {} :", board, upfile);
		
		// 첨부파일의 존재 유무
		// MultipartFile 객체의 fileName 필드값으로 확인해야함
		
		// INSERT INTO BOARD
		// VALUES (#{boardTitle}, #{boardContent}, #{boardWriter}, #{originName}, #{changeName}
		
		
		// 1. 권한있는 요청인가 (로그인 -> session)
		// 2. 파일 존재유무 체크 => 이름 바꾸기 작업(파일 확장자 체크(spring boot에서 알려드림) => 파일 업로드
		// 3. 값들이 유효성 있는 값인가
		// 4. 바뀐이름을 changeName 필드에 담아서 Mapper로 보내기
		boardService.save(board, upfile, session);
		
		
		return "redirect:boards";
	}
	
	//@GetMapping("boards/1")///1/2/3/... 할수 없음
	// 게시글 상세보기											인터파크 컨텐츠/장르/콘서트(pk == id) 만약 이벤트면 ?  events/ing/pk, events/end/pk
	@GetMapping("/{id}") // 예를 들어서 카테고리가 많을 경우 ("boards/{category}/{id}") / 매개변수 : + @PathVariable(name="category") sTRING CATEGORY
	public String toDetail(@PathVariable(name="id") Long boardNo, Model model) { //. url 경로에서 가변적임 값을 뽑아내려면 ?@PathVariable(name="id")
		
		//log.info("게시글번호 : {}, 카테고리 : {}", boardNo, category);
		log.info("게시글번호: {}", boardNo);
		
		// 조회수 증가
		// 조회수 증가에 성공했다면 SELECT로 조회
		// 만약에 없는 게시글 번호라면 예외발생
		BoardDTO board = boardService.findByBoardNo(boardNo);
		model.addAttribute("board", board);
		
		return "board/detail";
	}
	
}
