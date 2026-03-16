$(document).ready(function() {
	showLoader();
	var PhotoMasterData = [];
	var selectedPhotoID = null;
	var functions = {
		init: function() {
			$.ajax({
				url: URLS.PHOTO_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				success: function(data) {
					//console.log(data);
					if (data != null) {
						if (data.MESSAGESTATUS == false) {
							alert(data.MESSAGE);
						}
						else {
							if (!(data.ISADMINUSER)) {
								$('button[data-type="open_create_photo_master_record_modal"]').remove();
							}
							var photosMap = data.PHOTOSMAP;
							PhotoMasterData = photosMap;
							var row = 1;
							var html = '<td colspan="9" class="txt-center-align danger">No records available to display.</td>';
							if (Object.keys(photosMap).length) {
								html = "";
							}
							$.each(photosMap, function(index, record) {
								html += '<tr>' +
									'<td>' + row + '</td>' +
									'<td>' + record.PHOTOID + '</td>' +
									'<td><a data-type="view-photo-master-record" href="#" data-attr="' + record.PHOTOID + '">' + record.DESCRIPTION + '</a></td>' +
									'<td>' + record.QUANTITY + '</td>' +
									'<td>' + record.PRICE + '</td>' +
									'<td>' + record.CREATEDON + '</td>' +
									'<td>' + record.CREATEDBY + '</td>' +
									'<td><span class="' + statusIcons[record.STATUS] + '">&bull;</span> ' + entityStatus[record.STATUS] + '</td>';
								if (data.ISADMINUSER) {
									if (record.STATUS == 'Y') {
										html += '<td>' +
											'<a data-type="edit-photo-master-record" href="#" data-attr="' + record.PHOTOID + '" class="edit" title="Edit" data-toggle="tooltip"><i class="fa-solid fa-pen-to-square"></i></a>' +
											'<a data-type="delete-photo-master-record-int" href="#" data-attr="' + record.PHOTOID + '" class="delete" title="Delete" data-toggle="tooltip"><i class="fa-solid fa-trash"></i></a>' +
											'</td>';
									}
									else {
										html += '<td><a data-type="recreate-photo-master-record-int" href="#" data-attr="' + record.PHOTOID + '" class="edit" title="Recreate" ><i class="fa-solid fa-retweet"></i></a></td>';
									}
								}
								else {
									html += "<td></td>";
								}

								html += '</tr>';
								row++;
							});
						}
						let tableID = "#photo-master-overview";
						var table = $(tableID);
						if(table.hasClass("dataTable")){
							var dataTable =$(tableID).DataTable();
							dataTable.clear();
							dataTable.destroy();
						}
						$('#u_ovr_tbl_body').html(html);
						$(tableID).DataTable();	
					}
				},
				complete: function() {
					hideLoader();
				}

			});
		},
		view: function(photoID) {
			var photoRecord = PhotoMasterData[photoID];
			$('#v_photo_master_id').html(photoRecord.PHOTOID);
			$('#v_photo_master_name').html(photoRecord.DESCRIPTION);
			$('#v_photo_master_quantity').html(photoRecord.QUANTITY);
			$('#v_photo_master_price').html(photoRecord.PRICE);
			$('#v_photo_master_status').html(entityStatus[photoRecord.STATUS]);
			$('#v_photo_master_createdby').html(photoRecord.CREATEDBY);
			$('#v_photo_master_createdon').html(photoRecord.CREATEDON);
			$('#v_photo_master_updatedby').html(photoRecord.UPDATEDBY);
			$('#v_photo_master_updatedon').html(photoRecord.UPDATEDON);
			$('#v_photo_master_deletedby').html(photoRecord.DELETEDBY);
			$('#v_photo_master_deletedon').html(photoRecord.DELETEDON);

			$('#view_photo_master_record_modal').modal('show');
		},
		confirm_recreate: function(photoID) {
			if (PhotoMasterData[photoID].STATUS == 'N') {//Y-Available, N-Deleted
				$('#rec_photo_master_id').html(photoID);
				$('#recreate_photo_master_record_modal').modal('show');
			}
			else {
				alert("Selected Photo Master record is already in available status.");
			}
		},
		recreate: function(event) {
			showLoader();
			if (selectedPhotoID != null && selectedPhotoID != "" && selectedPhotoID != undefined) {
				$.ajax({
					url: URLS.PHOTO_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.R,
						PHOTOID: selectedPhotoID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#recreate_photo_master_record_modal').modal('hide');
								functions.init();
							}
						}
					},
					complete: function() {
						hideLoader();
					}
				});
			}
			else {
				alert("Photo ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		create: function(event) {
			showLoader();
			var desc = $('#p_photo_master_name').val();
			var quantity = $('#p_photo_master_quantity').val();
			var price = $('#p_photo_master_price').val();

			if (desc == null || desc == "" || desc == undefined) {
				alert("Name can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else if (quantity == null || quantity == "" || quantity == undefined) {
				alert("Quantity can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else if (price == null || price == "" || price == undefined) {
				alert("Price can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else {
				$.ajax({
					url: URLS.PHOTO_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.C,
						DESCRIPTION: desc,
						QUANTITY: quantity,
						PRICE: price
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								var html='<div class="alert alert-success" role="alert">'+
  										data.MESSAGE+
										'</div>';
									$('#create_photo_master_record_modal_body').prepend(html);
									$('#photo_Master_Record_Create_Success').get(0).play();
									$('div[role="alert"]').delay(10000).fadeOut('slow');
								//alert(data.MESSAGE);
								//$('#create_photo_master_record_modal').modal('hide');
								functions.init();
								$('#create_photo_master_record_form')[0].reset();
							}
						}
					},
					complete: function() {
						hideLoader();
					}
				});
			}
			hideLoader();
		},
		edit: function(event) {
			showLoader();
			if (selectedPhotoID == null || selectedPhotoID == undefined) {
				alert("Photo ID is missing. Refresh the page and try again.");
			}
			else if (PhotoMasterData[selectedPhotoID].STATUS == 'N') {
				alert("Selected Photo Master record is already in deleted status. Hence, it is not possible to edit the record.");
			}
			else {
				//Load data from Javascript session by selectedPhotoID
				var photoRecord = PhotoMasterData[selectedPhotoID];
				$('#photo_master_id').val(photoRecord.PHOTOID);
				$('#photo_master_name').val(photoRecord.DESCRIPTION);
				$('#photo_master_quantity').val(photoRecord.QUANTITY);
				$('#photo_master_price').val(photoRecord.PRICE);
				$('#edit_photo_master_record_modal').modal('show');
			}
			hideLoader();
		},
		update: function(event) {
			showLoader();
			var desc = $('#photo_master_name').val();
			var quantity = $('#photo_master_quantity').val();
			var price = $('#photo_master_price').val();

			if (desc == null || desc == "" || desc == undefined) {
				alert("Name can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else if (quantity == null || quantity == "" || quantity == undefined) {
				alert("Quantity can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else if (price == null || price == "" || price == undefined) {
				alert("Price can not be empty.");
				event.stopPropagation();
				event.preventDefault();
			}
			else {
				if (selectedPhotoID != null && selectedPhotoID != "" && selectedPhotoID != undefined) {
					$.ajax({
						url: URLS.PHOTO_MASTER,
						method: "POST",
						data: {
							MODE: userActionMode.U,
							PHOTOID: selectedPhotoID,
							DESCRIPTION: desc,
							QUANTITY: quantity,
							PRICE: price
						},

						success: function(data) {
							if (data != null) {
								if (data.MESSAGESTATUS == false) {
									alert(data.MESSAGE);
								}
								else {
									alert(data.MESSAGE);
									$('#edit_photo_master_record_modal').modal('hide');
									functions.init();
								}
							}
						},
						complete: function() {
							hideLoader();
						}
					});
				}
				else {
					alert("Photo ID is missing. Refresh the page and try again.");
				}
				hideLoader();
			}

		},
		confirm_delete: function(event) {
			if (PhotoMasterData[selectedPhotoID].STATUS == 'Y') {//Y-Available, N-Deleted
				$('#del_photo_master_id').html(selectedPhotoID);
				$('#delete_photo_master_record_modal').modal('show');
			}
			else {
				alert("Selected Photo Master record is already in deleted status.");
			}
		},
		delete: function(event) {
			showLoader();
			if (selectedPhotoID != null && selectedPhotoID != "" && selectedPhotoID != undefined) {
				$.ajax({
					url: URLS.PHOTO_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.D,
						PHOTOID: selectedPhotoID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#delete_photo_master_record_modal').modal('hide');
								functions.init();
							}
						}
					},
					complete: function() {
						hideLoader();
					}
				});
			}
			else {
				alert("Photo ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		export2Excel: function() {
			let date = new Date().toJSON().slice(0, 10);
			let nDate = date.slice(8, 10) + '_'
				+ date.slice(5, 7) + '_'
				+ date.slice(0, 4);
			$("#photo-master-overview").table2excel({
				filename: "PhotoMasterData" + nDate + ".xls",
				format: ".xls"
			});

		}
	}

	functions.init();//Load intial overview data

	$('body').on('click', 'a[data-type="edit-photo-master-record"]', function(event) {
		selectedPhotoID = $(this).attr('data-attr');
		functions.edit(event);
	});
	$('body').on('click', 'button[data-type="update-photo-master-record"]', function(event) {
		functions.update(event);
	});
	$('body').on('click', 'a[data-type="delete-photo-master-record-int"]', function(event) {
		selectedPhotoID = $(this).attr('data-attr');
		functions.confirm_delete(event);
	});
	$('button[data-type="delete-photo-master-record"]').click(function(event) {
		functions.delete(event);
	});
	$('button[data-type="create-photo-master-record"]').click(function(event) {
		functions.create(event);
	});
	$('body').on('click', 'a[data-type="view-photo-master-record"]', function(event) {
		functions.view($(this).attr('data-attr'));
	});
	$('body').on('click', 'a[data-type="recreate-photo-master-record-int"]', function(event) {
		selectedPhotoID = $(this).attr('data-attr');
		functions.confirm_recreate($(this).attr('data-attr'));
	});
	$('button[data-type="recreate-photo-master-record"]').click(function(event) {
		functions.recreate(event);
	});
	$('button[data-type="export-to-excel"]').click(function() {
		functions.export2Excel();
	});
	$('button[data-type="open_create_photo_master_record_modal"]').click(function(){
		$('#create_photo_master_record_modal').modal('show');
	});
	$('.modal').on('hidden.bs.modal', function() {
		//remove the backdrop
		$('.modal-backdrop').remove();
	});

})