$(document).ready(function() {
	$('body').on('click', 'button[data-type="form-reset"]', function(e) {
		var targetFormID = $(this).attr('data-target-form');
		e.preventDefault();
		$(targetFormID)[0].reset();

	})
	$('.modal').on('hidden.bs.modal', function() {
		//remove the backdrop
		$('.modal-backdrop').remove();
	});
	//Password toggle functionlaity
	$('body').on('click', '*[data-type="password_toggle"]', function() {
		var targetEleID = $(this).attr('data-target');
		if ($(targetEleID).attr('type') == "text") {
			$(targetEleID).attr('type', "password");
			$(this).attr('class', 'fas fa-eye');
		}
		else {
			$(targetEleID).attr('type', "text");
			$(this).attr('class', 'fas fa-eye-slash');
		}
	});
});