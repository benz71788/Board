<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="/JspProject/js/jquery-3.3.1.js"></script>
<link href="css/join.css" rel="stylesheet">
<script>
	$(document).ready(function(){
		$(".loginbtn").click(function(event){
			location.href="login.net";
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
	<form name="joinform" action="joinProcess.net"
		method="post">
		<h1>회원가입 페이지</h1>
		<hr>
		<b>아이디</b>
		<input type="text" name="id" placeholder="Enter id" required><br>
		<b>비밀번호</b>
		<input type="password" name="pass" placeholder="Enter pass" required><br>
		<b>이름</b>
		<input type="text" name="name" placeholder="Enter name" required><br>
		<b>나이</b>
		<input type="text" name="age" maxlength=2 placeholder="Enter age" required><br>
		<b>성별</b><div>
		<input type="radio" name="gender" value="남" checked><span>남자</span>
		<input type="radio" name="gender" value="여"><span>여자</span><br></div>
		<b>이메일 주소</b>
		<input type="text" name="email" placeholder="Enter email" required><br>
		<hr>
		<div class="clearfix">
			<button type="submit" class="submitbtn">회원가입</button>
			<button type="reset" class="cancelbtn">다시작성</button>
			<button type="button" class="loginbtn">로그인</button>
		</div>
	</form>
</body>
</html>