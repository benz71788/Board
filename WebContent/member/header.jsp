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
	<c:choose>
		<c:when test="${empty id}">
			location.href = 'login.net';
		</c:when>
		<c:when test="${id =='admin'}">
			<span>관리자 ${id}님 환영합니다.</span>
		</c:when>
		<c:otherwise>
			<span>일반 회원 ${id}님 환영합니다.</span>
		</c:otherwise>
	</c:choose>
	
	
	<span>(<a href="member_update.net">정보수정</a>)
	(<a href="logout.net">로그아웃</a>)</span>
<hr>