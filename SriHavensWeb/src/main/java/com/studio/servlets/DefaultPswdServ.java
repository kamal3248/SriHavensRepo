package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.studio.bean.MessageBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.Messages;
import com.studio.constants.URLS;
import com.studio.dao.UserAdminDAO;
import com.studio.validator.Validator;

/**
 * Servlet implementation class DefaultPswdServ
 */
public class DefaultPswdServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DefaultPswdServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		String userID = (String) session.getAttribute("UID");
		String password = (String) request.getParameter("PASSWORD");
		String rPassword = (String) request.getParameter("REPEAT_PASSWORD");
		MessageBean mBean = new MessageBean();
		mBean.setStatus("N");
		String message = null;
		if (Validator.isStringEmpty(userID)) {
			message = Messages.USERID_MISSING;
		} else if (!Validator.isUserIDValid(userID)) {
			message = Messages.USERID_INVALID;
		} else if (Validator.isStringEmpty(password)) {
			message = Messages.PASSWORD_MISSING;
		} else if (!Validator.isPasswordValid(password)) {
			message = Messages.PASSWORD_INVALID;
		} else if (Validator.isStringEmpty(rPassword)) {
			message = Messages.CONFIRM_PASSWORD_MISSING;
		} else if (!password.equals(rPassword)) {
			message = Messages.PASSWORD_NOT_MATCHING;
		} else {
			TransactionResponse tResponse = UserAdminDAO.changePassword(userID, password, userID);
			if (tResponse.isTransactionStatus()) {
				session.removeAttribute("ISPSWDDEFAULT");
				mBean.setStatus("Y");
				message = tResponse.getMessage();
				mBean.setRedirectURL(request.getContextPath()+URLS.HOME);
			}
		}
		mBean.setMessage(message);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(mBean));
		out.flush();
	}

}
