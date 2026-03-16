package com.studio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import com.studio.backend.DBHelper;
import com.studio.bean.FrameBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.Queries;

public class FrameMasterDAO {
	public static TransactionResponse recreateFramePrintMasterRecrod(String frameID, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			boolean transStatus = false;
			con = DBHelper.getConnection();
			String query = Queries.RECREATE_FRAME_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, loggedInUser);
			pst.setTimestamp(2, new Timestamp(new Date().getTime()));
			pst.setString(3, frameID);
			System.out.println(pst);
			int exStatus = pst.executeUpdate();
			if (exStatus == 1) {
				transStatus = true;
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
				if (rs != null) {
					rs.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return transResponse;
	}
	public static TransactionResponse LoadFrameMasterRecords(String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			SortedMap<String, FrameBean> fMap = new TreeMap<>();
			con = DBHelper.getConnection();
				String query=Queries.LOAD_ALL_FRAME_MASTER_DATA;
				pst = con.prepareStatement(query);
				rs = pst.executeQuery();
				while(rs.next()) {
							String frameID = rs.getString("FRAMEID");
							String status="Y";//Default in available status
							Timestamp deletedOn = rs.getTimestamp("DELETEDON");
							String deletedBy = rs.getString("DELETEDBY");
							if(deletedBy!=null && deletedOn!=null) {
								status="N";//Deleted status
							}
							FrameBean record = new FrameBean(frameID,
									rs.getString("DESCRIPTION"),
									rs.getInt("QUANTITY"),
									rs.getBigDecimal("PRICE"),
									rs.getString("CREATEDBY"), 
									rs.getTimestamp("CREATEDON"),
									rs.getString("UPDATEDBY"), 
									rs.getTimestamp("UPDATEDON"),
									deletedBy,
									deletedOn,
									status);
							fMap.put(frameID,record);
						}
				transResponse.setFrameMap(fMap);
				transResponse.setTransactionStatus(true);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				if(pst!=null) {
					pst.close();
				}
				if(rs!=null) {
					rs.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return transResponse;
	}
	
	public static TransactionResponse createFrameMasterRecrod(FrameBean fBean) {
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String FrameID = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.INSERT_NEW_FRAME_RECORD;
				pst = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, fBean.getDESCRIPTION());
				pst.setInt(2, fBean.getQUANTITY());
				pst.setBigDecimal(3, fBean.getPRICE());
				pst.setString(4, fBean.getCREATEDBY());
				pst.executeUpdate();
				rs = pst.getGeneratedKeys();
				if(rs.next()) {
						pst1 = con.prepareStatement(Queries.FETCH_FRAMEID);
						pst1.setInt(1, rs.getInt(1));
						rs1 = pst1.executeQuery();
						if(rs1.next()) {
							FrameID = rs1.getString("FRAMEID");
						}
						}
				con.commit();
				transResponse.setFrameID(FrameID);
				transResponse.setTransactionStatus(true);
		}
		catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if(pst!=null) {
					pst.close();
				}
				if(rs!=null) {
					rs.close();
				}
				if(pst1!=null) {
					pst1.close();
				}
				if(rs1!=null) {
					rs1.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return transResponse;
	}
	
	public static TransactionResponse deleteFrameMasterRecrod(String frameID, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean status = false;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.DELETE_FRAME_RECORD;
				pst = con.prepareStatement(query);
				pst.setTimestamp(1,new Timestamp(new java.util.Date().getTime()));
				pst.setString(2, loggedInUser);
				pst.setString(3, frameID);
				int count = pst.executeUpdate();
				if(count==1) {
					status=true;
				}
			con.commit();
			transResponse.setTransactionStatus(status);
		}
		catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if(pst!=null) {
					pst.close();
				}
				if(rs!=null) {
					rs.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return transResponse;
	}
	
	public static TransactionResponse updateFrameMasterRecrod(FrameBean fBean) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean status = false;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.UPDATE_FRAME_RECORD;
				pst = con.prepareStatement(query);
				pst.setString(1,fBean.getDESCRIPTION());
				pst.setInt(2, fBean.getQUANTITY());
				pst.setBigDecimal(3, fBean.getPRICE());
				pst.setString(4, fBean.getUPDATEDBY());
				pst.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
				pst.setString(6, fBean.getFRAMEID());
				int count = pst.executeUpdate();
				if(count==1) {
					status=true;
				}
				con.commit();
				transResponse.setTransactionStatus(status);
		}
		catch(Exception e){
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				if(pst!=null) {
					pst.close();
				}
				if(rs!=null) {
					rs.close();
				}
				DBHelper.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return transResponse;
	}
}
