package com.studio.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.studio.bean.PhotoMasterResponseBean;
import com.studio.bean.PhotosBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.ActionModes;
import com.studio.constants.Messages;
import com.studio.dao.PhotosMasterDAO;
import com.studio.dao.UserAdminDAO;

/**
 * Servlet implementation class PhotosMasterServ
 */
public class PhotosMasterServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PhotosMasterServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("MODE");
		String photoID = request.getParameter("PHOTOID");
		String description = request.getParameter("DESCRIPTION");
		String quantity = request.getParameter("QUANTITY");
		String price = request.getParameter("PRICE");
		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		BigDecimal price_reference = new BigDecimal(0);
		PhotoMasterResponseBean pmrBean = new PhotoMasterResponseBean();
		pmrBean.setMESSAGESTATUS(false);
		boolean isAdminUser = UserAdminDAO.isAdminUser(loggedInUser);
		pmrBean.setISADMINUSER(isAdminUser);
		
		if (mode.equals(ActionModes.OVERVIEW)) {
			TransactionResponse transResponse = PhotosMasterDAO.LoadPhotosPrintMasterRecords(loggedInUser);
			pmrBean.setPHOTOSMAP(transResponse.getPhotoMap());
			pmrBean.setMESSAGESTATUS(transResponse.isTransactionStatus());
		} else if (mode.equals(ActionModes.CREATE)) {
			if (isAdminUser) {
				pmrBean.setMESSAGE(Messages.PHOTO_MASTER_CREATE_FAILED);
				if (description == null || description .equals("")) {
					pmrBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					pmrBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					pmrBean.setMESSAGE(Messages.PRICE_MISSIING);
				} else {
					int i_quantity = 0;
					BigDecimal i_price = null;
					boolean isQuantityInteger = false;
					boolean isPriceBigDecimal = false;
					try {
						i_quantity = Integer.parseInt(quantity);
						isQuantityInteger = true;
						i_price = new BigDecimal(price);
						isPriceBigDecimal = true;
					} catch (NumberFormatException e) {
						// do nothing
					}
					if (!isQuantityInteger || i_quantity <= 0) {
						pmrBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						pmrBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						PhotosBean pBean = new PhotosBean();
						pBean.setCREATEDBY(loggedInUser);
						pBean.setDESCRIPTION(description);
						pBean.setPRICE(new BigDecimal(price));
						pBean.setQUANTITY(Integer.parseInt(quantity));
						TransactionResponse transResponse = PhotosMasterDAO.createPhotoPrintMasterRecrod(pBean);
						if(transResponse.isTransactionStatus()) {
							pmrBean.setPHOTOID(transResponse.getPhotoID());
							pmrBean.setMESSAGESTATUS(true);
							pmrBean.setMESSAGE(MessageFormat.format(Messages.PHOTO_MASTER_CREATE_SUCCESS, transResponse.getPhotoID()));
						}
					}
				}

			} else {
				pmrBean.setMESSAGE(Messages.NOT_ADMIN_USER);
				
			}
		} else if (mode.equals(ActionModes.UPDATE)) {
			if (isAdminUser) {

				pmrBean.setMESSAGE(Messages.PHOTO_MASTER_UPDATE_FAILED);
				if (photoID == null || photoID .equals("")) {
					pmrBean.setMESSAGE(Messages.PHOTO_ID_MISSING);
				} else if (description == null || description .equals("")) {
					pmrBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					pmrBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					pmrBean.setMESSAGE(Messages.PRICE_MISSIING);
				} else {
					int i_quantity = 0;
					BigDecimal i_price = null;
					boolean isQuantityInteger = false;
					boolean isPriceBigDecimal = false;
					try {
						i_quantity = Integer.parseInt(quantity);
						isQuantityInteger = true;
						i_price = new BigDecimal(price);
						isPriceBigDecimal = true;
					} catch (NumberFormatException e) {
						// do nothing
					}
					if (!isQuantityInteger || i_quantity <= 0) {
						pmrBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						pmrBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						PhotosBean pBean = new PhotosBean();
						pBean.setPHOTOID(photoID);
						pBean.setCREATEDBY(loggedInUser);
						pBean.setDESCRIPTION(description);
						pBean.setPRICE(new BigDecimal(price));
						pBean.setQUANTITY(Integer.parseInt(quantity));
						TransactionResponse transResponse = PhotosMasterDAO.updatePhotoMasterRecrod(pBean);
						if(transResponse.isTransactionStatus()) {
							pmrBean.setUPDATESTATUS(true);
							pmrBean.setMESSAGE(Messages.PHOTO_MASTER_UPDATE_SUCCESS);
							pmrBean.setMESSAGESTATUS(true);
						}
					}
				}
			} else {
				pmrBean.setMESSAGE(Messages.NOT_ADMIN_USER);
				
			}
		} else if (mode.equals(ActionModes.DELETE)) {
			if (isAdminUser) {
				pmrBean.setMESSAGE(Messages.PHOTO_MASTER_DELETE_FAILED);
				if (photoID == null || photoID .equals("")) {
					pmrBean.setMESSAGE(Messages.PHOTO_ID_MISSING);
				} else {
					TransactionResponse transResponse = PhotosMasterDAO.deletePhotosPrintMasterRecrod(photoID, loggedInUser);
					if(transResponse.isTransactionStatus()) {
						pmrBean.setDELETESTATUS(true);
						pmrBean.setMESSAGESTATUS(true);
						pmrBean.setMESSAGE(Messages.PHOTO_MASTER_DELETE_SUCCESS);
					}
				}
			} else {
				pmrBean.setMESSAGE(Messages.NOT_ADMIN_USER);
				
			}
		}
		else if (mode.equals(ActionModes.RECREATE)) {
			if (isAdminUser) {
				pmrBean.setMESSAGE(Messages.PHOTO_MASTER_RECREATE_FAILED);
				if (photoID == null || photoID .equals("")) {
					pmrBean.setMESSAGE(Messages.PHOTO_ID_MISSING);
				} else {
					TransactionResponse transResponse = PhotosMasterDAO.recreatePhotosPrintMasterRecrod(photoID, loggedInUser);
					if(transResponse.isTransactionStatus()) {
						pmrBean.setRECREATESTATUS(true);
						pmrBean.setMESSAGESTATUS(true);
						pmrBean.setMESSAGE(Messages.PHOTO_MASTER_RECREATE_SUCCESS);
					}
				}
			} else {
				pmrBean.setMESSAGE(Messages.NOT_ADMIN_USER);
				
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(pmrBean));
		out.flush();
	}
}
