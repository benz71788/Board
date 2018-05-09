<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<script src="/JspProject/js/jquery-3.3.1.js"></script>
<script>
	
	$(document).ready(function(){
		$('form').submit(function(event){
			var id = $.trim($('input[name=id]').val());
			if(id == ""){
				alert("아이디를 입력하세요.");
				$('input[name=id]').focus();
				return false;
			}
			
			var pass = $.trim($('input[name=pass]').val());
			if(pass.length < 4){
				alert("비밀번호를 4자 이상 입력하세요.");
				$('input[name=pass]').focus();
				return false;
			}
		});
		
		$('.join').click(function(){
			location.href="join.net";
		});
		
	});
</script>
</head>
<body>
	<form name="login" action="loginProcess.net" method="post">
		<h1>로그인</h1>
		<hr>
		<b>아이디</b>
			<input type="text" name="id" placeholder="Enter id" required><br>
		<b>Password</b>
			<input type="password" name="pass" placeholder="Enter pass" required><br>
		<div class="clearfix">
			<button type="submit" class="submitbtn">로그인</button>
			<button type="button" class="join">회원가입</button>
		</div>
	</form>
</body>
</html>