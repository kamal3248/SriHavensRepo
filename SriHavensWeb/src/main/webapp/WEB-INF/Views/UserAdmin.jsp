<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>User Administration Overview</title>
<%String dynKey = "Ver="+System.currentTimeMillis(); %>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" href="./Styles/tables.css?<%=dynKey%>">
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<link
	href="https://cdn.datatables.net/2.1.2/css/dataTables.dataTables.min.css"
	rel="stylesheet">
<script src="./Scripts/useradmin.js?<%=dynKey%>"></script>
</head>
<body>
	<div class="container-xl">
		<div class="table-responsive">
			<div class="table-wrapper">
				<div class="table-title">
					<div class="row">
						<div class="col-sm-5">
							<h2>
								<i class="fa fa-user"></i> User <b>Administration</b>
							</h2>
						</div>
						<div class="col-sm-7" id="admin-controls">
							<button data-toggle="modal"
								data-target="#create_user_master_record_modal" class="btn btn-secondary"><i
								class="fa fa-circle-plus"></i> <span>Add New User</span></button>
						</div>
					</div>
				</div>
				<table id="user-master-table" class="table table-striped table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>UserID</th>
							<th>Name</th>
							<th>Date Created</th>
							<th>Created By</th>
							<th>Last Login At</th>
							<th>Role</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody id="u_ovr_tbl_body">
						<tr>
							<td colspan="9" class="txt-center-align danger bold">No
								records available to display.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/Views/UserMasterCreation.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterEdit.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterDelete.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterRecreate.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterView.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterLockNUnlock.jsp"></jsp:include>
<jsp:include page="/WEB-INF/Views/UserMasterPasswordReset.jsp"></jsp:include>
</body>
<script type="text/javascript"
	src="https://cdn.datatables.net/2.1.2/js/dataTables.min.js"></script>
</html>