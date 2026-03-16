$(document).ready(function(){
	functions = {
		showLoader:function(){
			$('.loader').show();
    		$('.overlay').show();
		},
		hideLoader:function(){
			$('.loader').hide();
     		$('.overlay').hide();
		}
	}
});
function showLoader(){
	$('.loader').show();
    $('.overlay').show();
}

function hideLoader(){
	 $('.loader').hide();
     $('.overlay').hide();
}