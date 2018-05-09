<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
	div{background-color: green;
		text-align: right;
		color: white;
		padding: 10px;
		}
	a{text-decoration: none;}
	a:hover{font-weight: border;}
</style>
<c:if test="${empty id}">
	<script>
		location.href="login.net";
	</script>
</c:if>
<c:if test="${!empty id}">
	<c:if test="${id=='admin'}">
		<span>관리자 ${id}님 환영합니다.</span>
			<a href="member_list.net">관리자모드 접속(회원 목록 보기)</a>
			<a href="BoardList.bo">(게시판)</a>
	</c:if>
</c:if>
<c:if test="${id != 'admin'}">
	<span>일반회원 ${id}님 환영합니다.</span>
</c:if>	
	<span>(<a href="member_update.net">정보수정</a>)
	(<a href="logout.net">로그아웃</a>)</span>
<hr>