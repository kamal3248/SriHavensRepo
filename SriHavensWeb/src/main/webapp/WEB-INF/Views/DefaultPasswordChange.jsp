<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%
String dynKey = "Ver=" + System.currentTimeMillis();
%>
<title>RK Portrait Studio - Change Default Password</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Load font awesome icons -->
<link rel="shortcut icon"
	href="./LoginPage/Images/favicon.ico?<%=dynKey%>" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css"
	rel="stylesheet">
<link href="./Styles/loader.css?<%=dynKey%>" rel="stylesheet">
<link href="./Styles/home.css?<%=dynKey%>" rel="stylesheet">
<link href="./Styles/generic.css?<%=dynKey%>" rel="stylesheet">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="./Styles/tables.css?<%=dynKey%>">
<script src="./Scripts/jquery-3.6.4.min.js"></script>
<script src="./Scripts/generic.js?<%=dynKey%>"></script>
<script src="./Scripts/loader.js?<%=dynKey%>"></script>
<script src="./Scripts/defaultPasswordChange.js?<%=dynKey%>"></script>
</head>
<body>
	<!-- Loader  -->
	<jsp:include page="/WEB-INF/Views/Loader.jsp"></jsp:include>
	<div class="wrapper ">
		<div class="sidebar" data-color="purple" data-background-color="white"
			data-image="./Images/sidebar-1.jpg">
			<div class="logo">
				<a href="./Home" class="simple-text logo-normal"> <i
					class="fa fa-camera"></i> RK DIGITAL STUDIO
				</a>
			</div>
			<div class="sidebar-wrapper ps-theme-default ps-active-y">
				<!-- <ul data-type="nav" class="nav">
					<li class="nav-item active " data-title="Dashboard"
						data-view="DASHBOARD"><a class="nav-link" href="#"> <i
							class="fas fa-chart-line"></i>
							<p>Dashboard</p>
					</a></li>
					<li class="nav-item " data-title="User Administration"
						data-view="USERADMIN"><a class="nav-link" href="#"> <i
							class="fa fa-user"></i>
							<p>User Administration</p>
					</a></li>
					<li class="nav-item " data-title="Bills" data-view="BILLS"><a
						class="nav-link" href="#"> <i class="fas fa-file-invoice"></i>
							<p>Bills</p>
					</a></li>
					<li class="nav-item " data-title="Photos Print Master"
						data-view="PHOTOS_MASTER"><a class="nav-link" href="#"> <i
							class="fa fa-images"></i>
							<p>Photos Print Master</p>
					</a></li>
					<li class="nav-item " data-title="Frame Master"
						data-view="FRAME_MASTER"><a class="nav-link" href="#"> <i
							class="fa fa-clone"></i>
							<p>Frame Master</p>
					</a></li>
					<li class="nav-item " data-title="Mirror Master"
						data-view="MIRROR_MASTER"><a class="nav-link" href="#"> <i
							class="fa fa-square"></i>
							<p>Mirror Master</p>
					</a></li>

				</ul> -->
			</div>
			<div class="sidebar-background"
				style="background-image: url(./Images/sidebar-1.jpg)"></div>
		</div>

		<div class="main-panel ps-theme-default">
			<nav
				class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top box-shadow">
				<div class="container-fluid">
					<div class="navbar-wrapper">
						<a class="navbar-brand" href="#"><p data-type='data-title'>Change default password</p></a>
					</div>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						aria-controls="navigation-index" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="sr-only">Toggle navigation</span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span> <span
							class="navbar-toggler-icon icon-bar"></span>
					</button>
					<div class="collapse navbar-collapse justify-content-end">

						<ul class="navbar-nav">
							<li class="nav-item dropdown"><a class="nav-link"
								href="javascript:;" id="navbarDropdownProfile"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false"> <i class="fas fa-user"></i>
							</a>
								<div class="dropdown-menu dropdown-menu-right"
									aria-labelledby="navbarDropdownProfile">
									<a class="dropdown-item" href="#">Profile</a> <a
										class="dropdown-item" href="#">Settings</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" id="logout" href="#">Log out</a>
								</div></li>
						</ul>
					</div>
				</div>
			</nav>
			<div class="content">
				<section class="w-100 p-4" id="content-area"
					style="background-color: #eee; border-radius: .5rem .5rem 0 0;">
					<form id="default-pswd_change-form" class="col-sm-6">
						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="d_pswd">Password</label> <input
									type="password" id="d_pswd" class="form-control" /> <span
									class="password-toggle-icon"><i
									data-type="password_toggle" data-target="#d_pswd"
									class="fas fa-eye"></i></span>
							</div>
						</div>

						<div class="form-group">
							<div class="form-outline flex-fill mb-0">
								<label class="form-label" for="p_rep_pswd">Repeat your
									password</label> <input type="password" id="d_rep_pswd"
									class="form-control" /> <span class="password-toggle-icon"><i
									data-type="password_toggle" data-target="#d_rep_pswd"
									class="fas fa-eye"></i></span>
							</div>
						</div>
						<button data-type="default-pswd-change" type="button"
						class="btn btn-primary"><i class="fa fa-arrow-right"></i> Submit</button>
					</form>
				</section>
			</div>
			<footer class="container">
				<div class="row">
					<div class="col-sm-6">
						<h6 class="bold">Useful Links</h6>
						<a target="_blank" href="./anonymous/PasswordPolicy.jsp">Password
							Policy</a><br> <a target="_blank" href="./anonymous/UserIDPolicy.jsp">UserID
							Policy</a>
					</div>
					<div class="col-sm-6">
						<h6 class="bold">Credits</h6>
						<a target="_blank"
							href="https://www.flaticon.com/free-icons/reset-password"
							title="reset password icons">Reset password icons created by
							Freepik - Flaticon</a>

					</div>
				</div>
			</footer>
		</div>
	</div>
</body>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</html>