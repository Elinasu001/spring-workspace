package com.kh.spring.ajax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder  // 객체를 생성하면서 필드에 값을 담을 떄, 매개변수 순서, 세터는 세터하나씩 값을 set해서 쓸려면 힘드니 lombok 에서 제공
@NoArgsConstructor
@AllArgsConstructor
public class AjaxResponse {
	// api response 
	// 이 3 코드는 무조건 들어가고/ 추가적으로: 응답시간, 성공 실패도 들어간다.
	private String code;
	private String message;
	private Object data;
	
}
