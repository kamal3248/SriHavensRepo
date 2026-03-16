$(document).ready(function() {
	let items_table_id = "#bill_items_table";
	const defaultSelectOptions = "<option value=NONE>---Select---</option>";
	let BillMasterData = [];
	let selectedBillID = null;
	var entityStatus={
		I:"Instant",
		D:"Delivered",
		S:"Scheduled"
	};
	var statusIcons = {
		I: "status text-success",
		S: "status text-warning",
		D: "status text-success"
	};
	var functions = {
		init: function() {
			$.ajax({
				url: URLS.BILL_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				success: function(data) {
					console.log(data);
					if (data != null && data != undefined) {
						console.log(data);
						if (data.MESSAGESTATUS == false == 'E') {
							alert(data.message);
						}
						else {
							console.log(data);
							var billMap = data.BILLMAP;
							BillMasterData = billMap;
							var html = '<td colspan="9" class="txt-center-align danger">No records available to display.</td>';
							var row = 1;
							if (Object.keys(billMap).length) {
								html = "";
							}
							$.each(billMap, function(index, record) {
								html += '<tr>' +
									'<td>' + (row) + '</td>' +
									'<td><a  data-type="view-bill-master-record" href="#" data-attr="' + record.BILLID + '">' + record.BILLID + '</a></td>';
									var items = "<ol>";
									$.each(record.ITEMS,function(i,r){
										items+="<li>"+r.ITEMDESC + "-" + r.ITEMPRICE+ "-" +r.QUANTITY+"</li>";
									});
									items+="</ol>";
									html+= '<td>' + items + '</td>' +
									'<td>' + record.PRICE + '</td>' +
									'<td>' + record.CREATEDON + '</td>' +
									'<td>' + record.CREATEDBY + '</td>' +
									'<td><span class="' + statusIcons[record.DELIVERYSTATUS] + '">&bull;</span> ' + entityStatus[record.DELIVERYSTATUS] + '</td>';

							/*	if (record.STATUS == 'Y') {*/
									html += '<td>' +
										'<a data-type="edit-bill-master-record" href="#" data-attr="' + record.BILLID + '" class="edit" title="Edit" data-toggle="tooltip"><i class="icon-md fa-solid fa-pen-to-square"></i></a>' +
										'<a data-type="delete-bill-master-record-int" href="#" data-attr="' + record.BILLID + '" class="delete" title="Delete" data-toggle="tooltip"><i class="icon-md fa-solid fa-trash"></i></a>' +
										'<a data-type="order-delivery-master-record-int" href="#" data-attr="' + record.BILLID + '" class="warning" title="Order Delivery" data-toggle="tooltip"><i class="icon-md fa-solid fa-truck-ramp-box"></i></a>' +
										'</td>';
								/*}
								else {
									html += '<td><a data-type="recreate-frame-master-record-int" href="#" data-attr="' + record.BILLID + '" class="edit" title="Recreate" ><i class="fa-solid fa-retweet"></i></a></td>';
								}*/

								html += '</tr>';
								row++;
							});
						}
						let tableID = "#bills-overview";
						var table = $(tableID);
						if (table.hasClass("dataTable")) {
							var dataTable = $(tableID).DataTable();
							dataTable.clear();
							dataTable.destroy();
						}
						$('#u_ovr_tbl_body').html(html);
						$(tableID).DataTable();
					}
				},

				error: function(request, status, error) {

					var val = request.responseText;
					alert("error" + val);
				},
				complete: function() {
					hideLoader();
				}
			});
		},
		create: function() {

		},
		edit: function() {

		},
		update: function() {

		},
		delete: function() {

		},
		calculateTotalAmount: function() {
			let totalAmount = 0;
			$('input[name="select-item"]').each(function() {
				var key = $(this).attr('data-key');
				if (key.startsWith('PH')) {
					totalAmount += PHOTOMAP[key].PRICE;
				}
				else if (key.startsWith('FR')) {
					totalAmount += FRAMEMAP[key].PRICE;
				}
				else if (key.startsWith('MR')) {
					totalAmount += MIRRORMAP[key].PRICE;
				}
			});
			return totalAmount;
		},
		calcuteAmounts: function() {
			let totalAmount = this.calculateTotalAmount();
			var advance = $('#b_advance_amount').val();
			var discount = $('#b_discount_amount').val();

			if (advance == null || advance == "" || advance == undefined || isNaN(parseFloat(advance)) || advance < 0) {
				advance = 0;
			}
			if (discount == null || discount == "" || discount == undefined || isNaN(parseFloat(discount)) || discount < 0) {
				discount = 0;
			}
			var finalAmount = totalAmount - advance - discount;
			if (finalAmount < 0) {
				finalAmount = totalAmount;
				alert("Please recheck the amount that you have entered. Final amount can not be negative.");
				e.preventDefault();
				e.stopPropagation();
				$(this).attr('style', 'border:1px solid red');

			}
			$('#b_final_amount').val(finalAmount);
			$('#b_total_amount').val(finalAmount);
		},
		initPhotosPrintDropdown: function() {

			$.ajax({
				url: URLS.PHOTO_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				async: false,
				success: function(data) {
					if (data != null && data != undefined) {
						if (data.MESSAGESTATUS == false) {
							alert("An issue occurred while loading dropdown values");
						}
						else {
							var PhotoMAP = data.PHOTOSMAP;
							window.PHOTOMAP = PhotoMAP;
							var optionsDefault = "<option value=NONE>No Data Available</option>";
							var options = "<option value=NONE>---Select---</option>";
							$.each(PhotoMAP, function(index, record) {
								options += "<option value=" + record.PHOTOID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(PhotoMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_photo_print_dd').html(options);
							$('#b_photo_print_dd').show();
						}
					}
				},
				complete: function() {
					hideLoader();
				}
			});
		},
		initFrameDropdown: function() {
			$.ajax({
				url: URLS.FRAME_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				async: false,
				success: function(data) {

					if (data != null && data != undefined) {
						if (data.MESSAGESTATUS == false) {
							alert("An issue occurred while loading dropdown values");
						}
						else {
							var FrameMAP = data.FRAMEMAP;
							window.FRAMEMAP = FrameMAP;
							var optionsDefault = "<option value=NONE>No Data Available</option>";
							var options = "<option value=NONE>---Select---</option>";
							$.each(FrameMAP, function(index, record) {
								options += "<option value=" + record.FRAMEID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(FrameMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_frame_dd').html(options);
							$('#b_frame_dd').show();
						}
					}
				},
				complete: function() {
					hideLoader();
				}
			});
		},
		initMirrorDropdown: function() {
			$.ajax({
				url: URLS.MIRROR_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				async: false,
				success: function(data) {

					if (data != null && data != undefined) {
						if (data.MESSAGESTATUS == false) {
							alert("An issue occurred while loading dropdown values");
						}
						else {
							var MirrorMAP = data.MIRRORMAP;
							window.MIRRORMAP = MirrorMAP;
							var optionsDefault = "<option value=NONE>No Data Available</option>";
							var options = "<option value=NONE>---Select---</option>";
							$.each(MirrorMAP, function(index, record) {
								options += "<option value=" + record.MIRRORID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(MirrorMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_mirror_dd').html(options);
							$('#b_mirror_dd').show();
						}
					}
				},
				complete: function() {
					hideLoader();
				}
			});
		},
		initDropDowns: function() {
			$.ajax({
				url: URLS.DROPDOWN_VALUES,
				method: "POST",
				async: false,
				data: {
					MODE: "DD_READ",
				},

				success: function(data) {

					if (data != null) {
						if (data.transactionStatus == false) {
							alert("An issue occurred while loading dropdown values");
						}
						else {
							var PhotoMAP = data.photoMap;
							var FrameMAP = data.frameMap;
							var MirrorMAP = data.mirrorMap;
							var optionsDefault = "<option value=NONE>No Data Available</option>";
							var options = "<option value=NONE>---Select---</option>";
							$.each(PhotoMAP, function(index, record) {
								options += "<option value=" + record.PHOTOID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(PhotoMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_photo_print_dd').html(options);

							options = "<option value=NONE>---Select---</option>";
							$.each(FrameMAP, function(index, record) {
								options += "<option value=" + record.FRAMEID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(FrameMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_frame_dd').html(options);

							options = "<option value=NONE>---Select---</option>";
							$.each(MirrorMAP, function(index, record) {
								options += "<option value=" + record.MIRRORID + ">" + record.DESCRIPTION + "</option>";
							})
							if (Object.keys(MirrorMAP).length == 0) {
								options = optionsDefault;
							}
							$('#b_mirror_dd').html(options);
						}
					}
				},
				complete: function() {
					hideLoader();
				}
			});
		}
	}
	$('button[data-type="open_create_bill_modal"]').click(function() {
		$('#b_mirror_dd').html(defaultSelectOptions);
		$('#b_photo_print_dd').html(defaultSelectOptions);
		$('#b_frame_dd').html(defaultSelectOptions);
		$('#create_bill_modal').modal('show');

	});
	$('#b_is_photo_print').on('change', function() {
		if ($(this).is(":checked")) {
			functions.initPhotosPrintDropdown();
			$('#b_photo_print_dd').prop('disabled', false);
		} else {
			$('#b_photo_print_dd').html(defaultSelectOptions);
			$('#b_photo_print_dd').prop('disabled', true);
		}
	});
	$('#b_is_frame').on('change', function() {
		if ($(this).is(":checked")) {
			functions.initFrameDropdown();
			$('#b_frame_dd').prop('disabled', false);
		} else {
			$('#b_frame_dd').html(defaultSelectOptions);
			$('#b_frame_dd').prop('disabled', true);
		}
	});
	$('#b_is_mirror').on('change', function() {
		if ($(this).is(":checked")) {
			functions.initMirrorDropdown();
			$('#b_mirror_dd').prop('disabled', false);
		} else {
			$('#b_mirror_dd').html(defaultSelectOptions);
			$('#b_mirror_dd').prop('disabled', true);
		}
	});

	$('button[data-type="remove-from-items"]').click(function(e) {
		e.preventDefault();
		e.stopPropagation();
		$('input[name="select-item"]').each(function() {
			if ($(this).is(":checked")) {
				$(this).closest('tr').remove();
				functions.calcuteAmounts();
			}
		});
	});

	$('button[data-type="add-to-items"]').click(function(e) {
		e.preventDefault();
		e.stopPropagation();
		var options = "<option value=NONE>---Select---</option>";
		var isEmptyTable = $($(items_table_id).find('tbody tr')[0]).attr('data-type') == 'empty-row';
		var tbody = $(items_table_id).find('tbody').get(0);
		var totalPrice = 0;
		if ($('#b_photo_print_dd').val() != "NONE") {
			var record = PHOTOMAP[$('#b_photo_print_dd').val()];
			if (record != null && record != undefined) {
				var itemRow = "<tr>" +
					"<td><input type='checkbox' data-key='" + record.PHOTOID + "' name='select-item'></td>" +
					"<td>" + record.PHOTOID + "</td>" +
					"<td>" + record.DESCRIPTION + "</td>" +
					"<td>" + record.QUANTITY + "</td>" +
					"<td>" + record.PRICE + "</td>" +
					"<td><img class='img-preview-sm' src=" + window.tmpSrc + " /></td></tr>";
				totalPrice += record.PRICE;
				if (isEmptyTable) {
					$(tbody).html(itemRow);
					isEmptyTable = false;
				}
				else {
					$(tbody).append(itemRow);
				}
				$('#b_photo_print_dd').html(options);
				$('#b_photo_print_dd').val("NONE");
				$('#b_photo_print_dd').prop('disabled', true);
				$('#b_is_photo_print').prop('checked', false);
			}
		}
		if ($('#b_frame_dd').val() != "NONE") {
			var record = FRAMEMAP[$('#b_frame_dd').val()];
			if (record != null && record != undefined) {
				var itemRow = "<tr>" +
					"<td><input type='checkbox' data-key='" + record.FRAMEID + "' name='select-item'></td>" +
					"<td>" + record.FRAMEID + "</td>" +
					"<td>" + record.DESCRIPTION + "</td>" +
					"<td>" + record.QUANTITY + "</td>" +
					"<td>" + record.PRICE + "</td>" +
					"<td><img class='img-preview-sm' src=" + window.tmpSrc + " /></td></tr>";
				totalPrice += record.PRICE;
				if (isEmptyTable) {
					$(tbody).html(itemRow);
					isEmptyTable = false;
				}
				else {
					$(tbody).append(itemRow);
				}
				$('#b_frame_dd').html(options);
				$('#b_frame_dd').val("NONE");
				$('#b_frame_dd').prop('disabled', true);
				$('#b_is_frame').prop('checked', false);
			}
		}
		if ($('#b_mirror_dd').val() != "NONE") {
			var record = MIRRORMAP[$('#b_mirror_dd').val()];
			if (record != null && record != undefined) {
				var itemRow = "<tr>" +
					"<td><input type='checkbox' data-key='" + record.MIRRORID + "' name='select-item'></td>" +
					"<td>" + record.MIRRORID + "</td>" +
					"<td>" + record.DESCRIPTION + "</td>" +
					"<td>" + record.QUANTITY + "</td>" +
					"<td>" + record.PRICE + "</td>" +
					"<td><img class='img-preview-sm' src=" + window.tmpSrc + " /></td></tr>";
				totalPrice += record.PRICE;
				if (isEmptyTable) {
					$(tbody).html(itemRow);
					isEmptyTable = false;
				}
				else {
					$(tbody).append(itemRow);
				}
				$('#b_mirror_dd').html(options);
				$('#b_mirror_dd').val("NONE");
				$('#b_mirror_dd').prop('disabled', true);
				$('#b_is_mirror').prop('checked', false);
			}
		}
		$('#b_total_amount').val(functions.calculateTotalAmount());
		$('#b_final_amount').val(functions.calculateTotalAmount());
	});

	$('#b_image').change(function(e) {
		var image = this.files[0];
		if (image) {
			let reader = new FileReader();
			reader.onload = function(event) {
				window.tmpSrc = event.target.result;
			};
			reader.readAsDataURL(image);
		}
	});

	$('#b_delivery_scheduled').click(function(e) {
		$('#b_schedule_date').prop('disabled', false);
	});

	$('#b_delivery_instant').click(function(e) {
		$('#b_schedule_date').prop('disabled', true);
		$('#b_schedule_date').val(null);
	});

	$('#b_advance_amount,#b_discount_amount').on('input', function(e) {
		$(this).attr('style', '');
		var totalAmount = functions.calculateTotalAmount();;
		var advance = $('#b_advance_amount').val();
		var discount = $('#b_discount_amount').val();

		if (advance == null || advance == "" || advance == undefined || isNaN(parseFloat(advance)) || advance < 0) {
			advance = 0;
		}
		if (discount == null || discount == "" || discount == undefined || isNaN(parseFloat(discount)) || discount < 0) {
			discount = 0;
		}
		var finalAmount = totalAmount - advance - discount;
		if (finalAmount < 0) {
			finalAmount = totalAmount;
			alert("Please recheck the amount that you have entered. Final amount can not be negative.");
			e.preventDefault();
			e.stopPropagation();
			$(this).attr('style', 'border:1px solid red');

		}
		$('#b_final_amount').val(finalAmount);

	});
	$('#close_bill_modal').click(function() {
		var tbody = $(items_table_id).find('tbody').get(0);
		$(tbody).html('<tr data-type="empty-row"><td class="txt-center danger" colspan="5">No Data Available</td></tr>');
	})

	$('button[data-type="create-bill"]').click(function() {
		showLoader();
		var items = [];
		$('input[name="select-item"]').each(function() {
			items.push($(this).attr('data-key'));
		});
		$.ajax({
			url: URLS.BILL_MASTER,
			method: "POST",
			data: {
				MODE: "C",
				NAME: $('#b_cust_name').val(),
				CONTACT: $('#b_cust_mobile').val(),
				EMAIL: $('#b_cust_email').val(),
				ITEMS: JSON.stringify(items),
				ADVANCE: $('#b_advance_amount').val(),
				DISCOUNT: $('#b_discount_amount').val(),
				PAYMENT: $('input[name="b_payment_option"]').val(),
				DELIVERY: $('input[name="b_delivery_option"]').val(),
				DELDATE: $('#b_schedule_date').val(),
			},
			async: false,
			success: function(data) {
				console.log(data);
				if (data != null && data != undefined) {
					if (data.CREATESTATUS == false) {
						alert(data.MESSAGE);
					}
					else {
						alert("New order has been created successfully with Order ID: " + data.BILLID);
						$('#create_bill_form')[0].reset();
						var tbody = $(items_table_id).find('tbody').get(0);
						$(tbody).html('<tr data-type="empty-row"><td class="txt-center danger" colspan="5">No Data Available</td></tr>');
					}
				}
			},
			complete: function() {
				hideLoader();
			}
		});
	})
	functions.init();
});
