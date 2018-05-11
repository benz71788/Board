<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>
<%@ include file="/board/header.jsp" %>
	<table>
		<c:set var="bd" value="${boardDetail}"/>
		<tr>
			<th colspan="2">MVC 게시판 - view페이지</th>
		</tr>
		<tr>
			<td>글쓴이</td>
			<td>${bd.BOARD_NAME}</td>
		</tr>
		<tr>
			<td>글 제목</td>
			<td>${bd.BOARD_SUBJECT}</td>
		</tr>
		<tr>
			<td>글 내용</td>
			<td>${bd.BOARD_CONTENT}</td>
		</tr>
		<tr>
			<td>첨부 파일</td>
			<c:if test="${!empty boardDetail.BOARD_FILE}">
			<td>
				<img src="image/down.png" width="10px">
				<a href="./BoardFileDown.bo?filename=${boardDetail.BOARD_FILE}">
					${bd.BOARD_FILE}</a>
			</td>
			</c:if>
		</tr>
		<tr>
			<td colspan="2" class="center">
				<a href="./BoardReplyView.bo?num=${boardDetail.BOARD_NUM}">답변</a>&nbsp;&nbsp;
				
				<c:if test="${boardDetail.BOARD_NAME == id || id == 'admin'}">
					<a href="./BoardModifyView.bo?num=${boardDetail.BOARD_NUM}">수정</a>&nbsp;&nbsp;
					
					<a href="./BoardDelete.bo?num=${boardDetail.BOARD_NUM}">삭제</a>&nbsp;&nbsp;
				</c:if>
				
				<a href="./BoardList.bo">목록</a>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</body>
</html>