<%String dynKey = "Ver="+System.currentTimeMillis();
String requestURI = request.getRequestURI();
%>
<div id="forgot_password_modal" data-backdrop='static'
	class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog  modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Forgot Password</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="">
				<form class="mx-1 mx-md-4" id="forgot_password_form">
					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="f_user_id">UserID</label> <input
								type="text" id="f_user_id" class="form-control" />
						</div>
					</div>

					<div class="form-group">
						<div class="form-outline">
							<img id="captcha"></img>&nbsp;&nbsp;
							<button class="btn btn-secondary" title="Refresh Captcha" data-type="refresh_captcha"><i class="fa-solid fa-refresh"></i></button>
						</div>
					</div>
					
					<div class="form-group">
						<div class="form-outline">
							<label class="form-label" for="f_captcha">Captcha</label>
							<input type="text" id="f_captcha" class="form-control" />
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" data-type="verify_captcha" class="btn btn-primary">
					<i class="fa-solid fa-arrow-right-to-bracket"></i> Submit
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<i class="fa-solid fa-times"></i> Close
				</button>
			</div>
		</div>
	</div>
</div>