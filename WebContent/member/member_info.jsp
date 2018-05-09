<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	<style>
		table{margin: 0 auto; width: 300px}
		td:nth-child(2n){background: lightgreen}
		td:nth-child(2n+1){background: orange}
		td{text-align:center; height:30px; width:50px}
	</style>
	
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<c:set var="m" value="${memberinfo}"/>
	<table border=1>
		<tr>
			<td colspan=2>회원정보</td>
		</tr>
		<tr>
			<td>아이디</td>
			<td>${m.id}</td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td>${m.passward}</td>
		</tr>
		<tr>
			<td>이름</td>
			<td>${m.name}</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>${m.age}</td>
		</tr>
		<tr>
			<td>성별</td>
			<td>${m.gender}</td>
		</tr>
		<tr>
			<td>이메일</td>
			<td>${m.email}</td>
		</tr>
		<tr>
			<td colspan=2><a href="member_list.net">회원목록 가기</a></td>
		</tr>
		
	</table>
</body>
</html>