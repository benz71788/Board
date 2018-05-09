<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="/JspProject/js/jquery-3.3.1.js"></script>
<c:set var="m" value="${memberinfo}"/>
<script>
	$(document).ready(function(){
		var gender = '${m.gender}';
		if(gender == "남"){
			$('input[value=남]').attr("checked", "checked");
		} else {
			$('input[value=여]').attr("checked", "checked");
		}
		
		$(".cancelbtn").click(function(){
			location.href="main.net";	
		});
		$('.submitbtn').click(function(event){
			if($.isNumeric($('input[name=age]').val()) == false){
				alert('나이는 숫자만 입력하세요');
				$('input[name=age]').focus();
				return false;
			}
		});
	});
	
</script>
</head>
<body>
	<form name="joinform" action="updateProcess.net"
		method="post">
		<h1>회원 수정 페이지</h1>
		<hr>
		<b>아이디</b>
		<input type="text" name="id" value="${m.id}" required><br>
		<b>비밀번호</b>
		<input type="password" name="pass" value="${m.passward}" required><br>
		<b>이름</b>
		<input type="text" name="name" value="${m.name}" required><br>
		<b>나이</b>
		<input type="text" name="age" maxlength=2 value="${m.age}" required><br>
		<b>성별</b><div>
		<input type="radio" name="gender" value="남"><span>남자</span>
		<input type="radio" name="gender" value="여"><span>여자</span><br></div>
		<b>이메일 주소</b>
		<input type="text" name="email" value="${m.email}" required><br>
		<hr>
		<div class="clearfix">
			<button type="submit" class="updatebtn">수정</button>
			<button type="reset" class="cancelbtn">다시작성</button>
		</div>
	</form><br><br>
</body>
</html>