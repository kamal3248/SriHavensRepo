<div id="edit_mirror_master_record_modal" class="modal fade"
	tabindex="-1" role="dialog" data-backdrop='static'>
	<div class="modal-dialog  modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Edit Mirror Master Record</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form class="mx-1 mx-md-4" id="edit_mirror_master_record_frame">
					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="mirror_master_id">Mirror ID</label>
							<input type="text" id="mirror_master_id" class="form-control"
								placeholder=" " disabled />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="mirror_master_name">Name</label>
							<input type="text" id="mirror_master_name" class="form-control"
								placeholder=" " required />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="mirror_master_quantity">Quantity</label>
							<input type="number" id="mirror_master_quantity"
								class="form-control" required />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="mirror_master_price">Price
								(Rs.)</label> <input type="text" id="mirror_master_price"
								class="form-control" required />
						</div>
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button"  data-type="update-mirror-master-record" class="btn btn-primary">
					<i class="fa-solid fa-wrench"></i> Update
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<i class="fa-solid fa-times"></i> Close
				</button>
			</div>
		</div>
	</div>
</div>