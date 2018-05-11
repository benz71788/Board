<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link href="css/form.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="header.jsp" />
	<form name="deleteForm" action="./BoardDeleteAction.bo" method="post">
		<input type="hidden" name="num" value="${param.num}">
		<table border="1">
			<tr>
				<th>글 비밀번호</th>
				<td>
					<input type="password" name="BOARD_PASS" id="board_pass">
				</td>
			</tr>
			<tr>
				<td colspan=2 class="h30 center lime">
					<input type="submit" value="삭제">
					<input type="button" value="취소" onClick="history.go(-1)">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>