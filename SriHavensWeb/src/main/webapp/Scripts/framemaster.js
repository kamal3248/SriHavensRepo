$(document).ready(function() {
	showLoader();
	var FrameMasterData = [];
	var selectedFrameID = null;

	var functions = {
		init: function() {
			$.ajax({
				url: URLS.FRAME_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},

				success: function(data) {
					//console.log(data);
					if (data != null && data != undefined) {

						if (data.MESSAGESTATUS == false == 'E') {
							alert(data.message);
						}
						else {
							if(!(data.ISADMINUSER)){
								$('button[data-type="open_create_frame_master_record_modal"]').remove();
							}

							var frameMap = data.FRAMEMAP;
							FrameMasterData = frameMap;
							var html = '<td colspan="9" class="txt-center-align danger">No records available to display.</td>';
							var row = 1;
							if (Object.keys(frameMap).length) {
								html = "";
							}
							$.each(frameMap, function(index, record) {
								html += '<tr>' +
									'<td>' + (row) + '</td>' +
									'<td>' + record.FRAMEID + '</td>' +
									'<td><a  data-type="view-frame-master-record" href="#" data-attr="' + record.FRAMEID + '">' + record.DESCRIPTION + '</a></td>' +
									'<td>' + record.QUANTITY + '</td>' +
									'<td>' + record.PRICE + '</td>' +
									'<td>' + record.CREATEDON + '</td>' +
									'<td>' + record.CREATEDBY + '</td>' +
									'<td><span class="' + statusIcons[record.STATUS] + '">&bull;</span> ' + entityStatus[record.STATUS] + '</td>';
									if(data.ISADMINUSER){
										if (record.STATUS == 'Y') {
											html += '<td>' +
											'<a data-type="edit-frame-master-record" href="#" data-attr="' + record.FRAMEID + '" class="edit" title="Edit" data-toggle="tooltip"><i class="fa-solid fa-pen-to-square"></i></a>' +
											'<a data-type="delete-frame-master-record-int" href="#" data-attr="' + record.FRAMEID + '" class="delete" title="Delete" data-toggle="tooltip"><i class="fa-solid fa-trash"></i></a>' +
											'</td>';
										}
										else {
												html+='<td><a data-type="recreate-frame-master-record-int" href="#" data-attr="' + record.FRAMEID + '" class="edit" title="Recreate" ><i class="fa-solid fa-retweet"></i></a></td>';
										}
									}
									else{
										html+="<td></td>";
									}
									html += '</tr>';
									row++;
							});
						}
						let tableID = "#frame-master-overview";
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
		confirm_recreate: function(frameID) {
			if (FrameMasterData[frameID].STATUS == 'N') {//Y-Available, N-Deleted
				$('#rec_frame_master_id').html(frameID);
				$('#recreate_frame_master_record_modal').modal('show');
			}
			else {
				alert("Selected Frame Master record is already in available status.");
			}
		},
		recreate: function(event) {
			showLoader();
			if (selectedFrameID != null && selectedFrameID != "" && selectedFrameID != undefined) {
				$.ajax({
					url: URLS.FRAME_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.R,
						FRAMEID: selectedFrameID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#recreate_frame_master_record_modal').modal('hide');
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
				alert("Frame ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		view:function(frameID){
			var frameRecord = FrameMasterData[frameID];
			$('#v_frame_master_id').html(frameRecord.FRAMEID);
			$('#v_frame_master_name').html(frameRecord.DESCRIPTION);
			$('#v_frame_master_quantity').html(frameRecord.QUANTITY);
			$('#v_frame_master_price').html(frameRecord.PRICE);
			$('#v_frame_master_status').html(entityStatus[frameRecord.STATUS]);
			$('#v_frame_master_createdby').html(frameRecord.CREATEDBY);
			$('#v_frame_master_createdon').html(frameRecord.CREATEDON);
			$('#v_frame_master_updatedby').html(frameRecord.UPDATEDBY);
			$('#v_frame_master_updatedon').html(frameRecord.UPDATEDON);
			$('#v_frame_master_deletedby').html(frameRecord.DELETEDBY);
			$('#v_frame_master_deletedon').html(frameRecord.DELETEDON);
			
			$('#view_frame_master_record_modal').modal('show');
		},
		create: function(event) {
			showLoader();
			var desc = $('#f_master_name').val();
			var quantity = $('#f_master_quantity').val();
			var price = $('#f_master_price').val();

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
					url: URLS.FRAME_MASTER,
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
								alert(data.MESSAGE);
								//$('#create_frame_master_record_modal').modal('hide');
								functions.init();
								$('#create_frame_master_record_form')[0].reset();
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
			if (selectedFrameID == null || selectedFrameID == undefined) {
				alert("Frame ID is missing. Refresh the page and try again.");
			}
			else if (FrameMasterData[selectedFrameID].STATUS == 'N') {
				alert("Selected Frame Master record is already in deleted status. Hence, it is not possible to edit the record.");
			}
			else {
				//Load data from Javascript session by selectedFrameID
				var frameRecord = FrameMasterData[selectedFrameID];
				$('#frame_master_id').val(frameRecord.FRAMEID);
				$('#frame_master_name').val(frameRecord.DESCRIPTION);
				$('#frame_master_quantity').val(frameRecord.QUANTITY);
				$('#frame_master_price').val(frameRecord.PRICE);
				$('#edit_frame_master_record_modal').modal('show');
			}
			hideLoader();
		},
		update: function(event) {
			showLoader();
			var desc = $('#frame_master_name').val();
			var quantity = $('#frame_master_quantity').val();
			var price = $('#frame_master_price').val();

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
				if (selectedFrameID != null && selectedFrameID != "" && selectedFrameID != undefined) {
					$.ajax({
						url: URLS.FRAME_MASTER,
						method: "POST",
						data: {
							"MODE": userActionMode.U,
							"FRAMEID": selectedFrameID,
							"DESCRIPTION": desc,
							"QUANTITY": quantity,
							"PRICE": price
						},

						success: function(data) {
							if (data != null) {
								if (data.MESSAGESTATUS == false) {
									alert(data.MESSAGE);
								}
								else {
									alert(data.MESSAGE);
									$('#edit_frame_master_record_modal').modal('hide');
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
					alert("Frame ID is missing. Refresh the page and try again.");
				}
				hideLoader();
			}

		},
		confirm_delete: function(event) {
			if (FrameMasterData[selectedFrameID].STATUS == 'Y') {//Y-Available, N-Deleted
				$('#del_frame_master_id').html(selectedFrameID);
				$('#delete_frame_master_record_modal').modal('show');
			}
			else {
				alert("Selected Frame Master record is already in deleted status.");
			}
		},
		delete: function(event) {
			showLoader();
			if (selectedFrameID != null && selectedFrameID != "" && selectedFrameID != undefined) {
				$.ajax({
					url: URLS.FRAME_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.D,
						FRAMEID: selectedFrameID
					},

					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#delete_frame_master_record_modal').modal('hide');
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
				alert("Frame ID is missing. Refresh the page and try again.");
			}
			hideLoader();
		},
		export2Excel: function() {
			let date = new Date().toJSON().slice(0, 10);
			let nDate = date.slice(8, 10) + '_'
						+ date.slice(5, 7) + '_'
						+ date.slice(0, 4);
			$("#frame-master-overview").table2excel({
                    filename: "FrameMasterData"+nDate+".xls",
                    format:".xls"
                });
			
		}
	}

	functions.init();//Load intial overview data

	$('body').on('click', 'a[data-type="edit-frame-master-record"]', function(event) {
		selectedFrameID = $(this).attr('data-attr');
		functions.edit(event);
	});
	$('body').on('click', 'button[data-type="update-frame-master-record"]', function(event) {
		functions.update(event);
	});
	$('body').on('click', 'a[data-type="delete-frame-master-record-int"]', function(event) {
		selectedFrameID = $(this).attr('data-attr');
		functions.confirm_delete(event);
	});
	$('button[data-type="delete-frame-master-record"]').click(function(event) {
		functions.delete(event);
	});
	$('button[data-type="create-frame-master-record"]').click(function(event) {
		functions.create(event);
	});
	$('body').on('click','a[data-type="view-frame-master-record"]',function(){
		functions.view($(this).attr('data-attr'));
	});
	$('body').on('click','a[data-type="recreate-frame-master-record-int"]',function(){
		selectedFrameID = $(this).attr('data-attr');
		functions.confirm_recreate($(this).attr('data-attr'));
	});
	$('button[data-type="recreate-frame-master-record"]').click(function(event) {
		functions.recreate(event);
	});
	$('button[data-type="open_create_frame_master_record_modal"]').click(function(){
		$('#create_frame_master_record_modal').modal('show');
	});
	$('button[data-type="export-to-excel"]').click(function() {
		functions.export2Excel();
	});

})