<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공무원분들이 인정한 진짜 부산 맛집</title>
</head>
<style>
	.food{width:45%; height:auto; display:inline-block; padding:10px; text-align:center;}
	.food:hover{
		pointer:cursor;
		box-shadow:0px 3px 3px 3px rgba(0, 0, 0, 0.2);
		background-color:rgba(225, 225. 225. 0.9);
	}
</style>
<body>

	<jsp:include page="../include/header.jsp"/>
	<div class="innerOuter">
		
		<div id="result" style="">
			
		
		</div>
		
		<hr>
		
		<div style="width:300px; hegiht:80px; margin:auto;">
			<button onclick="getBusans();" class="btn btn-sm" style="background:#0056b3;  color:white; width:100%; height:100%; padding:15px; border:none;">더보기 ▽ </button>
		</div>
		
	</div>
	
	
	<script>
	
	pageNo = 1;
	
	$(function(){
		getBusans();	
	});
	
	function getBusans(){
		$.ajax({
			url: `/spring/api/busan`,
			data: {
				pageNo : pageNo
			},
			success: response => {
				pageNo++;
				//console.log(response);
				
				const foods = response.getFoodKr.item;
				console.log(foods);
				const result = foods.map(e => `
											<div onclick="detail(\${e.UC_SEQ})" class="food">
												<img src="\${e.MAIN_IMG_THUMB}" width="100%;" height="auto"/>
												<br>
												<h5>\${e.MAIN_TITLE}</h5>
												<p>\${e.GUGUN_NM}</p>
											</div>
										`).join('');
				document.getElementById('result').innerHTML += result;
			}
		})
	}
	
	function detail(num){
		location.href = `/spring/busan/detail/\${num}`;
	}
	</script>
	<jsp:include page="../include/footer.jsp"/>
	
</body>
</html>