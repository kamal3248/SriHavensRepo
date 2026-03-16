$(document).ready(function() {
	var functions = {
		LoadCaptcha: function() {
			$('#f_captcha').val(null);
			showLoader();
			$.ajax({
				url: "./CaptchaHandlerServ",
				method: "POST",
				async: false,
				data: {
					MODE: "GENERATE",
				},
				success: function(data) {
					$('#captcha').attr('src', data.CAPTCHAIMAGESTRING);
				},
				complete: function() {
					hideLoader();
				}

			});
		},
		ValidateCaptcha: function(userID, captcha) {
			showLoader();
			$.ajax({
				url: "./CaptchaHandlerServ",
				method: "POST",
				async: false,
				data: {
					MODE: "VALIDATE",
					CAPTCHACODE: captcha,
					USERID: userID
				},
				success: function(data) {
					if (data.CAPTCHAMATCHING != undefined && data.CAPTCHAMATCHING == true) {
						$.ajax({
							url: "./TempPasswordServ",
							method: "POST",
							async: false,
							data: {
								USERID: userID
							},
							success: function(data) {
								alert(data.message);
								if (data.status == "Y") {
									$('#forgot_password_modal').modal('hide');
									$('#forgot_password_form')[0].reset();
									
								}
								functions.LoadCaptcha();
								

							}
						});
					} else {
						alert("Invalid Captcha. Please try again.");
						functions.LoadCaptcha();
					}
					hideLoader();

				}

			});
		}
	}

	functions.LoadCaptcha();

	$('a[data-type="forgot_password"]').click(function() {
		$('#forgot_password_modal').modal('show');
	})

	$('button[data-type="refresh_captcha"]').click(function() {
		functions.LoadCaptcha();
	})

	$('button[data-type="verify_captcha"]').click(function() {
		var userid = $('#f_user_id').val();
		var captcha = $('#f_captcha').val();
		if (userid == null || userid == "" || userid == undefined) {
			alert("UserID can not be empty.");
			$('#f_user_id').focus();
		}
		else if (captcha == null || captcha == "" || captcha == undefined) {
			alert("Captcha can not be empty.");
			$('#f_captcha').focus();
		}
		else {
			functions.ValidateCaptcha(userid, captcha);
		}
	})
});