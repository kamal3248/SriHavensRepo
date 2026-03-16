$(document).ready(function() {
	var functions = {
		submitPasswordUpdate: function(password, rpassword) {
			showLoader();
			$.ajax({
				url: "./DefaultPswdServ",
				method: "POST",
				async: false,
				data: {
					PASSWORD: password,
					REPEAT_PASSWORD:rpassword
				},
				success: function(data) {
					if(data!=null && data!=undefined){
						alert(data.message);
						if(data.status=="Y"){
							$('#default-pswd_change-form')[0].reset();
							window.location.href=data.redirectURL;
						}
					}
					else{
						alert("Something went wrong.");
					}
				},
				complete: function() {
					hideLoader();
				}

			});
		}
	}


	$('button[data-type="default-pswd-change"]').click(function() {
		var password = $('#d_pswd').val();
		var rPassword = $('#d_rep_pswd').val();
		if (password == null || password == "" || password == undefined) {
			alert("Password can not be empty.");
			$('#d_pswd').focus();
		}
		else if (rPassword == null || rPassword == "" || rPassword == undefined) {
			alert("Repeat password can not be empty.");
			$('#d_rep_pswd').focus();
		}
		else {
			functions.submitPasswordUpdate(password, rPassword);
		}
	})
});