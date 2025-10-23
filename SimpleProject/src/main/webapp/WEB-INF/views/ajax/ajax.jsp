<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax_page</title>
</head>
<body>
	<h1>AJAX</h1>
	
	<jsp:include page="../include/header.jsp"/>
	<div class="innerOuter">
	<pre>
	
	웹페이지 전체를 새로고침하지 않고
	
	서버와 비동기 통신을 하여 화면을 갱신할 수 있는 기술 패턴
	
	* 핵심 => 비동기 통신 => 부분 갱신 => 사용자 경험 향상 => 트래픽이 줄어든다. ( 속도가 빠르다 )
	
	----------------------------------------------------------------------------
	
	전송 데이터 형식 => 과거에는 XML 사용 => 현재 JSON (조심 "")
	AJAX 구현 API == XMLHttpRequest => Modern Fetch API => js
								   => ajax() => jQuery 多  [spring]
								   => axios() => React	  [react]
	----------------------------------------------------------------------------
	
	전체적인 흐름
	
	1. 클라이언트가 요청 보냄(JS로 보냄)
	2. 서버는 요청 처리 후 데이터 응답 (문자열 => JSO형태로)
	3. 클라이언트는 응답받은 데이터로 자바스크립트 DOM요소객체를 갱신
	
	
	- jQuery로 ajax 요청 시 주요 속성
	- url : 요청할 URL(필수)
	- data : 요청 시 전달값({키 : 벨류})
	- type : 요청 전송방식(GET/POST/PUT/DELETE)
	- 		 GET방식 : 조회요청(SELECT)
			 POST방식 : 데이터 생성 요청(INSERT)
			 PUT방식 : 데이터 갱신요청(UPDATE)
			 DELETE방식 : 데이터 삭제 요청(DELETE)
	- success : AJAX 통시 성공 시 실행할 함수를 정의
			 					   
	</pre>
	
	<h3>1. 버튼 클릭해서 GET방식으로 요청 보내서 데이터 받아서 화면에 출력!</h3>
	<div class="form-group">
		<div class="form-control">
			입력 : <input type="text" id="ajax-input">
		</div>
		<div class="form-control">
			<button id="ajax-btn" onclick="test1();" class="btn btn-sm btn-success">AJAX로 요청보내기</button>
		</div>
	</div>
	
	
	응답 : <label id="result">현재 응답 없음</label>
	
	
	<!-- 
		계획 : 
		인풋요소에 아무거나 쓰고 요청보내기 버튼을 누르면 ajax요청을 보내서 
		요청을 받아서 처리해주는 RequestHandler가 값을 받아서 응답을 해주고
		받은 응답데이터를 라벨요소 Content영역에 출력할 것
	 -->
	 
	 
	 <script type="text/javascript">
	 	function test1(){
			
	 		$.ajax({
	 			url: "test",
	 			type: "get",
	 			data: {
	 				"input" : $("#ajax-input").val()
	 			},
	 			success: function(response){
	 				console.log(response);
	 				//$("#result").text(response);
	 				document.getElementById('result').innerHTML = response;
	 			}
	 		});
	 		
	 		
	 	}
	 </script>
	
	
	</div>
	
	<jsp:include page="../include/footer.jsp"/>
	
</body>
</html>