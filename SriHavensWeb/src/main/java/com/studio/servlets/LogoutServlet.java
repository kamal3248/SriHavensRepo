package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.studio.bean.MessageBean;
import com.studio.constants.MessageStatus;
import com.studio.constants.Messages;
import com.studio.constants.URLS;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(false).setAttribute("uid", null);
		request.getSession(false).invalidate();
		request.getSession(true);
		response.sendRedirect(request.getContextPath()+URLS.LOGIN);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession(false).setAttribute("uid", null);
		request.getSession(false).invalidate();
		request.getSession(true);
		
		MessageBean mBean = new MessageBean(Messages.LOGOFF_SUCCESS ,MessageStatus.SUCCESS, request.getContextPath()+URLS.LOGIN);
		PrintWriter out = response.getWriter();
	    response.setContentType("application/json");
	    out.print(new Gson().toJson(mBean));
	    out.flush();  
		
	}

}
