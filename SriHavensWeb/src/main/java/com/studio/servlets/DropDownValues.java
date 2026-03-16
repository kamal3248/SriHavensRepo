package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.studio.bean.TransactionResponse;
import com.studio.dao.FrameMasterDAO;
import com.studio.dao.MirrorMasterDAO;
import com.studio.dao.PhotosMasterDAO;

/**
 * Servlet implementation class DropDownValues
 */
public class DropDownValues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropDownValues() {
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
		String mode = request.getParameter("mode");
		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		TransactionResponse sResponse = new TransactionResponse();
		boolean transStatus = true;
		
		TransactionResponse tResponse = PhotosMasterDAO.LoadPhotosPrintMasterRecords(loggedInUser);
		sResponse.setPhotoMap(tResponse.getPhotoMap());
		transStatus=transStatus&&tResponse.isTransactionStatus();
		
		tResponse = FrameMasterDAO.LoadFrameMasterRecords(loggedInUser);
		sResponse.setFrameMap(tResponse.getFrameMap());
		transStatus=transStatus&&tResponse.isTransactionStatus();
		
		tResponse = MirrorMasterDAO.LoadMirrorMasterRecords(loggedInUser);
		sResponse.setMirrorMap(tResponse.getMirrorMap());
		transStatus=transStatus&&tResponse.isTransactionStatus();
		
		sResponse.setTransactionStatus(transStatus);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(sResponse));
		out.flush();
	}

}
