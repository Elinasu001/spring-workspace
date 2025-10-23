package com.kh.spring.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @Date는 기본생성자, 매개변수생성자는 지원을 안한다. lombak @Date 쓰지말기
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReplyDTO {
	
	private Long replyNo;
	private Long refBno;
	private String replyContent;
	private String replyWriter;
	private String createDate;
	private String status;
	
}

// 댓글 쿼리에서 한게시글에 댓글이 여러개 일때 leftouter join을 달아준다.
