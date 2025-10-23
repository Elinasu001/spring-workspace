package com.kh.spring.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.BoardDTO;

@Mapper
public interface BoardMapper {
	
	int selectTotalCount();
	
	// 게시판 조회
	List<BoardDTO> findAll(RowBounds rowBounds);
	
	// 게시판 등록
	int save(BoardDTO board);
	
	// 조회수 증가
	int increaseCount(Long boardNo);
	
	// 조회수 조회
	BoardDTO findByBoardNo(Long boardNo);
	
	// 댓글 달기
	BoardDTO findBoardAndReply(Long boardNo);
}
