$(document).ready(function(){
	
	var functions = {
		
		resetLogonForm:function()
		{
			$('#l_password_inp').val("");
			$('#l_logonid_inp').val("");
		},
		
		performLoginActivity: function(e){
			$('#message-bar').empty();
		showLoader();
		var password = $('#l_password_inp').val();
		var username = $('#l_logonid_inp').val();
		
		if(username == null || password==null || username.length==0 || password.length==0){
			var html = '<div class="alert alert-danger" role="alert">'+
  						'<strong>Please input Username and Password</strong></div>';
  						$('#message-bar').append(html);
			e.preventDefault();
			e.stopPropagation();
			hideLoader();
		}
		else
		{
			$.ajax({
					url : "./LogonServlet",
					method : "POST",
					data : {
						   uid: username,
			   			   pswd: password
						 },
						
					success : function(data) {
							console.log(data);
						    if(!data.isUserAuthorized){
								var html = '<div class="alert alert-danger" role="alert">'+
  										'<strong>'+data.message+'</strong></div>';
  								$('#message-bar').append(html);
							}   
							else{
								window.location.href=data.redirectURL;
							}		
							
					},
					complete: function(){
							functions.resetLogonForm();
				            hideLoader();
					}
							   
				});
		}
		}
	};
	$('#l_sign_in_btn').on('click',function(e){
		
			functions.performLoginActivity(e);
		
	});
	
	$('#l_logonid_inp, #l_password_inp').on('keypress',function(e){
		
			if(e.keyCode==13){
				e.preventDefault();
				functions.performLoginActivity(e);
			}
		
	});
	
	$('#l_show_pswd').on('click',function(e){
		if($('#l_password_inp').attr('type')=="text"){
			$('#l_password_inp').attr('type',"password");
			$(this).attr('class','fas fa-eye');
		}
		else{
			$('#l_password_inp').attr('type',"text");
			$(this).attr('class','fas fa-eye-slash');
		}
	});
})