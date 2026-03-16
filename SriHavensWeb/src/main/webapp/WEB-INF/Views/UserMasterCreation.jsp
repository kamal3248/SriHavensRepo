<div id="create_user_master_record_modal" class="modal fade"
	tabindex="-1" role="dialog" data-backdrop='static'>
	<div class="modal-dialog modal-lg  modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Create User Master Record</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="mx-1 mx-md-4 row" id="create_user_master_record_form">
					<div class="col-md-6">
						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_user_id">UserID</label> <input
									type="text" id="u_user_id" class="form-control" placeholder=" "
									required />
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_user_name">Name</label> <input
									type="text" id="u_user_name" class="form-control" />
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_user_email">Email</label> <input
									type="email" id="u_user_email" class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_user_role">Role</label>
								<select data-type="user-roles" id="u_user_role" class="form-select"></select>
							</div>
						</div>

					</div>
					<div class="col-md-6">
						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_mobile_number">Mobile
									Number</label> <input type="text" id="u_mobile_number"
									class="form-control" />

							</div>
						</div>
						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_pswd">Password</label> <input
									type="password" id="u_pswd" class="form-control" />
								<span class="password-toggle-icon"><i data-type="password_toggle"
									data-target="#u_pswd" class="fas fa-eye"></i></span>
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="u_rep_pswd">Repeat your
									password</label> <input type="password" id="u_rep_pswd"
									class="form-control" />
								<span class="password-toggle-icon"><i data-type="password_toggle"
									data-target="#u_rep_pswd" class="fas fa-eye"></i></span>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					data-type="create-user-master-record">
					<i class="fa-solid fa-circle-plus"></i> Create
				</button>
				<button type="button" class="btn btn-secondary"
					data-type="form-reset" data-target-form="#create_user_master_record_form">
					<i class="fa-solid fa-trash-arrow-up"></i> Reset
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<i class="fa-solid fa-times"></i> Close
				</button>
			</div>
		</div>
	</div>
</div>