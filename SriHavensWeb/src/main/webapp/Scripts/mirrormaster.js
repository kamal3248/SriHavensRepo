$(document).ready(function() {
	showLoader();

	var MirrorMasterData = [];
	var selectedMirrorID = null;

	var functions = {
		init: function() {
			$.ajax({
				url: URLS.MIRROR_MASTER,
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
							if(!(data.ISADMINUSER)){
								$('button[data-type="open_create_mirror_master_record_modal"]').remove();
							}
							var mirrorMap = data.MIRRORMAP;
							MirrorMasterData = mirrorMap;
							var html = '<td colspan="9" class="txt-center-align danger">No records available to display.</td>';
							var row = 1;
							if (Object.keys(mirrorMap).length) {
								html = "";
							}
							$.each(mirrorMap, function(index, record) {
								html += '<tr>' +
									'<td>' + row + '</td>' +
									'<td>' + record.MIRRORID + '</td>' +
									'<td><a data-type="view-mirror-master-record" href="#" data-attr="' + record.MIRRORID + '">' + record.DESCRIPTION + '</a></td>' +
									'<td>' + record.QUANTITY + '</td>' +
									'<td>' + record.PRICE + '</td>' +
									'<td>' + record.CREATEDON + '</td>' +
									'<td>' + record.CREATEDBY + '</td>' +
									'<td><span class="' + statusIcons[record.STATUS] + '">&bull;</span> ' + entityStatus[record.STATUS] + '</td>';
									if(data.ISADMINUSER){
										if (record.STATUS == 'Y') {
											html += '<td>' +
											'<a data-type="edit-mirror-master-record" href="#" data-attr="' + record.MIRRORID + '" class="edit" title="Edit" data-toggle="tooltip"><i class="fa-solid fa-pen-to-square"></i></a>' +
											'<a data-type="delete-mirror-master-record-int"  href="#" data-attr="' + record.MIRRORID + '" class="delete" title="Delete" data-toggle="tooltip"><i class="fa-solid fa-trash"></i></a>' +
											'</td>';
										}
										else {
											html +='<td><a data-type="recreate-mirror-master-record-int" href="#" data-attr="' + record.MIRRORID + '" class="edit" title="Recreate" ><i class="fa-solid fa-retweet"></i></a></td>';
										}	
									}
									else{
										html+="<td></td>";
									}
									html += '</tr>';
									row++;
							});
						}
						let tableID = "#mirror-master-overview";
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
		confirm_recreate: function(mirrorID) {
			if (MirrorMasterData[mirrorID].STATUS == 'N') {//Y-Available, N-Deleted
				$('#rec_mirror_master_id').html(mirrorID);
				$('#recreate_mirror_master_record_modal').modal('show');
			}
			else {
				alert("Selected Mirror Master record is already in available status.");
			}
		},
		recreate: function(event) {
			showLoader();
			if (selectedMirrorID != null && selectedMirrorID != "" && selectedMirrorID != undefined) {
				$.ajax({
					url: URLS.MIRROR_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.R,
						MIRRORID: selectedMirrorID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#recreate_mirror_master_record_modal').modal('hide');
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
				alert("Mirror ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		view:function(mirrorID){
			var mirrorRecord = MirrorMasterData[mirrorID];
			$('#v_mirror_master_id').html(mirrorRecord.MIRRORID);
			$('#v_mirror_master_name').html(mirrorRecord.DESCRIPTION);
			$('#v_mirror_master_quantity').html(mirrorRecord.QUANTITY);
			$('#v_mirror_master_price').html(mirrorRecord.PRICE);
			$('#v_mirror_master_status').html(entityStatus[mirrorRecord.STATUS]);
			$('#v_mirror_master_createdby').html(mirrorRecord.CREATEDBY);
			$('#v_mirror_master_createdon').html(mirrorRecord.CREATEDON);
			$('#v_mirror_master_updatedby').html(mirrorRecord.UPDATEDBY);
			$('#v_mirror_master_updatedon').html(mirrorRecord.UPDATEDON);
			$('#v_mirror_master_deletedby').html(mirrorRecord.DELETEDBY);
			$('#v_mirror_master_deletedon').html(mirrorRecord.DELETEDON);
			
			$('#view_mirror_master_record_modal').modal('show');
		},
		create: function(event) {
			showLoader();
			var desc = $('#m_master_name').val();
			var quantity = $('#m_master_quantity').val();
			var price = $('#m_master_price').val();

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
					url: URLS.MIRROR_MASTER,
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
									$('#create_mirror_master_record_modal_body').prepend(html);
									$('#mirror_Master_Record_Create_Success').get(0).play();
									$('div[role="alert"]').delay(10000).fadeOut('slow');
								//alert(data.MESSAGE);
								//$('#create_mirror_master_record_modal').modal('hide');
								functions.init();
								$('#create_mirror_master_record_form')[0].reset();
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
		edit: function() {
			showLoader();
			if (selectedMirrorID == null || selectedMirrorID == undefined) {
				alert("Mirror ID is missing. Refresh the page and try again.");
			}
			else if (MirrorMasterData[selectedMirrorID].STATUS == 'N') {
				alert("Selected Mirror Master record is already in deleted status. Hence, it is not possible to edit the record.");
			}
			else {
				//Load data from Javascript session by selectedMirrorID
				var mirrorRecord = MirrorMasterData[selectedMirrorID];
				$('#mirror_master_id').val(mirrorRecord.MIRRORID);
				$('#mirror_master_name').val(mirrorRecord.DESCRIPTION);
				$('#mirror_master_quantity').val(mirrorRecord.QUANTITY);
				$('#mirror_master_price').val(mirrorRecord.PRICE);
				$('#edit_mirror_master_record_modal').modal('show');
			}
			hideLoader();
		},
		update: function(event) {
			showLoader();
			var desc = $('#mirror_master_name').val();
			var quantity = $('#mirror_master_quantity').val();
			var price = $('#mirror_master_price').val();

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
				if (selectedMirrorID != null && selectedMirrorID != "" && selectedMirrorID != undefined) {
					$.ajax({
						url: URLS.MIRROR_MASTER,
						method: "POST",
						data: {
							MODE: userActionMode.U,
							MIRRORID: selectedMirrorID,
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
									$('#edit_mirror_master_record_modal').modal('hide');
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
					alert("Mirror ID is missing. Refresh the page and try again.");
				}
				hideLoader();
			}

		},
		confirm_delete: function(event) {
			if (MirrorMasterData[selectedMirrorID].STATUS == 'Y') {//Y-Available, N-Deleted
				$('#del_mirror_master_id').html(selectedMirrorID);
				$('#delete_mirror_master_record_modal').modal('show');
			}
			else {
				alert("Selected Mirror Master record is already in deleted status.");
			}
		},
		delete: function(event) {
			showLoader();
			if (selectedMirrorID != null && selectedMirrorID != "" && selectedMirrorID != undefined) {
				$.ajax({
					url: URLS.MIRROR_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.D,
						MIRRORID: selectedMirrorID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#delete_mirror_master_record_modal').modal('hide');
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
				alert("Mirror ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		export2Excel: function() {
			let date = new Date().toJSON().slice(0, 10);
			let nDate = date.slice(8, 10) + '_'
						+ date.slice(5, 7) + '_'
						+ date.slice(0, 4);
			$("#mirror-master-overview").table2excel({
                    filename: "MirrorMasterData"+nDate+".xls",
                    format:".xls"
                });
			
		}
	}

	functions.init();//Load intial overview data

	$('body').on('click', 'a[data-type="edit-mirror-master-record"]', function(event) {
		selectedMirrorID = $(this).attr('data-attr');
		functions.edit(event);
	});
	$('body').on('click', 'button[data-type="update-mirror-master-record"]', function(event) {
		functions.update(event);
	});
	$('body').on('click', 'a[data-type="delete-mirror-master-record-int"]', function(event) {
		selectedMirrorID = $(this).attr('data-attr');
		functions.confirm_delete(event);
	});
	$('button[data-type="delete-mirror-master-record"]').click(function(event) {
		functions.delete(event);
	});
	$('button[data-type="create-mirror-master-record"]').click(function(event) {
		functions.create(event);
	});
	$('body').on('click','a[data-type="view-mirror-master-record"]',function(event){
		functions.view($(this).attr('data-attr'));
	});
	$('body').on('click','a[data-type="recreate-mirror-master-record-int"]',function(event){
		selectedMirrorID = $(this).attr('data-attr');
		functions.confirm_recreate($(this).attr('data-attr'));
	});
	$('button[data-type="recreate-mirror-master-record"]').click(function(event) {
		functions.recreate(event);
	});
	$('button[data-type="export-to-excel"]').click(function() {
		functions.export2Excel();
	});
	$('button[data-type="open_create_mirror_master_record_modal"]').click(function(){
		$('#create_mirror_master_record_modal').modal('show');
	});
	$('.modal').on('hidden.bs.modal', function() {
		//remove the backdrop
		$('.modal-backdrop').remove();
	});
})