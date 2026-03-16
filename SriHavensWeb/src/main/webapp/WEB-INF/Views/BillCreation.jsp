<div id="create_bill_modal" data-backdrop='static' class="modal fade"
	tabindex="-1" role="dialog">
	<div
		class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable"
		role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">New Bill</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="create_bill_modal_body">
				<audio id="bill_Create_Success" class="hide">
					<source src="./Music/Bill_Generation_Success.mp3" type="audio/mpeg">
				</audio>
				<form class="mx-1 mx-md-4 row" id="create_bill_form"
					enctype="multipart/form-data" method="post">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="form-outline">
								<label class="form-label" for="b_cust_name">Customer
									Name</label> <input type="text" id="b_cust_name" class="form-control"
									placeholder=" " required />
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline">
								<label class="form-label" for="b_cust_mobile">Mobile No</label>
								<input type="number" id="b_cust_mobile" class="form-control"
									required />
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline">
								<label class="form-label" for="b_cust_email">Email</label> <input
									type="email" id="b_cust_email" class="form-control" required />
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<input class="form-check-input" type="checkbox" value=""
								id="b_is_photo_print"> <label class="form-check-label"
								for="b_is_photo_print">Photo Print </label> <select
								class="form-select" disabled id="b_photo_print_dd"></select>
						</div>

						<div class="form-group">
							<input class="form-check-input" type="checkbox" value=""
								id="b_is_frame"> <label class="form-check-label"
								for="b_is_frame"> Frame </label> <select class="form-select"
								disabled id="b_frame_dd"></select>
						</div>
						<div class="form-group">
							<input class="form-check-input" type="checkbox" value=""
								id="b_is_mirror"> <label class="form-check-label"
								for="b_is_mirror"> Mirror </label> <select class="form-select"
								disabled id="b_mirror_dd"></select>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="input-group mb-3">
							<label class="input-group-text" for="b_image">Select
								Image</label><input type="file" id="b_image"
								accept=".jpg, .png, .psd, .gif" class="form-control">
							<div class="form-outline"></div>
						</div>

						<table class="table table-striped" id="bill_items_table">
							<thead>
								<tr>
									<td colspan=5 style="border: none">
										<!-- <button class="btn btn-primary btn-sm rfloat"
											data-type="select-all-items">Select All</button> -->
										<button class="btn btn-danger btn-sm rfloat"
											data-type="remove-from-items">Remove Item</button> <!-- <button class="btn btn-warning btn-sm rfloat"
											data-type="edit-item">Edit Item</button> -->
										<button class="btn btn-info btn-sm rfloat"
											data-type="add-to-items">Add to Items</button>
									</td>
								</tr>
								<tr>
									<th>#</th>
									<th>Type ID</th>
									<th>Particulars</th>
									<th>Quantity</th>
									<th>Amount</th>
									<th>Photo</th>
								</tr>
							</thead>
							<tbody>
								<tr data-type="empty-row">
									<td class="txt-center danger" colspan=5>No Data Available</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="col-sm-12">
						<table style="width: 100%">
							<tr>
								<td><div class="form-group">
										<label class="form-label" for="b_total_amount"> Total
											Amount(in rs.) </label> <input class="form-control disabled"
											type="number" value="0" id="b_total_amount" disabled>
									</div></td>
								<td><div class="form-group">
										<label class="form-label" for="b_final_amount"> Final
											Amount(in rs.) </label> <input class="form-control disabled"
											type="number" value="0" id="b_final_amount" disabled>
									</div></td>
							</tr>
							<tr>
								<td><div class="form-group">
										<label class="form-label" for="b_discount_amount">
											Discount Amount (in rs.) </label> <input class="form-control"
											type="number" value="" id="b_discount_amount">
									</div></td>
								<td><div class="form-group">
										<label class="form-label" for="b_advance_amount">
											Advance (in rs.) </label> <input class="form-control" type="number"
											value="" id="b_advance_amount">
									</div></td>
							</tr>
						</table>
					</div>
					<div class="col-sm-12">
						<h6>Payment</h6>
						<div class="form-check">
							<input class="" type="radio" name="b_payment_option"
								id="b_payment_online" value="O" checked> <label
								class="form-label" for="b_payment_upi"> Online/UPI </label>&nbsp;&nbsp;
							<input class="" type="radio" name="b_payment_option"
								id="b_payment_cash" value="C"> <label class="form-label"
								for="b_payment_cash"> Cash </label>&nbsp;
						</div>
					</div>
					<div class="col-sm-12">
						<h6>Delivery</h6>
						<div class="form-check">
							<input class="" type="radio" name="b_delivery_option"
								id="b_delivery_instant" value="I" checked> <label
								class="form-label" for="b_delivery_instant"> Instant </label>&nbsp;&nbsp;
							<input class="" type="radio" value="S" name="b_delivery_option"
								id="b_delivery_scheduled"> <label class="form-label"
								for="b_delivery_scheduled"> Schedule </label>&nbsp; <input
								id="b_schedule_date" type="date" disabled>
						</div>

					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary"
					data-type="create-bill">
					<i class="fa-solid fa-circle-plus"></i> Create
				</button>
				<button type="button" class="btn btn-secondary"
					data-type="form-reset" data-target-form="#create_bill_form">
					<i class="fa-solid fa-trash-arrow-up"></i> Reset
				</button>
				<button id="close_bill_modal" type="button" class="btn btn-secondary" data-dismiss="modal"
					data-type="form-reset" data-target-form="#create_bill_form">
					<i class="fa-solid fa-times"></i> Close
				</button>
			</div>
		</div>
	</div>
</div>