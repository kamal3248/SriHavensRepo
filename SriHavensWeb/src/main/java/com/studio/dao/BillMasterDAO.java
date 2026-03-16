package com.studio.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import com.studio.backend.DBHelper;
import com.studio.bean.BillBean;
import com.studio.bean.ItemBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.Queries;

public class BillMasterDAO {
	public static TransactionResponse updateDelivery(String loggedInUser, String billID, Timestamp delDate) {
		TransactionResponse transResponse = new TransactionResponse();
		return transResponse;
	}
	public static TransactionResponse createABill(String loggedInUser, String cName, String cContact, String cEmail, String[] iArr, BigDecimal discount,BigDecimal totalPrice, BigDecimal advance, String payment, String delivery, Timestamp delDate) {
		Connection con = null;
		ResultSet rs = null, rs1=null;
		PreparedStatement pst = null, pst1=null, pst2=null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			boolean transStatus = false;
			con = DBHelper.getConnection();
			String query=Queries.INSERT_NEW_BILL;
			pst = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, cName);
			pst.setString(2, cContact);
			pst.setString(3, cEmail);
			pst.setBigDecimal(4, discount);
			pst.setBigDecimal(5, totalPrice);
			pst.setBigDecimal(6, advance);
			pst.setTimestamp(7, delDate);
			pst.setString(8, delivery);//Delivered in case of Instant else it will be scheduled
			pst.setString(9, loggedInUser);
			pst.setTimestamp(10, new Timestamp(new java.util.Date().getTime()));
			System.out.println(pst);
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			if(rs.next()) {
					pst1 = con.prepareStatement(Queries.FETCH_BILLID);
					pst1.setInt(1, rs.getInt(1));
					rs1 = pst1.executeQuery();
					if(rs1.next()) {
						String billID = rs1.getString("BILLID");
						pst2 = con.prepareStatement(Queries.INSERT_NEW_ITEM);
						con.setAutoCommit(false);
						for(int i=0;i<iArr.length;i++) {
							pst2.setString(1, iArr[i]);
							pst2.setString(2, billID);
							pst2.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
							pst2.setString(4, loggedInUser);
							pst2.addBatch();
						}
						int[] count = pst2.executeBatch();
						if(count.length>0) {
							transStatus = true;
							transResponse.setBillID(billID);
						}
					}
					}
			con.commit();
			transResponse.setTransactionStatus(transStatus);
		} catch (Exception e) {
			e.printStackTrace();
			// ucBean.setMessage(message);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (pst1 != null) {
					pst1.close();
				}
				if (pst2 != null) {
					pst2.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (rs1 != null) {
					rs1.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return transResponse;
	}
	public static TransactionResponse loadBillsOverview() {
		SortedMap<String, BillBean> BillMap = new TreeMap<>();
		TransactionResponse transResponse = new TransactionResponse();
		Connection con = null;
		ResultSet rs=null,rs1 = null;
		PreparedStatement pst=null,pst1 = null;
		transResponse.setTransactionStatus(false);
		try {
			boolean transStatus = false;
			con = DBHelper.getConnection();
			String query=Queries.LOAD_BILLS_OVERVIEW;
			pst = con.prepareStatement(query);
			System.out.println(pst);
			rs = pst.executeQuery();
			while(rs.next()) {
				BillBean b = new BillBean();
				b.setBILLID(rs.getString("BILLID"));
				b.setCREATEDBY(rs.getString("CREATEDBY"));
				b.setCREATEDON(rs.getTimestamp("CREATEDON"));
				b.setPRICE(rs.getBigDecimal("FINAL_BILL_PRICE"));
				b.setDELIVERYSTATUS(rs.getString("DELIVERY_STATUS"));
				b.setDELIVERYDATE(rs.getTimestamp("DELIVERY_DATE"));
				pst1 = con.prepareStatement(Queries.LOAD_ITEMS_OVERVIEW);
				pst1.setString(1, rs.getString("BILLID"));
				rs1=pst1.executeQuery();
				ArrayList<ItemBean> itemList = new ArrayList<>();
				while(rs1.next()) {
					if (rs1.getString("ITEMID").startsWith("PH")) {
						itemList.add(new ItemBean(rs1.getString(28), rs1.getString(29), rs1.getBigDecimal(31), rs1.getInt(30)));
					} else if (rs1.getString("ITEMID").startsWith("FR")) {
						itemList.add(new ItemBean(rs1.getString(6), rs1.getString(7), rs1.getBigDecimal(9), rs1.getInt(8)));
					} else if (rs1.getString("ITEMID").startsWith("MR")) {
						itemList.add(new ItemBean(rs1.getString(17), rs1.getString(18), rs1.getBigDecimal(20), rs1.getInt(19)));
					}
				}
				b.setITEMS(itemList);
				BillMap.put(rs.getString("BILLID"), b);
			}
			transResponse.setBillMAP(BillMap);
			transResponse.setTransactionStatus(transStatus);
		} catch (Exception e) {
			e.printStackTrace();
			// ucBean.setMessage(message);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (pst1 != null) {
					pst1.close();
				}
				if (rs1 != null) {
					rs1.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return transResponse;
	}
}
