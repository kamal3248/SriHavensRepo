<div id="pswd_reset_user_master_record_modal" class="modal" tabindex="-1"
	role="dialog" data-backdrop='static'>
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">
					Reset Password for User: <span id="p_user_name"></span>
				</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form id="pswd-reset-user-master-record-form">
				<div class="form-group">
					<div class="form-outline flex-fill mb-0">
						<label class="form-label" for="p_pswd">Password</label> <input
							type="password" id="p_pswd" class="form-control" /> <span
							class="password-toggle-icon"><i
							data-type="password_toggle" data-target="#p_pswd"
							class="fas fa-eye"></i></span>
					</div>
				</div>

				<div class="form-group">
					<div class="form-outline flex-fill mb-0">
						<label class="form-label" for="p_rep_pswd">Repeat your
							password</label> <input type="password" id="p_rep_pswd"
							class="form-control" /> <span class="password-toggle-icon"><i
							data-type="password_toggle" data-target="#p_rep_pswd"
							class="fas fa-eye"></i></span>
					</div>
				</div>
				</form>
			</div>
			<div class="modal-footer">
				<button data-type="pswd-reset-user-master-record" type="button"
					class="btn btn-primary">Yes</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
			</div>
		</div>
	</div>
</div>