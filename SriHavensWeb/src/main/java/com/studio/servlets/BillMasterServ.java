package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.studio.bean.BillBean;
import com.studio.bean.BillResponseBean;
import com.studio.bean.FrameBean;
import com.studio.bean.MirrorBean;
import com.studio.bean.PhotosBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.ActionModes;
import com.studio.constants.Messages;
import com.studio.dao.BillMasterDAO;
import com.studio.dao.FrameMasterDAO;
import com.studio.dao.MirrorMasterDAO;
import com.studio.dao.PhotosMasterDAO;
import com.studio.validator.Validator;

/**
 * Servlet implementation class BillMasterServ
 */
@WebServlet("/BillMasterServ")
public class BillMasterServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillMasterServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendError(503, "Not supported");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("MODE");
		String cName = request.getParameter("NAME");
		String cContact = request.getParameter("CONTACT");
		String cEmail = request.getParameter("EMAIL");
		String items = request.getParameter("ITEMS");
		String discount = request.getParameter("DISCOUNT");
		String advance = request.getParameter("ADVANCE");
		String payment = request.getParameter("PAYMENT");
		String delivery = request.getParameter("DELIVERY");
		String delDate = request.getParameter("DELDATE");

		Gson gson = new Gson();
		String[] iArr = gson.fromJson(items, String[].class);

//		for(int i=0;i<iArr.length;i++) {
//			System.out.println(iArr[i]);
//		}
//		
		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		BillResponseBean brBean = new BillResponseBean();
		brBean.setMESSAGESTATUS(false);
		// Bill overview table
		if (mode != null && mode != "" && mode.equals(ActionModes.OVERVIEW)) {
			TransactionResponse tResponse = BillMasterDAO.loadBillsOverview();
			brBean.setMESSAGESTATUS(tResponse.isTransactionStatus());
			brBean.setBILLMAP(tResponse.getBillMAP());
		}
		// create bill method
		if (mode != null && mode != "" && mode.equals("C")) {
			if (Validator.isStringEmpty(cName)) {
				brBean.setMESSAGE(Messages.CNAME_MISSING);
			} else if (Validator.isStringEmpty(cContact)) {
				brBean.setMESSAGE(Messages.MOBILE_NO_MISSING);
			} else if (!Validator.isMobileNumberValid(cContact)) {
				brBean.setMESSAGE(Messages.MOBILE_NO_INVALID);
			} else if (Validator.isStringEmpty(cEmail)) {
				brBean.setMESSAGE(Messages.EMAIL_MISSING);
			} else if (!Validator.isEmailValid(cEmail)) {
				brBean.setMESSAGE(Messages.EMAIL_INVALID);
			} else if (iArr != null && iArr.length <= 0) {
				brBean.setMESSAGE(Messages.EMPTY_ITEMS);
			} else if (!Validator.isStringEmpty(discount) && !Validator.isValidFloat(discount)) {
				brBean.setMESSAGE(Messages.DISCOUNT_INVALID);
			} else if (Validator.isStringEmpty(advance)) {
				brBean.setMESSAGE(Messages.EMPTY_ADVANCE);
			} else if (!Validator.isValidFloat(advance)) {
				brBean.setMESSAGE(Messages.ADVANCE_INVALID);
			} else if (Validator.isStringEmpty(payment)) {
				brBean.setMESSAGE(Messages.PAYMENT_MISSING);
			} else if (Validator.isStringEmpty(delivery)) {
				brBean.setMESSAGE(Messages.DEVLIVERY_MISSING);
			} else if (delivery.equals("S")) {
				if (Validator.isStringEmpty(delDate)) {
					brBean.setMESSAGE(Messages.DEVLIVERY_DATE_MISSING);
				}
			} else {
				Timestamp deliveryDate = new Timestamp(new java.util.Date().getTime());
				if (delivery.equals("S")) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date parsedDate;
					try {
						parsedDate = dateFormat.parse(delDate);
						// Convert java.util.Date to java.sql.Timestamp
						deliveryDate = new Timestamp(parsedDate.getTime());
					} catch (java.text.ParseException e) {
						e.printStackTrace();
					}
				}
				BigDecimal b_discount = new BigDecimal(discount);
				BigDecimal b_advance = new BigDecimal(advance);
				BigDecimal b_totalPrice = new BigDecimal(0);
				SortedMap<String, PhotosBean> photoMAP = PhotosMasterDAO.LoadPhotosPrintMasterRecords(loggedInUser)
						.getPhotoMap();
				SortedMap<String, FrameBean> frameMAP = FrameMasterDAO.LoadFrameMasterRecords(loggedInUser)
						.getFrameMap();
				SortedMap<String, MirrorBean> mirrorMAP = MirrorMasterDAO.LoadMirrorMasterRecords(loggedInUser)
						.getMirrorMap();

				for (int i = 0; i < iArr.length; i++) {
					String item = iArr[i];
					if (item.startsWith("PH")) {
						b_totalPrice = b_totalPrice.add(new BigDecimal(String.valueOf(photoMAP.get(item).getPRICE())));
					} else if (item.startsWith("FR")) {
						b_totalPrice = b_totalPrice.add(new BigDecimal(String.valueOf(frameMAP.get(item).getPRICE())));
					} else if (item.startsWith("MR")) {
						b_totalPrice = b_totalPrice.add(new BigDecimal(String.valueOf(mirrorMAP.get(item).getPRICE())));
					}

				}
				
				if(BigDecimal.ZERO.compareTo(b_totalPrice)==-1 ) {
					if(delivery.equals("I")&& b_totalPrice.compareTo(b_advance.add(b_discount))!=0)
					{
						brBean.setMESSAGE("");
					}
					else {
						TransactionResponse tResponse = BillMasterDAO.createABill(loggedInUser, cName, cContact, cEmail, iArr,
								b_discount, b_totalPrice, b_advance, payment, delivery, deliveryDate);
						brBean.setMESSAGE(tResponse.getMessage());
						brBean.setCREATESTATUS(tResponse.isTransactionStatus());
						brBean.setMESSAGESTATUS(tResponse.isTransactionStatus());
						brBean.setBILLID(tResponse.getBillID());
					}
				}
				else {
					brBean.setMESSAGE("Invalid total price");
				}
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(brBean));
		out.flush();
	}
}
