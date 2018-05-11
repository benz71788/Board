<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<script src="/JspProject/js/jquery-3.3.1.js"></script>
<link href="css/form.css" rel="stylesheet">

<body>
<jsp:include page="header.jsp"/>
	<form action="./BoardModifyAction.bo" method="post" name="boardform">
	<input type="hidden" name="BOARD_NUM" value="${boardData.BOARD_NUM}">
		<table>
			<tr class="center">
				<th colspan="2">MVC 게시판 - 수정</th>
			</tr>
			<tr>
				<td><div>글쓴이</div></td>
				<td>
					<input type="text" name="BOARD_NAME" id="board_name" value="${id}">
				</td>
			</tr>
			<tr>
			<td><div>제목</div></td>
			<td><input name="BOARD_SUBJECT" id="board_subject"
					type="text" size="50" maxlength="100" value="${boardData.BOARD_SUBJECT}">
		</tr>
		<tr>
			<td><div>내용</div></td>
			<td><textarea name="BOARD_CONTENT" id="board_content" 
						cols="67" rows="15">${boardData.BOARD_CONTENT}</textarea>
			</td>
		</tr>
		<tr>
			<td><div>비밀번호</div></td>
			<td>
				<input name="BOARD_PASS" id="board_pass"
					type="password" size="10" maxlength="30">
			</td>
		</tr>
		<tr>
			<td><div>첨부 파일</div></td>
			<c:if test="${!empty boardDetail.BOARD_FILE}">
			<td>
				&nbsp;&nbsp;${boardData.BOARD_FILE}
			</td>
			</c:if>
		</tr>
		<tr class="center">
			<td colspan="2" class="h30 lime">
				<input type="submit" value="등록">
				<input type="reset"	value="취소"	onClick="history.go(-1)">
			</td>
		</tr>
		</table>
	</form>
</body>
</html>