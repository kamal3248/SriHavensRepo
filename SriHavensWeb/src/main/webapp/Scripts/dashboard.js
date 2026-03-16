$(document).ready(function(){
	//load all dashboard data from database while the page is loading
	$.ajax({
			url : URLS.DASHBOARD,
			method : "POST",
			data : {
			},		
			success : function(data) {
				
					var userDetails = data.userDetails;
					$('#welcome-container').html(userDetails.NAME);
					$('#userid').html(userDetails.UID);
					$('#name').html(userDetails.NAME);
					$('#email').html(userDetails.EMAIL);
					$('#mobile').html(userDetails.MOBILE);
					$('#last_login_at').html(userDetails.LASTLOGINAT);
					$('#role').html(userDetails.ROLE);		
			},
			complete: function(){
				hideLoader();
			}
							   
		});
});