<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link href="<c:url value="/css/default.css"/>" rel="stylesheet" type="text/css">

</head>
<body>
<!-- 해더 시작 -->
<%@ include file="/WEB-INF/views/frame/header.jsp" %>
<!-- 해더 끝 -->

<!-- 네비게이션 시작 -->
<%@ include file="/WEB-INF/views/frame/nav.jsp" %>
<!-- 네비게이션 끝 -->

<!-- 컨텐츠 시작 -->
<div id="contents">
	<form>
	가입하신 아이디를 입력하세요 
	<input type="text" id="uId" name="uId"> 
	<input type="submit" id="forgotPW">
	</form>
</div>
<!-- 컨텐츠 끝 -->


<!-- 푸터 시작 -->
<%@ include file="/WEB-INF/views/frame/footer.jsp" %>
<!-- 푸터 끝 -->

<script>
	$(document).ready(function(){
		$('#forgotPW').click(function(){
			
			$.ajax({
				url : 'changeNewPW',
				type : 'POST',
				data : {uId : $("#uId").val()},
				success : function(data){
					if(data=="success"){
						alert("가입하신 이메일로 비밀번호가 전송되었습니다.");
					}
				}
			});
			
			return false;
		});
	});


</script>
</body>
</html>