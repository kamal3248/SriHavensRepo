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
import com.studio.bean.MirrorBean;
import com.studio.bean.MirrorResponseBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.ActionModes;
import com.studio.constants.Messages;
import com.studio.dao.MirrorMasterDAO;
import com.studio.dao.UserAdminDAO;

/**
 * Servlet implementation class MirrorMasterServ
 */
public class MirrorMasterServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MirrorMasterServ() {
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
		String mirrorID = request.getParameter("MIRRORID");
		String description = request.getParameter("DESCRIPTION");
		String quantity = request.getParameter("QUANTITY");
		String price = request.getParameter("PRICE");
		String loggedInUser = (String) request.getSession(false).getAttribute("UID");
		BigDecimal price_reference = new BigDecimal(0);
		MirrorResponseBean mrBean = new MirrorResponseBean();
		mrBean.setMESSAGESTATUS(false);

		boolean isAdminUser = UserAdminDAO.isAdminUser(loggedInUser);
		mrBean.setISADMINUSER(isAdminUser);

		if (mode.equals(ActionModes.OVERVIEW)) {
			TransactionResponse transResponse = MirrorMasterDAO
					.LoadMirrorMasterRecords((String) request.getSession(false).getAttribute("UID"));
			mrBean.setMIRRORMAP(transResponse.getMirrorMap());
			mrBean.setMESSAGESTATUS(true);

		} else if (mode.equals(ActionModes.CREATE)) {
			if (isAdminUser) {
				mrBean.setMESSAGE(Messages.MIRROR_MASTER_CREATE_FAILED);
				if (description == null || description .equals("")) {
					mrBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					mrBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					mrBean.setMESSAGE(Messages.PRICE_MISSIING);
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
						mrBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						mrBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						MirrorBean mBean = new MirrorBean();
						mBean.setCREATEDBY(loggedInUser);
						mBean.setDESCRIPTION(description);
						mBean.setPRICE(new BigDecimal(price));
						mBean.setQUANTITY(Integer.parseInt(quantity));
						TransactionResponse transResponse = MirrorMasterDAO.createMirrorMasterRecrod(mBean);
						if (transResponse.isTransactionStatus()) {
							mrBean.setMIRRORID(transResponse.getMirrorID());
							mrBean.setMESSAGESTATUS(true);
							mrBean.setMESSAGE(MessageFormat.format(Messages.MIRROR_MASTER_CREATE_SUCCESS,
									transResponse.getMirrorID()));
						}
					}
				}
			} else {
				mrBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.UPDATE)) {
			if (isAdminUser) {

				mrBean.setMESSAGE(Messages.MIRROR_MASTER_UPDATE_FAILED);
				if (mirrorID == null || mirrorID .equals("")) {
					mrBean.setMESSAGE(Messages.MIRROR_ID_MISSING);
				} else if (description == null || description .equals("")) {
					mrBean.setMESSAGE(Messages.DESC_MISSING);
				} else if (quantity == null || quantity .equals("")) {
					mrBean.setMESSAGE(Messages.QUANTITY_MISSING);
				} else if (price == null || price .equals("")) {
					mrBean.setMESSAGE(Messages.PRICE_MISSIING);
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
						mrBean.setMESSAGE(Messages.QUANTITY_INVALID);
					} else if (!isPriceBigDecimal || i_price.compareTo(price_reference) != 1) {
						mrBean.setMESSAGE(Messages.PRICE_INVALID);
					} else {
						MirrorBean mBean = new MirrorBean();
						mBean.setMIRRORID(mirrorID);
						mBean.setCREATEDBY(loggedInUser);
						mBean.setDESCRIPTION(description);
						mBean.setPRICE(i_price);
						mBean.setQUANTITY(i_quantity);
						TransactionResponse transactionResponse = MirrorMasterDAO.updateMirrorMasterRecrod(mBean);
						if (transactionResponse.isTransactionStatus()) {
							mrBean.setUPDATESTATUS(true);
							mrBean.setMESSAGESTATUS(true);
							mrBean.setMESSAGE(Messages.MIRROR_MASTER_UPDATE_SUCCESS);
						}
					}
				}

			} else {
				mrBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		} else if (mode.equals(ActionModes.DELETE)) {
			if (isAdminUser) {
				mrBean.setMESSAGE(Messages.MIRROR_MASTER_DELETE_FAILED);
				if (mirrorID == null || mirrorID .equals("")) {
					mrBean.setMESSAGE(Messages.MIRROR_ID_MISSING);
				} else {
					TransactionResponse transactionResponse = MirrorMasterDAO.deleteMirrorMasterRecrod(mirrorID,
							loggedInUser);
					if (transactionResponse.isTransactionStatus()) {
						mrBean.setDELETESTATUS(true);
						mrBean.setMESSAGESTATUS(true);
						mrBean.setMESSAGE(Messages.MIRROR_MASTER_DELETE_SUCCESS);
					}
				}
			} else {
				mrBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}

		} else if (mode.equals(ActionModes.RECREATE)) {
			if (isAdminUser) {
				mrBean.setMESSAGE(Messages.MIRROR_MASTER_RECREATE_FAILED);
				if (mirrorID == null || mirrorID .equals("")) {
					mrBean.setMESSAGE(Messages.MIRROR_ID_MISSING);
				} else {
					TransactionResponse transResponse = MirrorMasterDAO.recreateMirrorPrintMasterRecrod(mirrorID,
							loggedInUser);
					if (transResponse.isTransactionStatus()) {
						mrBean.setRECREATESTATUS(true);
						mrBean.setMESSAGESTATUS(true);
						mrBean.setMESSAGE(Messages.MIRROR_MASTER_RECREATE_SUCCESS);
					}
				}
			} else {
				mrBean.setMESSAGE(Messages.NOT_ADMIN_USER);

			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.print(new Gson().toJson(mrBean));
		out.flush();
	}

}
