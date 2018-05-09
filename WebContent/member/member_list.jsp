<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>

<body>
<jsp:include page="header.jsp"></jsp:include>
<table border=1>
	<caption>회원 목록</caption>
	<thead>
		<tr>
			<td>아이디</td><td>이름</td><td>삭제</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="m" items="${totallist}">
			<tr>
				<td><a href="member_info.net?id=${m.id}">${m.id}</a></td>
				<td>${m.name}</td>
				<td><a href="member_delete.net?id=${m.id}">삭제</a></td>
			</tr>
			
		</c:forEach>
	</tbody>
	
<%--
<%@ include file="header.jsp" %>
<%
	ArrayList<Member> list = (ArrayList<Member>) request.getAttribute("totallist");
%>
<table border=1>
	<tr>
		<td colspan=3>회원목록</td>
	</tr>
	<%
		for(Member m:list){
	%>
		<tr align=center>
			<td>
				<a href="member_info.net?id=<%=m.getId() %>"><%=m.getId() %></a>
			</td>
			<td>
				<a href="memver_delete.net?id=<%=m.getId() %>">삭제</a>
			</td>
		</tr>
	<%
		}
	%>
</table>
 --%>
</table>
</body>
</html>
