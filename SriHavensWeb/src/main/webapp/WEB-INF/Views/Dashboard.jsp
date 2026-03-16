<%String dynKey = "Ver="+System.currentTimeMillis(); %>
<script src="./Scripts/dashboard.js?<%=dynKey%>"></script>
<div class="container-fluid">
	<div class="welcome-container">
		<h1>
			Welcome <span id="welcome-container"></span>
		</h1>
	</div>
</div>
<div class="row">
	<div class="col-lg-5">
		<div class="card mb-4">
			<div class="card-header">Personal Information</div>
			<div class="card-body">
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">UserID</p>
					</div>
					<div class="col-sm-9">
						<span id="userid" class="text-muted mb-0"></span>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">Name</p>
					</div>
					<div class="col-sm-9">
						<span id="name" class="text-muted mb-0"></span>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">Email</p>
					</div>
					<div class="col-sm-9">
						<span id="email" class="text-muted mb-0"></span>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">Mobile</p>
					</div>
					<div class="col-sm-9">
						<span id="mobile" class="text-muted mb-0"></span>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">Last Login At</p>
					</div>
					<div class="col-sm-9">
						<span id="last_login_at" class="text-muted mb-0"></span>
					</div>
				</div>
				<hr>
				<div class="row">
					<div class="col-sm-3">
						<p class="mb-0">Role</p>
					</div>
					<div class="col-sm-9">
						<span id="role" class="text-muted mb-0"></span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>