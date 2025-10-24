<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안심식당리스트</title>
</head>
<body>

	<jsp:include page="../include/header.jsp"/>
	
	<div class="innerOuter">
		<button onclick="beef();" class="btn btn-sm btn-info">안심식당 정보 보기</button>
	</div>
	
	<table class="table">
		<thead>
			<tr>
				<th>식당명</th>
				<th>주소</th>
				<th>전화번호</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	
	<script>
		function beef(){
			$.ajax({
				url: 'api/beef',
				success: result =>{
					//console.log(result);
					//console.log(result.Grid_20200713000000000605_1);
					const resultEl = result.Grid_20200713000000000605_1.row;
					const el = resultEl.map(e => `<tr>
													<td>\${e.RELAX_RSTRNT_NM}</td>
													<td>\${e.RELAX_ADD1}</td>
													<td>\${e.RELAX_RSTRNT_TEL}</td>
												
												</tr>
											`).join('');
					document.querySelector('tbody').innerHTML = el;
				}
			})
		
		}
	</script>
	
	<jsp:include page="../include/footer.jsp"/>
</body>
</html>