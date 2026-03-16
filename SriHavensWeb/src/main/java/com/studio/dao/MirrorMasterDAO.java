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
import com.studio.bean.MirrorBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.Queries;

public class MirrorMasterDAO {
	
	public static TransactionResponse recreateMirrorPrintMasterRecrod(String mirrorID, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			boolean transStatus = false;
			con = DBHelper.getConnection();
			String query = Queries.RECREATE_MIRROR_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, loggedInUser);
			pst.setTimestamp(2, new Timestamp(new Date().getTime()));
			pst.setString(3, mirrorID);
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
	public static TransactionResponse LoadMirrorMasterRecords(String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		SortedMap<String, MirrorBean> mMap = new TreeMap<>();
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.LOAD_ALL_MIRROR_MASTER_DATA;
				pst = con.prepareStatement(query);
				rs = pst.executeQuery();
				while(rs.next()) {
							String mirrorID = rs.getString("MIRRORID");
							String status="Y";//Default in available status
							Timestamp deletedOn = rs.getTimestamp("DELETEDON");
							String deletedBy = rs.getString("DELETEDBY");
							if(deletedBy!=null && deletedOn!=null) {
								status="N";//Deleted status
							}
							MirrorBean record = new MirrorBean(mirrorID,
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
							mMap.put(mirrorID, record);
						}
				transResponse.setMirrorMap(mMap);
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
	
	public static TransactionResponse createMirrorMasterRecrod(MirrorBean mBean) {
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		String MirrorID = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.INSERT_NEW_MIRROR_RECORD;
				pst = con.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, mBean.getDESCRIPTION());
				pst.setInt(2, mBean.getQUANTITY());
				pst.setBigDecimal(3, mBean.getPRICE());
				pst.setString(4, mBean.getCREATEDBY());
				pst.execute();
				rs = pst.getGeneratedKeys();
				if(rs.next()) {
						pst1 = con.prepareStatement(Queries.FETCH_MIRRORID);
						pst1.setInt(1, rs.getInt(1));
						rs1 = pst1.executeQuery();
						if(rs1.next()) {
							MirrorID = rs1.getString("MIRRORID");
						}
						}
				con.commit();
				transResponse.setMirrorID(MirrorID);
				transResponse.setTransactionStatus(true);
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
	public static TransactionResponse deleteMirrorMasterRecrod(String mirrorID, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean status = false;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.DELETE_MIRROR_RECORD;
				pst = con.prepareStatement(query);
				pst.setTimestamp(1,new Timestamp(new java.util.Date().getTime()));
				pst.setString(2, loggedInUser);
				pst.setString(3, mirrorID);
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
			//ucBean.setMessage(message);
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
	
	public static TransactionResponse updateMirrorMasterRecrod(MirrorBean mBean) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean status = false;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try 
		{
			con = DBHelper.getConnection();
				String query=Queries.UPDATE_MIRROR_RECORD;
				pst = con.prepareStatement(query);
				pst.setString(1,mBean.getDESCRIPTION());
				pst.setInt(2, mBean.getQUANTITY());
				pst.setBigDecimal(3, mBean.getPRICE());
				pst.setString(4, mBean.getUPDATEDBY());
				pst.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
				pst.setString(6, mBean.getMIRRORID());
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
			//ucBean.setMessage(message);
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
