package com.kh.spring.member.model.dto;
import com.kh.spring.member.model.dto.MemberDTO;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 매개변수 생성자
@ToString
public class MemberDTO {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private Date enrollDate;
}
