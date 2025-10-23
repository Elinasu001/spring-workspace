package com.kh.spring.ajax.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse {
	// 이 3 코드는 무조건 들어가고/ 추가적으로: 응답시간, 성공 실패도 들어간다.
	private String code;
	private String message;
	private Object data;
	
}
