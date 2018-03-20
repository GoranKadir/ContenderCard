

$(document).ready(function(){
	
	$('.table .eBtn').on('click', function(event){
		event.preventDefault();
		var href = $(this).attr('href');
		
		$.get(href,function(user,status){
			$('.myForm #id').val(user.id);
			$('.myForm #username').val(user.username);
			$('.myForm #firstname').val(user.firstname);
			$('.myForm #lastname').val(user.lastname);
			$('.myForm #personnr').val(user.personnr);
			$('.myForm #adress').val(user.adress);
			$('.myForm #ort').val(user.ort);
			$('.myForm #telefon').val(user.telefon);
			$('.myForm #email').val(user.email);
			$('.myForm #klubb').val(user.klubb);
		});
		
		$('.myForm #exampleModal').modal();
	});
});