package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {
	
	// 게시글 목록 조회 + 페이징 처리
	// 총 게시글 수 조회
	//int selectListCount(); // 여기서 하는게 아니고 전부다 넘겨서 serviceimpl에서 처리
	
	// 목록조회
	/* 
	 * 네이밍컨벤션 참고
	 * selectBoardList();
	 * findAll();
	 * selectAll();
	 * 
	 */
	Map<String, Object> findAll(Long boardNo);
	
	// 게시글 작성
	/*
	 * insertBoard();
	 * save();
	 */
	int save(BoardDTO board, MultipartFile upfile, HttpSession session);
	
	// 게시글 상세보기(+조회수 증가 +댓글달기)
	/*
	 * selectBoard();
	 * findByxxx();
	 */
	BoardDTO findByBoardNo(Long boardNo);
	
	// 게시글 삭제하기
	/*
	 * deleteBoard();
	 * deleteByxxx(); - pk 넣어도됨
	 */
	int deleteByBoardNo(Long boardNo);
	
	// 게시글 수정하기
	/*
	 * updateBoard();
	 * updateByxxx();
	 */
	int update(BoardDTO board);
	
	
	//--------------------------
	// 댓글 서비스
	
	
}
