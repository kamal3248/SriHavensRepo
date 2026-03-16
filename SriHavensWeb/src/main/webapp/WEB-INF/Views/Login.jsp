<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%String dynKey = "Ver="+System.currentTimeMillis();
String requestURI = request.getRequestURI();
%>
<title>SriHavens Apartment - Login</title>
<link rel="shortcut icon"
 href="./LoginPage/Images/favicon.ico?<%=dynKey%>" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<link href="<%=requestURI%>/Styles/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="<%=requestURI%>/Styles/Login.css?<%=dynKey%>">
<link rel="stylesheet"
	href="<%=requestURI%>/Styles/loader.css?<%=dynKey%>">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="<%=requestURI%>/Scripts/jquery-3.6.4.min.js"></script>
<script 
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=requestURI%>/Scripts/loader.js?<%=dynKey%>"></script>
<script type="text/javascript"
	src="<%=requestURI%>/Scripts/Login.js?<%=dynKey%>"></script>
<script type="text/javascript"
	src="<%=requestURI%>/Scripts/forgotPassword.js?<%=dynKey%>"></script>
</head>
<body>
	<!-- Loader  -->
	<jsp:include page="/WEB-INF/Views/Loader.jsp"></jsp:include>
	<!-- Section: Design Block -->
	<section class=" text-center text-lg-start">
		<div class="card mb-3">
			<div class="row g-0 d-flex align-items-center">
				<div class="col-lg-8 d-none d-lg-flex pad-right-10">
					<img
						src="<%=request.getRequestURI() %>/Images/studio_background.jpg"
						alt="Sri Havens Community"
						class="w-100 rounded-t-5 rounded-tr-lg-0 rounded-bl-lg-5" />
				</div>
				<div class="col-lg-4">
					<h1 class="px-md-5">SriHavens Community</h1>
					<div class="card-body py-5 px-md-5">

						<form class="text-lg-start">
							<div class="mb-4">
								<label class="form-label" for="l_logonid_inp">Logon ID</label> <input
									type="text" id="l_logonid_inp" class="form-control" />
							</div>

							<div class="mb-4">
								<label class="form-label" for="l_password_inp">Password</label>
								<input type="password" id="l_password_inp" class="form-control" />
								<span class="password-toggle-icon"><i id="l_show_pswd"
									class="fas fa-eye"></i></span>
							</div>
							<div class="row mb-4">
								<div class="col d-flex justify-content-center">
									<!-- Checkbox -->
									<div class="form-check">
										<input class="form-check-input" type="checkbox" value=""
											id="l_rem_me_chk" /> <label class="form-check-label"
											for="l_rem_me_chk"> Remember me </label>
									</div>
								</div>
								<div class="col">
									<a href="#" data-type="forgot_password">Forgot password?</a>
								</div>
							</div>
							<div id="message-bar" class="message-bar"></div>
							<button type="button" id="l_sign_in_btn"
								class="btn btn-primary mb-4" style="width:100%">Login</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
<jsp:include page="/WEB-INF/Views/ForgotPassword.jsp"></jsp:include>
</body>
</html>