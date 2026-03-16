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
import com.studio.bean.FrameBean;
import com.studio.bean.FrameResponseBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.ActionModes;
import com.studio.constants.Messages;
import com.studio.dao.FrameMasterDAO;
import com.studio.dao.UserAdminDAO;

/**
 * Servlet implementation class FrameMasterServ
 */
public class FrameMasterServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FrameMasterServ() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mode = request.getParameter("MODE");
		String frameID = request.getParameter("FRAMEID");
		String description = request.getParameter("DESCRIPTION");
		String quantity = request.getParameter("QUANTITY");
		String price = request.getParameter("PRICE");

		// System.out.println(mode+"... "+frameID + "..." + description + "..." +
		// quantity + "..." + price);

		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		BigDecimal price_reference = new BigDecimal(0);
		FrameResponseBean frBean = new FrameResponseBean();
		frBean.setMESSAGESTATUS(false);

		boolean isAdminUser = UserAdminDAO.isAdminUser(loggedInUser);
		frBean.setISADMINUSER(isAdminUser);

		if (mode.equals(ActionModes.OVERVIEW)) {
			TransactionResponse transResponse = FrameMasterDAO
					.LoadFrameMasterRecords((String) request.getSession(false).getAttribute("UID"));
			frBean.setFRAMEMAP(transResponse.getFrameMap());
			frBean.setMESSAGESTATUS(true);
		}

		else if (mode.equals(ActionModes.CREATE)) {
			if (isAdminUser) {
				frBean.setMESSAGE(Messages.FRAME_MASTER_CREATE_FAILED);
				if (description == null || description .equals("")) {
					frBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					frBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					frBean.setMESSAGE(Messages.PRICE_MISSIING);
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
						frBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						frBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						FrameBean fBean = new FrameBean();
						fBean.setCREATEDBY(loggedInUser);
						fBean.setDESCRIPTION(description);
						fBean.setPRICE(i_price);
						fBean.setQUANTITY(i_quantity);
						TransactionResponse transResponse = FrameMasterDAO.createFrameMasterRecrod(fBean);
						if (transResponse.isTransactionStatus()) {
							frBean.setFRAMEID(transResponse.getFrameID());
							frBean.setMESSAGESTATUS(true);
							frBean.setMESSAGE(MessageFormat.format(Messages.FRAME_MASTER_CREATE_SUCCESS,
									transResponse.getFrameID()));
						}
					}
				}

			} else {
				frBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.UPDATE)) {
			if (isAdminUser) {

				frBean.setMESSAGE(Messages.FRAME_MASTER_UPDATE_FAILED);
				if (frameID == null || frameID .equals("")) {
					frBean.setMESSAGE(Messages.FRAME_ID_MISSING);
				} else if (description == null || description .equals("")) {
					frBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					frBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					frBean.setMESSAGE(Messages.PRICE_MISSIING);
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
						frBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						frBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						FrameBean fBean = new FrameBean();
						fBean.setFRAMEID(frameID);
						fBean.setUPDATEDBY(loggedInUser);
						fBean.setDESCRIPTION(description);
						fBean.setPRICE(i_price);
						fBean.setQUANTITY(i_quantity);
						TransactionResponse transResponse = FrameMasterDAO.updateFrameMasterRecrod(fBean);
						if (transResponse.isTransactionStatus()) {
							frBean.setUPDATESTATUS(true);
							frBean.setMESSAGESTATUS(true);
							frBean.setMESSAGE(Messages.FRAME_MASTER_UPDATE_SUCCESS);
						}
					}
				}

			} else {
				frBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.DELETE)) {
			if (isAdminUser) {
				frBean.setMESSAGE(Messages.FRAME_MASTER_DELETE_FAILED);
				if (frameID == null || frameID .equals("")) {
					frBean.setMESSAGE(Messages.FRAME_ID_MISSING);
				} else {
					TransactionResponse transResponse = FrameMasterDAO.deleteFrameMasterRecrod(frameID, loggedInUser);
					if (transResponse.isTransactionStatus()) {
						frBean.setDELETESTATUS(true);
						frBean.setMESSAGESTATUS(true);
						frBean.setMESSAGE(Messages.FRAME_MASTER_DELETE_SUCCESS);
					}
				}
			} else {
				frBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.RECREATE)) {
			if (isAdminUser) {
				frBean.setMESSAGE(Messages.FRAME_MASTER_RECREATE_FAILED);
				if (frameID == null || frameID .equals("")) {
					frBean.setMESSAGE(Messages.FRAME_ID_MISSING);
				} else {
					TransactionResponse transResponse = FrameMasterDAO.recreateFramePrintMasterRecrod(frameID,
							loggedInUser);
					if (transResponse.isTransactionStatus()) {
						frBean.setRECREATESTATUS(true);
						frBean.setMESSAGESTATUS(true);
						frBean.setMESSAGE(Messages.FRAME_MASTER_RECREATE_SUCCESS);
					}
				}
			} else {
				frBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		System.out.println(frBean.getMESSAGESTATUS());
		out.print(new Gson().toJson(frBean));
		out.flush();
	}

}
