
function generateAlertModal(header,message){
	var html = '<div data-type="modal show" class="modal" id="alertModal"'+
				'<div class="modal-dialog">'+
    			'<div class="modal-content">'+
     	 		'<div class="modal-header">'+
        		'<h5 class="modal-title" id="modal-title">'+header+'</h5>'+
        		'<button type="button" class="modal-close" data-dismiss="modal">'+
          		'<span aria-hidden="true">&times;</span>'+
        		'</button>'+
     			'</div>'+
      			'<div class="modal-body">'+
        		'<form>'+
          		'<div class="form-group">'+
            	'<label for="message-text" class="col-form-label">Message:</label>'+
            	'<textarea class="form-control" id="message-text">'+message+'</textarea>'+
          		'</div>'+
        		'</form>'+
      			'</div>'+
      			'<div class="modal-footer">'+
        		'<button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>'+
      			'</div>'+
    			'</div>'+
  				'</div>'+
				'</div>';
				
				$('body').append(html);
				$('.overlay').show();
};