<div id="create_mirror_master_record_modal" class="modal fade"
	tabindex="-1" role="dialog" data-backdrop='static'>
	<div class="modal-dialog  modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Create Mirror Master Record</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<audio id="mirror_Master_Record_Create_Success" class="hide"> <source src="./Music/Mirror_Master_Record_Create_Success.mp3" type="audio/mpeg"> </audio>
			<div id="create_mirror_master_record_modal_body" class="modal-body">
				<form class="mx-1 mx-md-4" id="create_mirror_master_record_form">
					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="m_master_name">Name</label> <input
								type="text" id="m_master_name" class="form-control"
								placeholder=" " required />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="m_master_quantity">Quantity</label>
							<input type="number" id="m_master_quantity" class="form-control"
								required />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="m_master_price">Price
								(Rs.)</label> <input type="text" id="m_master_price"
								class="form-control" required />
						</div>
					</div>

				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					data-type="create-mirror-master-record">
					<i class="fa-solid fa-circle-plus"></i> Create
				</button>
				<button type="button" class="btn btn-secondary"
					 data-type="form-reset" data-target-form="#create_mirror_master_record_form">
					<i class="fa-solid fa-trash-arrow-up"></i> Reset
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<i class="fa-solid fa-times"></i> Close
				</button>

			</div>
		</div>
	</div>
</div>