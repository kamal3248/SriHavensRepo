package com.studio.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import com.studio.bean.UserAuthBean;
import com.studio.constants.URLS;
import com.studio.dao.LogonHandlerDao;

/**
 * Servlet implementation class LogonServlet
 */
public class LogonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LogonServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		String password = request.getParameter("pswd");
//		System.out.println(uid + password);
		UserAuthBean auth = LogonHandlerDao.isUserAuthorized(uid, password);
//		System.out.print("auth"+auth.isUserAuthorized());
		if(auth.isUserAuthorized()) {
			HttpSession httpSession = request.getSession(); 
			httpSession.setMaxInactiveInterval(30*60);
			httpSession.setAttribute("UID", uid);
			httpSession.setAttribute("LASTLOGINAT", auth.getLastLoginAt());
			httpSession.setAttribute("ISPSWDDEFAULT", auth.isPSWDDefault());
			auth.setRedirectURL(request.getContextPath()+URLS.HOME);
		}
		 PrintWriter out = response.getWriter();
	     response.setContentType("application/json");
	     out.print(new Gson().toJson(auth));
	     out.flush();  
	}

}
