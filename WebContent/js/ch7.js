$(document).ready(function(){
	$('form').submit(function(event){
		var name = $.trim($('#name').val());
		
		var id = $.trim($('#id').val())
		if(id == ""){
			alert('아이디를 입력하세요.');
			$('#id').focus();
			event.preventDefault();
		}
		
		var pass = $.trim($('#pass').val().length);
		if( pass < 6){
			alert("비밀번호를 6자 이상 입력하세요.")
			$('#pass').focus().val("");
			event.preventDefault();
		}
		
		//첫자리에 0을 제외한 숫자가 오고 [0-9]가 1개 또는 2개 오도록 합니다.
		var pattern = /^[1-9][0-9]{1,2}$/;
		
		if(!pattern.test(age)){
			alert("2-3자리의 숫자를 입력하세요[0으로 시작하지 마세요]");
			$("#age").val('');
			$("#age").focus();
			return false;
		}
		
		var email = $.trim($("#email").val())
		if(email == ""){
			alert("이메일 주소를 입력하세요");
			$("#email").val('');
			$("#email").focus();
			event.preventDefault();
		}

	})

});