$(document).ready(function(){

	$('form').submit(function(event){
		var id = $.trim($('#id').val())
		if(id == ""){
			alert('아이디를 입력하세요.');
			$('#id').focus();
			event.preventDefault();
		}
				
		var pass = $.trim($('#pass').val());
		if( pass.length < 4){
			alert("비밀번호를 4자 이상 입력하세요.")
			$('#pass').focus().val("");
			event.preventDefault();
		}

	});
});