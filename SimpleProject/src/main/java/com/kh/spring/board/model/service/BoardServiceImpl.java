package com.kh.spring.board.model.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.dto.ReplyDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.BoardSaveFailedException;
import com.kh.spring.exception.InvalidArgumentsException;
import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.util.PageInfo;
import com.kh.spring.util.Pagination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	private final Pagination pagination;
	
	@Override
	public Map<String, Object> findAll(Long page) { // 두 개의 값을 담아야 하는데 type이 다름.
											   // 1.  List<BoardDTO> : 이것을 담을 수 있는 dto 만들기(나중에 이 코드가 뭐지 하고 파악해야됨)
											   // 2. Map ( LIST= 순서 동일, SET = 중복제거, MAP = 값을 다룰 경우 KEY [V]) - (이미 기존에 존재하는 걸 사용하는 편이 효율적)
		
		Map<String, Object> map = new HashMap();
		List<BoardDTO> boards = new ArrayList(); //scope 을 벗어 났으니 밖에서 선언
		
		// 유 효 성 검 증
		if(page < 1) {
			throw new InvalidArgumentsException("잘못된 접근입니다.");
		}
		
		// 조회수
		int count = boardMapper.selectTotalCount();
		
		// 확인
		log.info("총 게시글 개수: {}", count); // 총 게시글 개수: 16
		
		// 보여줄 게시글 수"와 "페이지 묶음 수"
		PageInfo pi = pagination.getPageInfo(count, page.intValue(), 5, 5); // page ...실수로 int가 아닌 long으로 받아서 inValue()사용
		
		if(count > 0) {
			//게시글이 있으면				
			RowBounds rb = new RowBounds((page.intValue() - 1) * 5, 5); // offset (시작행) :  (page.intValue() - 1) * 5. 5 limit (가져올 개수)
			boards = boardMapper.findAll(rb);
		}
		
		map.put("pi", pi);
		map.put("boards", boards);
		
		return map;
	}
	
	// 1. 권한검증
	private void validateUser(BoardDTO board, HttpSession session) {
		String boardWriter = board.getBoardWriter();
		MemberDTO loginMember = ((MemberDTO)session.getAttribute("loginMember"));
		//String userId = loginMember.getUserId(); //NullpointerExcetion check
		if(loginMember == null || !boardWriter.equals(loginMember.getUserId())) { // or든 and든 Short-Circuit Evaluation : false && true 앞에만 연산 후 끝!
			throw new AuthenticationException("권한 없는 접근입니다.");
		}
		
		
		/*이것도 인서트와는 연관 없으니 메소드로 빼주기*/
		// 만약에 인풋에 악의 적인 사람이 스킈립트가 들어간걸 넣는다면? 망가지는 현상이 일어나는데 이것을 유효값 검증으로 체크해서 빼줘야 한다. 즉, 태그가 인식되지 않게 넣어줘야 한다.
		String boardTitle = board.getBoardTitle().replaceAll("<", "&lt;");
		String boardContent = board.getBoardContent().replaceAll("<", "&lt");
		// 욕설 막기
		if(board.getBoardTitle().contains("이승철바보")) {
			boardTitle = board.getBoardTitle().replaceAll("이승철바보", "글쓴사람바보");
		}
		board.setBoardTitle(boardTitle);
		board.setBoardContent(boardContent);
	}
	
	
	
	@Override
	public int save(BoardDTO board, MultipartFile upfile, HttpSession session) {
		
		// 1. 권한검증
		validateUser(board, session);
		
		// 2. 값에 대한 유효성 검증
		if(board.getBoardTitle().trim().isEmpty() || board.getBoardContent().trim().isEmpty()) { // insert x
			throw new InvalidArgumentsException("유효하지 않은 요청입니다.");
		}
		// 3. 파일이 있을 경우 업로드 (원본파일) - 공통 파일 관련 (클래스로빼기) ==> 빈으로 분리하기
		if(!upfile.getOriginalFilename().isEmpty()) {
			
			// 파일명이 있다면.
			// 이름바꾸기
			// KH_시간+숫자+원본확장자
			//--------------------------------- 메서드 책임 [메서드 1: 파일 이름 생성 (Math, 날짜, 확장자 등)]-------------------------------------------
			StringBuilder sb = new StringBuilder();
			sb.append("KH_");
			String currentTime = new SimpleDateFormat("yyyMMddHHmmss").format(new Date());
			sb.append(currentTime);
			sb.append("_");
			
			int num = (int)(Math.random() * 900) + 100;
			sb.append(num);
			String ext = upfile.getOriginalFilename().substring(upfile.getOriginalFilename().lastIndexOf("."));
		
			sb.append(ext);
			
			
			//--------------------------------- 메서드 책임 [분리 메서드 2: 실제 파일 저장 (ServletContext 경로 + transferTo)]-------------------------------------------
			ServletContext application = session.getServletContext();
			String savePath = application.getRealPath("/resources/files/");
			
			try {
				upfile.transferTo(new File(savePath + sb.toString())); // 스프링이 제공하는 파일 저장 메서드
			} catch(Exception e) {
				e.printStackTrace();
			}
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName("/spring/resources/files/" + sb.toString());
		}
		// ----------------여기까지 !!! 책임분리하기 까지가 커트라인 ==> 총대가 ... ---------------------------
		
		int result = boardMapper.save(board);
		
		if(result != 1) {
			
			// 이럴 때 발생시킬 예외하나 깔끔하게 만들어보기 ResponseStatusException
			throw new BoardSaveFailedException("이게 왜 그럴까요?");
		}
		
		return 0;
	}

	@Override
	public BoardDTO findByBoardNo(Long boardNo) {
		
		// 악의적인 사용자가 게시글 번호를 바꿀 수 있으니 예외처리 해줘야됨. / 어차피 있을 수 없는 번호면 db갈 필요가 없음
		if(boardNo < 1) {
			throw new InvalidArgumentsException("유효하지 않은 요청입니다.");
		}
		
		// 업데이트 먼저 하고 최신 갱신된 데이터를 조회하자
		int result = boardMapper.increaseCount(boardNo);
		
		if(result != 1) {
			// 요청 잘 못 올 경우 BadRequestException - 숙제 이걸로
			throw new InvalidArgumentsException("잘못된 요청입니다.");
		}
		
		// 조회해오기 및 답글
		BoardDTO board = boardMapper.findBoardAndReply(boardNo);
		
		// 지워졌는데 조회되면 안되니, 조회가 안됐다면 null과 같다면 // 예외처리 이것도 밖으로 빼주기
		if(board == null) {
			throw new InvalidArgumentsException("삭제된 게시글입니다.");
		}
		
		return board;
	}

	@Override
	public int deleteByBoardNo(Long boardNo) {
		return 0;
	}

	@Override
	public int update(BoardDTO board) {
		return 0;
	}
	
	
	@Override
	public int insertReply(ReplyDTO reply, HttpSession session) {
		MemberDTO loginMember = ((MemberDTO)session.getAttribute("loginMember"));
		
		if(loginMember == null) {
			throw new InvalidArgumentsException("이 장면 꿈에서 봄");
		}
		
		// 유효성 검증 (안하면 없은 걸로 인서트 하게 될 수 있음 => sqlExcepiton 부모키 못찾음)
		Long boardNo = reply.getRefBno();
		BoardDTO board = boardMapper.findByBoardNo(boardNo);
		
		if(board == null) {
			throw new InvalidArgumentsException("올바르지 않은 게시글 번호입니다.");
		}
		
		reply.setReplyWriter(loginMember.getUserId());
		
		return boardMapper.insertReply(reply);
		
	}

}
