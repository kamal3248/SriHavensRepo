$(document).ready(function(){

	showLoader();
	
	//jsp pages path
	window.jspPath = {
		HOME:"./Home",
		DASHBOARD:"./Dashboard",
		USERADMIN:"./UserAdmin",
		BILLS:"./Bills",
		PHOTOS_MASTER:"./PhotoMaster",
		FRAME_MASTER:"./FrameMaster",
		MIRROR_MASTER:"./MirrorMaster"
	}
	window.URLS = {
		DASHBOARD:"./DashboardServ",
		USER_MASTER:"./UserAdminServ",
		PHOTO_MASTER:"./PhotosMasterServ",
		FRAME_MASTER:"./FrameMasterServ",
		MIRROR_MASTER:"./MirrorMasterServ",
		DROPDOWN_VALUES:"./DropDownValues",
		BILL_MASTER:"./BillMasterServ"
		
	}
	window.entityStatus = {
		Y: "Available",
		N: "Deleted"
	}
	window.statusIcons = {
		Y: "status text-success",
		N: "status text-warning",
		D: "status text-danger"
	};
	
	window.userActionMode = {
		O: "OVERVIEW",
		C: "CREATE",
		U: "UPDATE",
		D: "DELETE",
		R: "RECREATE",
		L: "LOCK",
		UL:"UNLOCK",
		P: "PSWD_CHANGE"
	}
	
	//Load default page first
	$('#content-area').load(jspPath["DASHBOARD"]);
	//Control navigation panel
	$('ul[data-type="nav"] li').click(function(){
		$('ul[data-type="nav"] li').removeClass("active");
		$(this).addClass("active");
		$('p[data-type="data-title"]').html($(this).attr('data-title'));
		
		$('#content-area').load(jspPath[$(this).attr('data-view')]);
	});
	
		
	//handle logout functionality
	$('#logout').click(function(){
		showLoader();
		$.ajax({
			url : "./LogoutServlet",
			method : "POST",
			data : {
			},		
			success : function(data) {
					alert(data.message);
					if(data.status=='S')
					{
					
					window.location.href=data.redirectURL;
					}	
			},
			complete: function(){
				hideLoader();
			}
							   
		});
	});
	
	$('.modal').on('hidden.bs.modal', function() {
		//remove the backdrop
		$('.modal-backdrop').remove();
	});
	
});