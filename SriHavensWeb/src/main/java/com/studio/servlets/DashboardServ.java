package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.studio.bean.Dashboard;
import com.studio.bean.UserDetails;
import com.studio.dao.DashboardDAO;

/**
 * Servlet implementation class DashboardServ
 */
public class DashboardServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServ() {
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
		HttpSession session = request.getSession(false);
		String loggedInUser = (String) session.getAttribute("UID");
		String lastLoginAt = (String) session.getAttribute("LASTLOGINAT");
		UserDetails userDetails = DashboardDAO.loadUserDetails(loggedInUser);
		userDetails.setLASTLOGINAT(lastLoginAt);
		Dashboard dashboard = new Dashboard(userDetails);
		PrintWriter out = response.getWriter();
	    response.setContentType("application/json");
	    out.print(new Gson().toJson(dashboard));
	    out.flush();  
	}

}
