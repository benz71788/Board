<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>MVC 게시판</title>
	<link href="css/list.css" rel="stylesheet">
</head>
<body>
<%@ include file="/board/header.jsp" %>
<!-- 게시판 리스트 -->
	<table>
	<!-- 레코드가 없으면 -->
		<c:if test="${listcount == 0}">
			<tr>
				<td colspan="4">MVC 게시판</td>
				<td style="text-align:right">
					<font size=2>등록된 글이 없습니다.	</font></td>
			</tr>
		</c:if>
			<tr>
				<td colspan="5" style="text-align:right">
					<a href="./BoardWrite.bo">[글쓰기]</a>
				</td>
			</tr>
	</table>
</body>
</html>