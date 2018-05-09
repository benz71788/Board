$(document).ready(function(){
	$('form').submit(function(event){
		if($.trim($('#id').val()) == ""){
			alert('아이디를 입력하세요.');
			$('#id').focus();
			event.preventDefault();
		}
		
		if($.trim($('#pass').val().length) < 6){
			alert("비밀번호를 6자 이상 입력하세요.")
			$('#pass').focus().val("");
			event.preventDefault();
		}
		
		if($.trim($('#email').val()) == ""){
			alert('이메일을 입력하세요.');
			$('#email').focus();
			event.preventDefault();
		}
		
		if($.trim($('#domain').val()) == ""){
			alert('도메인을 입력하세요.');
			$('#domain').focus();
			event.preventDefault();
		}
		
		if($('#gender1').is(':checked') == false && 
				$('#gender2').is(':checked') == false){
			alert('남자, 여자 중에서 선택하세요.')
			event.preventDefault();
		}
		
		var checkbox = $('input[type=checkbox]:checked');
		if(checkbox.length < 2){
			alert("취미를 2개 이상 입력하세요.");
			event.preventDefault();
		}
		
		if($.trim($('#post1').val()) == ""){
			alert("우편번호 앞자리를 입력하세요.");
			$('#post1').focus();
			event.preventDefault();
		} else if($.trim($('#post2').val()) == ""){
			alert("우편번호를 뒷자리를 입력하세요.");
			$('#post2').focus();
			event.preventDefault();
		}
		
		if($.trim($('#address').val()) == ""){
			alert("주소를 입력하세요.");
			$('#address').focus();
			event.preventDefault();
		}
		
		if($.trim($('#intro').val()) == ""){
			alert("자기소개를 작성하세요.");
			$('#intro').focus();
			event.preventDefault();
		}
		
		if(isNaN($('#jumin1, #jumin2').val())){
			alert('숫자를 입력하세요.');
			event.preventDefault();
		}  else {
			if($.trim($('#jumin1').val().length) < 6){
				alert("주빈번호 앞 6자리를 입력하세요.");
				$('#jumin1').focus();
				event.preventDefault();
			} else if($.trim($('#jumin2').val().length) < 7){
				alert("주빈번호 뒷 7자리를 입력하세요.");
				$('#jumin2').focus();
				event.preventDefault();
			}
		}
	});
	
	$('#jumin1, #jumin2').on('keyup', function(){
		if(isNaN($(this).val())){
			alert('숫자를 입력하세요.');
			$(this).focus();
			return false;
		} 
	});
	
	$('#idcheck').on('click', function(){
		if($('#id').val() == ""){
			alert('아이디를 입력하세요.');
			$('#id').focus();
			return false;
		} else {
			var ref = "idcheck.html";
			window.open('', 'idcheck', 'width=300, height=300');
		}
	});
	
	$('#postcode').on('click', function(){
		if($('#post1').val() == ""){
			alert("우편번호 앞자리를 입력하세요.");
			$('#post1').focus();
			return false;
		} else if($('#post2').val() == ""){
			alert("우편번호를 뒷자리를 입력하세요.");
			$('#post2').focus();
			return false;
		} else {
			var ref = "postSearch.html";
			window.open('', 'postSearch', 'width=300, height=300');
		}
	});
	
	$('#sel').on('change', function(){
		if($("#sel").val() == ""){
			$("#domain").val("").focus();
			$('#domain').attr("disabled", false);
		} else {
			$("#domain").val($("#sel").val());
			$("#domain").attr("disabled", "disabled");
		}
		

		
	})

});