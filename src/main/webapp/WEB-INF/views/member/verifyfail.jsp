<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<link href="<c:url value="/css/default.css"/>" rel="stylesheet"
	type="text/css">
</head>
<body>
	<!-- 해더 시작 -->
	<%@ include file="/WEB-INF/views/frame/header.jsp"%>
	<!-- 해더 끝 -->

	<!-- 네비게이션 시작 -->
	<%@ include file="/WEB-INF/views/frame/nav.jsp"%>
	<!-- 네비게이션 끝 -->

	<!-- 컨텐츠 시작 -->
	<div id="contents">

		<h1>
			정상적인 요청이 아닙니다.<br> 관리자에게 문의 하세요.
		</h1>

	</div>
	<!-- 컨텐츠 끝 -->


	<!-- 푸터 시작 -->
	<%@ include file="/WEB-INF/views/frame/footer.jsp"%>
	<!-- 푸터 끝 -->

</body>
</html>