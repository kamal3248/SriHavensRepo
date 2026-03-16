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
import com.studio.bean.PhotosBean;
import com.studio.bean.TransactionResponse;
import com.studio.constants.Queries;

public class PhotosMasterDAO {

	public static TransactionResponse recreatePhotosPrintMasterRecrod(String photoID, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			con = DBHelper.getConnection();
			boolean transStatus = false;
			String query = Queries.RECREATE_PHOTOS_PRINT_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, loggedInUser);
			pst.setTimestamp(2, new Timestamp(new Date().getTime()));
			pst.setString(3, photoID);
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

	public static TransactionResponse LoadPhotosPrintMasterRecords(String loggedInUser) {
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			con = DBHelper.getConnection();
			String query = Queries.LOAD_ALL_PHOTOS_PRINT_MASTER_DATA;
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			SortedMap<String, PhotosBean> pMap = new TreeMap<>();
			while (rs.next()) {
				String photoID = rs.getString("PHOTOID");
				String status = "Y";// Default in available status
				Timestamp deletedOn = rs.getTimestamp("DELETEDON");
				String deletedBy = rs.getString("DELETEDBY");
				if (deletedBy != null && deletedOn != null) {
					status = "N";// Deleted status
				}
				PhotosBean record = new PhotosBean(photoID, rs.getString("DESCRIPTION"), rs.getInt("QUANTITY"),
						rs.getBigDecimal("PRICE"), rs.getString("CREATEDBY"), rs.getTimestamp("CREATEDON"),
						rs.getString("UPDATEDBY"), rs.getTimestamp("UPDATEDON"), deletedBy, deletedOn, status);
				pMap.put(photoID, record);
			}
			transResponse.setTransactionStatus(true);
			transResponse.setPhotoMap(pMap);
		} catch (Exception e) {
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

	public static TransactionResponse createPhotoPrintMasterRecrod(PhotosBean pBean) {
	
		Connection con = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement pst = null;
		PreparedStatement pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {

			String PhotoID = null;
			con = DBHelper.getConnection();
			String query = Queries.INSERT_NEW_PHOTOS_PRINT_RECORD;
			pst = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			pst.setString(1, pBean.getDESCRIPTION());
			pst.setInt(2, pBean.getQUANTITY());
			pst.setBigDecimal(3, pBean.getPRICE());
			pst.setString(4, pBean.getCREATEDBY());
			pst.execute();
			rs = pst.getGeneratedKeys();
			if (rs.next()) {
				pst1 = con.prepareStatement(Queries.FETCH_PHOTOSID);
				pst1.setInt(1, rs.getInt(1));
				rs1 = pst1.executeQuery();
				if (rs1.next()) {
					PhotoID = rs1.getString("PHOTOID");
				}
			}
			con.commit();
			transResponse.setPhotoID(PhotoID);
			transResponse.setTransactionStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

	public static TransactionResponse deletePhotosPrintMasterRecrod(String photoID, String loggedInUser) {
		
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {

			boolean delStatus = false;
			con = DBHelper.getConnection();
			String query = Queries.DELETE_PHOTOS_PRINT_RECORD;
			pst = con.prepareStatement(query);
			pst.setTimestamp(1, new Timestamp(new java.util.Date().getTime()));
			pst.setString(2, loggedInUser);
			pst.setString(3, photoID);
			int count = pst.executeUpdate();
			if (count == 1) {
				delStatus = true;
			}
			con.commit();
			transResponse.setTransactionStatus(delStatus);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

	public static TransactionResponse updatePhotoMasterRecrod(PhotosBean pBean) {

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean status = false;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		try {
			con = DBHelper.getConnection();
			String query = Queries.UPDATE_PHOTOS_PRINT_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, pBean.getDESCRIPTION());
			pst.setInt(2, pBean.getQUANTITY());
			pst.setBigDecimal(3, pBean.getPRICE());
			pst.setString(4, pBean.getUPDATEDBY());
			pst.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));
			pst.setString(6, pBean.getPHOTOID());
			int count = pst.executeUpdate();
			if (count == 1) {
				status = true;
			}
			con.commit();
			transResponse.setTransactionStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
}
