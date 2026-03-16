$(document).ready(function() {
	var UserMasterData = {};
	var selectedUserRecord = null;

	var statusIcons = {
		Y: "status text-success",
		L: "status text-warning",
		D: "status text-danger"
	};

	var icons = {
		Y: "fa-solid fa-lock",
		L: "fa-solid fa-unlock"
	};

	var lockActions = {
		Y: "LOCK",
		L: "UNLOCK"
	}

	var userStatus = {
		Y: "Active",
		L: "Locked",
		D: "Deleted"
	};

	var elements = {
		ViewModal: {
			USERID: "#v_user_id",
			NAME: "#v_user_name",
			EMAIL: "#v_user_email",
			ROLE: "#v_user_role",
			MOBILE: "#v_mobile_number",
			LAST_LOGIN: "#v_last_login",
			ACTIVE: "#v_user_active",
			STATUS: "#v_user_status",
			CREATED_BY: "#v_created_by",
			CREATED_ON: "#v_created_on",
			UPDATED_BY: "#v_updated_by",
			UPDATED_ON: "#v_updated_on",
			DELETED_BY: "#v_deleted_by",
			DELETED_ON: "#v_deleted_on",
			PSWD_CHANGED_BY: "#v_pswd_changed_by",
			PSWD_CHANGED_ON: "#v_pswd_changed_on"
		},
		CreateModal: {
			USERID: "#u_user_id",
			NAME: "#u_user_name",
			EMAIL: "#u_user_email",
			ROLE: "#u_user_role",
			MOBILE: "#u_mobile_number",
			PASSWORD: "#u_pswd",
			REPEAT_PASSWORD: "#u_rep_pswd"
		},
		EditModal: {
			USERID: "#e_user_id",
			NAME: "#e_user_name",
			EMAIL: "#e_user_email",
			ROLE: "#e_user_role",
			MOBILE: "#e_mobile_number",
			PASSWORD: "#e_pswd",
			REPEAT_PASSWORD: "#e_rep_pswd"
		},
		DeleteModal: {
			USERID: "#d_user_id",
			NAME: "#d_user_name"
		},
		RecreateModal: {
			USERID: "#r_user_id",
			NAME: "#r_user_name"
		},
		PasswordModal: {
			NAME: "#p_user_name",
			PASSWORD: "#p_pswd",
			REPEAT_PASSWORD: "#p_rep_pswd"
		},
		LockNUnlockModal: {
			USERID: "#l_user_id",
			ACTION: "span[data-type='l_action']",
			NAME: "#l_user_name"
		}
	}

	showLoader();

	var functions = {
		init: function() {
			$.ajax({
				url: URLS.USER_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.O
				},
				success: function(data) {
					console.log(data);
					if (data != null && data != undefined) {

						if (data.MESSAGESTATUS == false) {
							alert(data.MESSAGE);
						}
						else {
							//add roles details from DB to all Role dropdowns
							var roleDD = $('select[data-type="user-roles"');
							var options = "";
							$.each(data.ROLEMAP, function(index, record) {
								options += '<option value=' + index + '>' + record + '</option>';
							});
							$(roleDD).html(options);

							if (!(data.ISADMINUSER)) {
								$('#admin-controls').remove();
							}
							var userMap = data.USERMAP;
							UserMasterData = userMap;
							var html = '<td colspan="9" class="txt-center-align danger">No records available to display.</td>';
							var row = 1;
							if (Object.keys(userMap).length) {
								html = "";
							}
							$.each(userMap, function(index, record) {
								html += '<tr>' +
									'<td>' + (row) + '</td>' +
									'<td>' + record.UID + '</td>' +
									'<td><a data-type="view-mirror-master-record" href="#" data-attr="' + record.UID + '"><i class="fa-solid fa-user"></i>&nbsp;' + record.NAME + '</a></td>' +
									'<td>' + record.CREATEDON + '</td>' +
									'<td>' + record.CREATEDBY + '</td>';
									if(record.LASTLOGINAT!=undefined){
										html+='<td>' + record.LASTLOGINAT + '</td>';
									}
									else{
										html+='<td>Not Yet Logged In</td>';
									}
								html+= '<td>' + record.ROLENAME + '</td>' +
									'<td><span class="' + statusIcons[record.STATUS] + '">&bull;</span> ' + userStatus[record.STATUS] + '</td>' +
									'<td class="pad-0">';
								if (data.ISADMINUSER) {
									if (record.STATUS == 'Y') {
										html += '<a data-type="edit-user-master-record" href="#" data-attr="' + record.UID + '" class="edit" title="Edit" data-toggle="tooltip"><i class="fa-solid fa-pen-to-square icon-sm"></i></a>' +
											'<a data-type="pswd-reset-user-master-record-int" href="#" data-attr="' + record.UID + '" class="" title="Change Password" data-toggle="tooltip"><img class="icon-sm" src="./Images/reset-password.png"></i></a>';
								
									}
									else if (record.STATUS == 'D') {
										html += '<a data-type="recreate-user-master-record-int" href="#" data-attr="' + record.UID + '" class="edit" title="Recreate" ><i class="fa-solid fa-retweet icon-sm"></i></a></td>';
									}
									html += '<a data-type="locknunlock-user-master-record-int" href="#" data-action="' + lockActions[record.STATUS] + '" data-attr="' + record.UID + '" class="" title="' + lockActions[record.STATUS] + '" data-toggle="tooltip"><i class="icon-sm ' + icons[record.STATUS] + '"></i></a>'+
									'<a data-type="delete-user-master-record-int"  href="#" data-attr="' + record.UID + '" class="delete" title="Delete" data-toggle="tooltip"><i class="fa-solid fa-trash icon-sm"></i></a>';
								}
								html += '</td></tr>';
								row++;
							});
						}
						let tableID = "#user-master-table";
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
			})
		},
		view: function(userID) {
			var userRecord = UserMasterData[userID];
			$(elements.ViewModal.USERID).html(userRecord.UID);
			$(elements.ViewModal.NAME).html(userRecord.NAME);
			$(elements.ViewModal.EMAIL).html(userRecord.EMAIL);
			$(elements.ViewModal.ROLE).html(userRecord.ROLENAME);
			$(elements.ViewModal.MOBILE).html(userRecord.CONTACT);
			let lastLoginAt = userRecord.LASTLOGINAT;
			if(lastLoginAt==undefined){
				lastLoginAt="Not Yet logged in";
			}
			$(elements.ViewModal.LAST_LOGIN).html(lastLoginAt);
			$(elements.ViewModal.STATUS).html(userStatus[userRecord.STATUS]);
			$(elements.ViewModal.CREATED_BY).html(userRecord.CREATEDBY);
			$(elements.ViewModal.CREATED_ON).html(userRecord.CREATEDON);
			$(elements.ViewModal.UPDATED_BY).html(userRecord.UPDATEDBY);
			$(elements.ViewModal.UPDATED_ON).html(userRecord.UPDATEDON);
			$(elements.ViewModal.DELETED_BY).html(userRecord.DELETEDBY);
			$(elements.ViewModal.DELETED_ON).html(userRecord.DELETEDON);
			$(elements.ViewModal.PSWD_CHANGED_BY).html(userRecord.PSWDCHANGEDBY);
			$(elements.ViewModal.PSWD_CHANGED_ON).html(userRecord.PSWDCHANGEDON);

			$('#view_user_master_record_modal').modal('show');
		},
		edit: function(userID) {
			var userRecord = UserMasterData[userID];
			if (userRecord != null && userRecord != undefined && userRecord.STATUS=="Y") {
				selectedUserRecord = userRecord;
				$(elements.EditModal.USERID).val(userRecord.UID);
				$(elements.EditModal.NAME).val(userRecord.NAME);
				$(elements.EditModal.EMAIL).val(userRecord.EMAIL);
				$(elements.EditModal.ROLE).val(userRecord.ROLE);
				$(elements.EditModal.MOBILE).val(userRecord.CONTACT);
				$('#edit_user_master_record_modal').modal('show');
			}
			else {
				alert("No details found/Not possible to perform edit.");
			}
		},
		create: function() {
			$.ajax({
				url: URLS.USER_MASTER,
				method: "POST",
				data: {
					MODE: userActionMode.C,
					USERID: $(elements.CreateModal.USERID).val(),
					NAME: $(elements.CreateModal.NAME).val(),
					EMAIL: $(elements.CreateModal.EMAIL).val(),
					ROLE: $(elements.CreateModal.ROLE).val(),
					MOBILE: $(elements.CreateModal.MOBILE).val(),
					PASSWORD: $(elements.CreateModal.PASSWORD).val(),
					REPEAT_PASSWORD: $(elements.CreateModal.REPEAT_PASSWORD).val()
				},
				success: function(data) {
					console.log($(elements.CreateModal.USERID).val()+$(elements.CreateModal.NAME).val()+$(elements.CreateModal.EMAIL).val()+
					$(elements.CreateModal.ROLE).val()+$(elements.CreateModal.PASSWORD).val()+$(elements.CreateModal.REPEAT_PASSWORD).val());
					if (data != null) {
						if (data.MESSAGESTATUS == false) {
							alert(data.MESSAGE);
						}
						else {
							alert(data.MESSAGE);
							$('#create_user_master_record_modal').modal('hide');
							$('#create_user_master_record_form')[0].reset();
							functions.init();
						}
					}
				},
				complete: function() {
					hideLoader();
				}
			});
		},
		update: function() {

			if (selectedUserRecord != null && selectedUserRecord != undefined && selectedUserRecord.STATUS=="Y") {
				$.ajax({
					url: URLS.USER_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.U,
						USERID: selectedUserRecord.UID,
						NAME: $(elements.EditModal.NAME).val(),
						EMAIL: $(elements.EditModal.EMAIL).val(),
						ROLE: $(elements.EditModal.ROLE).val(),
						MOBILE: $(elements.EditModal.MOBILE).val(),
						PASSWORD: $(elements.EditModal.PASSWORD).val(),
						REPEAT_PASSWORD: $(elements.EditModal.REPEAT_PASSWORD).val()
					},
					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#edit_user_master_record_modal').modal('hide');
								$('#update_user_master_record_form')[0].reset();
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
				alert("No details found/Not possible to perform update.");
			}

		},
		consent_delete: function(userID) {
			var userRecord = UserMasterData[userID];
			if (userRecord != null && userRecord != undefined && userRecord.STATUS!="D") {
				selectedUserRecord = userRecord;
				$(elements.DeleteModal.USERID).html(userRecord.UID);
				$(elements.DeleteModal.NAME).html(userRecord.NAME);
				$('#delete_user_master_record_modal').modal('show');
			}
			else {
				alert("No details found/Not possible to perform delete.");
			}
		},
		delete: function() {
			if (selectedUserRecord != null && selectedUserRecord != undefined  && selectedUserRecord.STATUS!="D") {
				$.ajax({
					url: URLS.USER_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.D,
						USERID: selectedUserRecord.UID

					},
					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#delete_user_master_record_modal').modal('hide');
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
				alert("No details found/Not possible to perform delete.");
			}
		},
		consent_LockNUnlock: function(userID, action) {
			var userRecord = UserMasterData[userID];
			if (userRecord != null && userRecord != undefined && userRecord.STATUS=="Y") {
				selectedUserRecord = userRecord;
				window.selectedAction = action;
				$(elements.LockNUnlockModal.USERID).html(userRecord.UID);
				$(elements.LockNUnlockModal.NAME).html(userRecord.NAME);
				$(elements.LockNUnlockModal.ACTION).html(action);
				$('#locknunlock_user_master_record_modal').modal('show');
			}
			else {
				alert("No details found/ Not possible to perform " + action + " .");
			}
		},
		lockNUnlock: function() {
			if (selectedUserRecord != null && selectedUserRecord != undefined && selectedUserRecord.STATUS=="Y") {
				$.ajax({
					url: URLS.USER_MASTER,
					method: "POST",
					data: {
						MODE: window.selectedAction,
						USERID: selectedUserRecord.UID

					},
					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#locknunlock_user_master_record_modal').modal('hide');
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
				alert("No details found / Not possible to perform lock or unlock.");
			}
		},
		consent_Password_Change: function(userID) {
			var userRecord = UserMasterData[userID];
			if (userRecord != null && userRecord != undefined && userRecord.STATUS=="Y") {
				selectedUserRecord = userRecord;
				$(elements.PasswordModal.NAME).html(userRecord.NAME);
				$('#pswd_reset_user_master_record_modal').modal('show');
			}
			else {
				alert("No details found / Not possible to perform " + action + " .");
			}
		},
		password_Change: function() {
			if (selectedUserRecord != null && selectedUserRecord != undefined && selectedUserRecord.STATUS=="Y") {
				$.ajax({
					url: URLS.USER_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.P,
						USERID: selectedUserRecord.UID,
						PASSWORD: $(elements.PasswordModal.PASSWORD).val(),
						REPEAT_PASSWORD: $(elements.PasswordModal.REPEAT_PASSWORD).val()
					},
					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#pswd_reset_user_master_record_modal').modal('hide');
								$('#pswd-reset-user-master-record-form')[0].reset();
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
				alert("No details found to perform password change./Password change not possible.");
			}
		},
		consent_recreate:function(userID){
			var userRecord = UserMasterData[userID];
			if (userRecord != null && userRecord != undefined) {
				if(userRecord.STATUS!="D"){
					alert("Recreate not possible");
				}
				else{
					selectedUserRecord = userRecord;
					$(elements.RecreateModal.NAME).html(userRecord.NAME);
					$(elements.RecreateModal.USERID).html(userRecord.UID);
				$('#recreate_user_master_record_modal').modal('show');
				}
			}
			else {
				alert("No details found to perform recreate.");
			}
		},
		recreate:function(){
			if (selectedUserRecord != null && selectedUserRecord != undefined && selectedUserRecord.STATUS=="D") {
				$.ajax({
					url: URLS.USER_MASTER,
					method: "POST",
					data: {
						MODE: userActionMode.R,
						USERID: selectedUserRecord.UID
					},
					success: function(data) {
						if (data != null) {
							if (data.MESSAGESTATUS == false) {
								alert(data.MESSAGE);
							}
							else {
								alert(data.MESSAGE);
								$('#recreate_user_master_record_modal').modal('hide');
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
				alert("No details found/Not possible to perform User Recreate.");
			}
		}
	}
	functions.init();
	
	$('button[data-type="create-user-master-record"]').click(function() {
		functions.create();
	});
	$('body').on('click', 'a[data-type="view-mirror-master-record"]', function() {
		functions.view($(this).attr('data-attr'));
	});
	$('body').on('click', 'a[data-type="edit-user-master-record"]', function() {
		functions.edit($(this).attr('data-attr'));
	});
	$('button[data-type="update-user-master-record"]').click(function() {
		functions.update();
	});
	$('body').on('click', 'a[data-type="delete-user-master-record-int"]', function() {
		functions.consent_delete($(this).attr('data-attr'));
	});
	$('button[data-type="delete-user-master-record"]').click(function() {
		functions.delete();
	});
	$('body').on('click', 'a[data-type="recreate-user-master-record-int"]', function() {
		functions.consent_recreate($(this).attr('data-attr'));
	});
	$('button[data-type="recreate-user-master-record"]').click(function() {
		functions.recreate();
	});
	$('body').on('click', 'a[data-type="locknunlock-user-master-record-int"]', function() {
		functions.consent_LockNUnlock($(this).attr('data-attr'), $(this).attr('data-action'));
	});
	$('button[data-type="locknunlock-user-master-record"]').click(function() {
		functions.lockNUnlock();
	});
	$('body').on('click', 'a[data-type="pswd-reset-user-master-record-int"]', function() {
		functions.consent_Password_Change($(this).attr('data-attr'));
	});
	$('button[data-type="pswd-reset-user-master-record"]').click(function() {
		functions.password_Change();
	});
});