package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.studio.bean.TransactionResponse;
import com.studio.bean.UserAdminOverviewResponse;
import com.studio.bean.UserDetails;
import com.studio.constants.ActionModes;
import com.studio.constants.Messages;
import com.studio.dao.UserAdminDAO;
import com.studio.validator.Validator;

/**
 * Servlet implementation class UserAdminOverview
 */
public class UserAdminServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserAdminServ() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(405, "Method not allowed");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserAdminOverviewResponse uResponse = new UserAdminOverviewResponse();
		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		boolean isAdminUser = UserAdminDAO.isAdminUser(loggedInUser);
		uResponse.setISADMINUSER(isAdminUser);
		uResponse.setMESSAGESTATUS(false);

		String mode = (String) request.getParameter("MODE");
		String userID = (String) request.getParameter("USERID");
		String name = (String) request.getParameter("NAME");
		String email = (String) request.getParameter("EMAIL");
		String mobile = (String) request.getParameter("MOBILE");
		String password = (String) request.getParameter("PASSWORD");
		String rPassword = (String) request.getParameter("REPEAT_PASSWORD");
		String role = (String) request.getParameter("ROLE");

		if (mode.equals(ActionModes.OVERVIEW)) {
			TransactionResponse transResponse = UserAdminDAO.loadUsersForOverview();
			uResponse.setUSERMAP(transResponse.getUserMap());
			uResponse.setROLEMAP(UserAdminDAO.loadAllRoles());
			uResponse.setMESSAGESTATUS(true);
		} else if (mode.equals(ActionModes.CREATE)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.USER_MASTER_CREATE_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else if (Validator.isStringEmpty(name)) {
					uResponse.setMESSAGE(Messages.NAME_MISSING);
				} else if (Validator.isStringValidWithOutNumNSpaceNSpl(name)) {
					uResponse.setMESSAGE(Messages.NAME_INVALID);
				} else if (Validator.isStringEmpty(email)) {
					uResponse.setMESSAGE(Messages.EMAIL_MISSING);
				} else if (!Validator.isEmailValid(email)) {
					uResponse.setMESSAGE(Messages.EMAIL_INVALID);
				} else if (Validator.isStringEmpty(mobile)) {
					uResponse.setMESSAGE(Messages.MOBILE_NO_MISSING);
				} else if (!Validator.isMobileNumberValid(mobile)) {
					uResponse.setMESSAGE(Messages.MOBILE_NO_INVALID);
				} else if (Validator.isStringEmpty(password)) {
					uResponse.setMESSAGE(Messages.PASSWORD_MISSING);
				} else if (!Validator.isPasswordValid(password)) {
					uResponse.setMESSAGE(Messages.PASSWORD_INVALID);
				} else if (Validator.isStringEmpty(rPassword)) {
					uResponse.setMESSAGE(Messages.CONFIRM_PASSWORD_MISSING);
				} else if (!password.equals(rPassword)) {
					uResponse.setMESSAGE(Messages.PASSWORD_NOT_MATCHING);
				} else if (Validator.isStringEmpty(role)) {
					uResponse.setMESSAGE(Messages.ROLE_MISSING);
				} else if (Validator.isStringValidWithOutSpaceNSpl(role)) {
					uResponse.setMESSAGE(Messages.ROLE_INVALID);
				} else {
					UserDetails userDetails = new UserDetails();
					userDetails.setUID(userID);
					userDetails.setNAME(name);
					userDetails.setEMAIL(email);
					userDetails.setMOBILE(mobile);
					userDetails.setROLE(role);
					userDetails.setPASSWORD(password);
					userDetails.setCREATEDBY(loggedInUser);
					TransactionResponse tResponse = UserAdminDAO.createUserMasterRecord(userDetails);
					if (tResponse.isTransactionStatus()) {
						uResponse.setCREATESTATUS(true);
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);
			}
		} else if (mode.equals(ActionModes.UPDATE)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.USER_MASTER_UPDATE_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else if (Validator.isStringEmpty(name)) {
					uResponse.setMESSAGE(Messages.NAME_MISSING);
				} else if (Validator.isStringValidWithOutNumNSpaceNSpl(name)) {
					uResponse.setMESSAGE(Messages.NAME_INVALID);
				} else if (Validator.isStringEmpty(email)) {
					uResponse.setMESSAGE(Messages.EMAIL_MISSING);
				} else if (!Validator.isEmailValid(email)) {
					uResponse.setMESSAGE(Messages.EMAIL_INVALID);
				} else if (Validator.isStringEmpty(mobile)) {
					uResponse.setMESSAGE(Messages.MOBILE_NO_MISSING);
				} else if (!Validator.isMobileNumberValid(mobile)) {
					uResponse.setMESSAGE(Messages.MOBILE_NO_INVALID);
				} else if (Validator.isStringEmpty(role)) {
					uResponse.setMESSAGE(Messages.ROLE_MISSING);
				} else if (Validator.isStringValidWithOutSpaceNSpl(role)) {
					uResponse.setMESSAGE(Messages.ROLE_INVALID);
				}

				else if (!Validator.isStringEmpty(password) && !Validator.isPasswordValid(password)) {
					uResponse.setMESSAGE(Messages.PASSWORD_INVALID);
				} else if (!Validator.isStringEmpty(password) && Validator.isStringEmpty(rPassword)) {
					uResponse.setMESSAGE(Messages.CONFIRM_PASSWORD_MISSING);
				} else if (!Validator.isStringEmpty(password) && !password.equals(rPassword)) {
					uResponse.setMESSAGE(Messages.PASSWORD_NOT_MATCHING);
				} else {
					UserDetails userDetails = new UserDetails();
					userDetails.setUID(userID);
					userDetails.setNAME(name);
					userDetails.setEMAIL(email);
					userDetails.setMOBILE(mobile);
					userDetails.setROLE(role);
					userDetails.setPASSWORD(password);
					userDetails.setUPDATEDBY(loggedInUser);
					TransactionResponse tResponse = UserAdminDAO.updateUserMasterRecord(userDetails);
					if (tResponse.isTransactionStatus()) {
						uResponse.setUPDATESTATUS(true);
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);
			}
		} else if (mode.equals(ActionModes.DELETE)) {
			if (isAdminUser) {
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else {
					TransactionResponse tResponse = UserAdminDAO.deleteUserMasterRecord(userID, loggedInUser);
					if (tResponse.isTransactionStatus()) {
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);
			}
		} else if (mode.equals(ActionModes.RECREATE)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.USER_MASTER_RECREATE_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else {
					TransactionResponse tResponse = UserAdminDAO.recreateUserMasterRecord(userID, loggedInUser);
					if (tResponse.isTransactionStatus()) {
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);
			}
		} else if (mode.equals(ActionModes.LOCK)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.USER_MASTER_LOCK_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else {
					TransactionResponse tResponse = UserAdminDAO.updateUserMasterStatusRecord(userID, "L",
							loggedInUser);
					if (tResponse.isTransactionStatus()) {
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);
			}
		} else if (mode.equals(ActionModes.UNLOCK)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.USER_MASTER_UNLOCK_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else {
					TransactionResponse tResponse = UserAdminDAO.updateUserMasterStatusRecord(userID, "Y",
							loggedInUser);
					if (tResponse.isTransactionStatus()) {
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.PSWD_CHANGE)) {
			if (isAdminUser) {
				uResponse.setMESSAGE(Messages.CHANGE_PASSWORD_FAILED);
				if (Validator.isStringEmpty(userID)) {
					uResponse.setMESSAGE(Messages.USERID_MISSING);
				} else if (!Validator.isUserIDValid(userID)) {
					uResponse.setMESSAGE(Messages.USERID_INVALID);
				} else if (Validator.isStringEmpty(password)) {
					uResponse.setMESSAGE(Messages.PASSWORD_MISSING);
				} else if (!Validator.isPasswordValid(password)) {
					uResponse.setMESSAGE(Messages.PASSWORD_INVALID);
				} else if (Validator.isStringEmpty(rPassword)) {
					uResponse.setMESSAGE(Messages.CONFIRM_PASSWORD_MISSING);
				} else if (!password.equals(rPassword)) {
					uResponse.setMESSAGE(Messages.PASSWORD_NOT_MATCHING);
				} else {
					TransactionResponse tResponse = UserAdminDAO.changePassword(userID, password, loggedInUser);
					if (tResponse.isTransactionStatus()) {
						uResponse.setMESSAGESTATUS(true);
						uResponse.setMESSAGE(tResponse.getMessage());
					}
				}
			} else {
				uResponse.setMESSAGE(com.studio.constants.Messages.NOT_ADMIN_USER);

			}
		}

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(uResponse));
		out.flush();
	}

}
