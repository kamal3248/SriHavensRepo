<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Mirror Master Data Administration</title>
<%
String dynKey = "Ver=" + System.currentTimeMillis();
%>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link rel="stylesheet" href="./Styles/tables.css?<%=dynKey%>">
<link rel="stylesheet" href="./Styles/generic.css?<%=dynKey%>">
<script src="./Scripts/jquery-3.6.4.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/rainabba/jquery-table2excel@1.1.0/dist/jquery.table2excel.min.js"> </script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>

<link rel="stylesheet" href="./Styles/loader.css?<%=dynKey%>">
<script type="text/javascript" src="./Scripts/loader.js?<%=dynKey%>"></script>
<link
	href="https://cdn.datatables.net/2.1.2/css/dataTables.dataTables.min.css"
	rel="stylesheet">
<script src="./Scripts/mirrormaster.js?<%=dynKey%>"></script>
</head>
<body>
	<div class="container-xl">
		<div class="table-responsive">
			<div class="table-wrapper">
				<div class="table-title">
					<div class="row">
						<div class="col-sm-5">
							<h2>
								<i class="fa fa-square"></i> Mirror <b>Master Data</b>
							</h2>
						</div>
						<div class="col-sm-7">
							<button class="btn btn-secondary"
								data-type="open_create_mirror_master_record_modal">
								<i class="fa fa-circle-plus"></i> <span>New Mirror Record</span>
							</button>
							<button data-type="export-to-excel" class="btn btn-secondary">
								<i class="fa fa-file-export"></i> <span>Export to Excel</span>
							</button>
						</div>
					</div>
				</div>
				<table id="mirror-master-overview"
					class="table table-striped table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Mirror ID</th>
							<th>Description</th>
							<th>Quantity</th>
							<th>Price(Rs.)</th>
							<th>Created on</th>
							<th>Created by</th>
							<th>Status</th>
							<th>Actions</th>
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
	<jsp:include page="/WEB-INF/Views/MirrorMasterCreation.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/Views/MirrorMasterEdit.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/Views/MirrorMasterDelete.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/Views/MirrorMasterView.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/Views/MirrorMasterRecreate.jsp"></jsp:include>
</body>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
	src="https://cdn.datatables.net/2.1.2/js/dataTables.min.js"></script>
</html>