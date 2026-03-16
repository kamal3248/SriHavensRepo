package com.studio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;

import com.studio.constants.Messages;
import com.studio.backend.DBHelper;
import com.studio.bean.TransactionResponse;
import com.studio.bean.UserAdminOverview;
import com.studio.bean.UserDetails;
import com.studio.constants.Queries;

public class UserAdminDAO {

	public static boolean isAdminUser(String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean isAdminUser = false;
		try {
			con = DBHelper.getConnection();
			String query = Queries.LOAD_USER_ROLE;
			pst = con.prepareStatement(query);
			pst.setString(1, loggedInUser);
			rs = pst.executeQuery();
			if (rs.next()) {

				if (rs.getString("ROLE").equals("R001")) {// ADMIN Users can only manage other users
					isAdminUser = true;
				}
			}
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
		return isAdminUser;
	}

	public static HashMap<String, String> loadAllRoles() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		HashMap<String, String> roleMap = new HashMap<>();
		try {
			con = DBHelper.getConnection();
			String query = Queries.LOAD_ALL_ROLES;
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				roleMap.put(rs.getString("RID"), rs.getString("RNAME"));
			}
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
		return roleMap;
	}

	public static TransactionResponse loadUsersForOverview() {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		try {
			HashMap<String, UserAdminOverview> userMap = new HashMap<>();
			con = DBHelper.getConnection();
			String query = Queries.LOAD_ALL_USER_DETAILS;
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				String uID = rs.getString("UID");
				String status = null;
				Timestamp deletedOn = rs.getTimestamp("DELETEDON");
				String deletedBy = rs.getString("DELETEDBY");
				if (deletedBy != null && deletedOn != null) {
					status = "D";// Deleted status
				}
				UserAdminOverview record = new UserAdminOverview();
				record.setUID(uID.toUpperCase());
				record.setNAME(rs.getString("NAME"));
				record.setEMAIL(rs.getString("EMAIL"));
				record.setCONTACT(rs.getString("CONTACT"));
				record.setCREATEDON(rs.getTimestamp("CREATEDON"));
				record.setCREATEDBY(rs.getString("CREATEDBY"));
				record.setUPDATEDON(rs.getTimestamp("UPDATEDON"));
				record.setUPDATEDBY(rs.getString("UPDATEDBY"));
				record.setDELETEDON(deletedOn);
				record.setDELETEDBY(deletedBy);
				record.setLASTLOGINAT(rs.getTimestamp("LASTLOGINAT")!=null?rs.getTimestamp("LASTLOGINAT"):null);
				record.setSTATUS(status != null ? status : rs.getString("STATUS"));
				record.setROLE(rs.getString("ROLE"));
				record.setROLENAME(rs.getString("RNAME"));
				record.setPSWDCHANGEDBY(rs.getString("PSWDCHANGEDBY"));
				record.setPSWDCHANGEDON(rs.getTimestamp("PSWDCHANGEDON"));
				userMap.put(rs.getString("UID"), record);
			}
			transResponse.setUserMap(userMap);
			transResponse.setTransactionStatus(true);
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

	public static TransactionResponse recreateUserMasterRecord(String userID, String updatedBy) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1=null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.USER_MASTER_RECREATE_FAILED);
		try {
			con = DBHelper.getConnection();
			String query = Queries.RECREATE_USERS_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, updatedBy);
			pst.setTimestamp(2, new Timestamp(new Date().getTime()));
			pst.setString(3, userID);
			int count = pst.executeUpdate();
			if (count == 1) {
				query = Queries.RECREATE_LOGON_RECORD;
				pst1 = con.prepareStatement(query);
				pst1.setString(1, updatedBy);
				pst1.setTimestamp(2, new Timestamp(new Date().getTime()));
				pst1.setString(3, userID);
				int count1 = pst1.executeUpdate();
				if (count1 == 1) {
					transResponse.setTransactionStatus(true);
					transResponse.setMessage(Messages.USER_MASTER_RECREATE_SUCCESS);
					con.commit();
				} else {
					con.rollback();
				}
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			// ucBean.setMessage(message);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if (pst1 != null) {
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

	public static TransactionResponse deleteUserMasterRecord(String userID, String deletedBy) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.USER_MASTER_DELETE_FAILED);
		try {
			con = DBHelper.getConnection();
			String query = Queries.DELETE_USERS_RECORD;
			pst = con.prepareStatement(query);
			pst.setTimestamp(1, new Timestamp(new Date().getTime()));
			pst.setString(2, deletedBy);
			pst.setString(3, userID);
			int count = pst.executeUpdate();
			if (count == 1) {
				query = Queries.DELETE_LOGON_RECORD;
				pst1 = con.prepareStatement(query);
				pst1.setTimestamp(1, new Timestamp(new Date().getTime()));
				pst1.setString(2, deletedBy);
				pst1.setString(3, userID);
				int count1 = pst1.executeUpdate();
				if (count1 == 1) {
					transResponse.setTransactionStatus(true);
					transResponse.setMessage(Messages.USER_MASTER_DELETE_SUCCESS);
					con.commit();
				} else {
					con.rollback();
				}
			} else {
				con.rollback();
			}
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
				if (pst1 != null) {
					pst1.close();
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

	public static TransactionResponse updateUserMasterStatusRecord(String userID, String status, String updatedBy) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		String message = Messages.USER_MASTER_UPDATE_FAILED;
		try {
			con = DBHelper.getConnection();
			String query = Queries.UPDATE_USERS_STATUS_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, status);
			pst.setString(2, updatedBy);
			pst.setTimestamp(3, new Timestamp(new Date().getTime()));
			pst.setString(4, userID);
			int count = pst.executeUpdate();
			if (count == 1) {
				if (status == "Y") {
					message = Messages.USER_MASTER_UNLOCK_SUCCESS;
				} else if (status == "L") {
					message = Messages.USER_MASTER_LOCK_SUCCESS;
				}
				transResponse.setTransactionStatus(true);
				con.commit();
			} else {
				if (status == "Y") {
					message = Messages.USER_MASTER_UNLOCK_FAILED;
				} else if (status == "L") {
					message = Messages.USER_MASTER_LOCK_FAILED;
				}
			}
		} catch (Exception e) {
			if (status == "Y") {
				message = Messages.USER_MASTER_UNLOCK_FAILED;
			} else if (status == "L") {
				message = Messages.USER_MASTER_LOCK_FAILED;
			}
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
		transResponse.setMessage(message);
		return transResponse;
	}

	public static TransactionResponse updateUserMasterRecord(UserDetails record) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.USER_MASTER_UPDATE_FAILED);
		String salt = LogonHandlerDao.getSaltvalue(30);
		try {
			con = DBHelper.getConnection();
			String query = Queries.UPDATE_USERS_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, record.getNAME());
			pst.setString(2, record.getEMAIL());
			pst.setString(3, record.getMOBILE());
			pst.setString(4, record.getROLE());
			pst.setTimestamp(5, new Timestamp(new Date().getTime()));
			pst.setString(6, record.getUPDATEDBY());
			pst.setString(7, record.getUID());
			int count = pst.executeUpdate();
			if (count == 1) {
				if(record.getPASSWORD()!=null) {
					query = Queries.UPDATE_LOGON_RECORD;
					pst1 = con.prepareStatement(query);
					pst1.setString(1, LogonHandlerDao.generateSecurePassword(record.getPASSWORD(), salt));
					pst1.setString(2, salt);
					pst1.setString(3, record.getCREATEDBY());
					pst1.setTimestamp(4, new Timestamp(new Date().getTime()));
					pst1.setString(5, record.getUID());
					int count1 = pst1.executeUpdate();
					if (count1 == 1) {
						transResponse.setTransactionStatus(true);
						transResponse.setMessage(Messages.USER_MASTER_UPDATE_SUCCESS);
						con.commit();
					} else {
						con.rollback();
					}
				}
				else {
					transResponse.setTransactionStatus(true);
					transResponse.setMessage(Messages.USER_MASTER_UPDATE_SUCCESS);
					con.commit();
				}
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			// ucBean.setMessage(message);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if(pst1!=null) {
					pst1.close();
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
	public static TransactionResponse createUserMasterRecord(UserDetails record) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.USER_MASTER_CREATE_FAILED);
		String salt = LogonHandlerDao.getSaltvalue(30);
		try {
			con = DBHelper.getConnection();
			String query = Queries.CREATE_USER_DETAILS;
			pst = con.prepareStatement(query);
			pst.setString(1, record.getUID());
			pst.setString(2, record.getNAME());
			pst.setString(3, record.getEMAIL());
			pst.setString(4, record.getMOBILE());
			pst.setString(5, record.getROLE());
			pst.setString(6, "Y");
			pst.setString(7, record.getCREATEDBY());
			pst.setTimestamp(8, new Timestamp(new Date().getTime()));
			int count = pst.executeUpdate();
			if (count == 1) {
				query = Queries.CREATE_USER_LOGON;
				pst1 = con.prepareStatement(query);
				pst1.setString(1, record.getUID());
				pst1.setString(2, LogonHandlerDao.generateSecurePassword(record.getPASSWORD(), salt));
				pst1.setString(3, salt);
				pst1.setString(4, record.getCREATEDBY());
				pst1.setTimestamp(5, new Timestamp(new Date().getTime()));
				int count1 = pst1.executeUpdate();
				if (count1 == 1) {
					transResponse.setTransactionStatus(true);
					transResponse.setMessage(MessageFormat.format(Messages.USER_MASTER_CREATE_SUCCESS, record.getUID()));
					con.commit();
				} else {
					con.rollback();
				}
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			// ucBean.setMessage(message);
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				if(pst1!=null) {
					pst1.close();
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
	public static TransactionResponse changePassword(String userID, String password, String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.CHANGE_PASSWORD_FAILED);
		String salt = LogonHandlerDao.getSaltvalue(30);
		try {
			con = DBHelper.getConnection();
			String query = Queries.UPDATE_LOGON_RECORD;
			pst = con.prepareStatement(query);
			pst.setString(1, LogonHandlerDao.generateSecurePassword(password, salt));
			pst.setString(2, salt);
			pst.setString(3, loggedInUser);
			pst.setTimestamp(4, new Timestamp(new Date().getTime()));
			pst.setString(5, userID);
			int count = pst.executeUpdate();
			if (count == 1) {
					query = Queries.UPDATE_USER_CHANGE_PASSWORD;
					pst1 = con.prepareStatement(query);
					pst1.setString(1, loggedInUser);
					pst1.setTimestamp(2, new Timestamp(new Date().getTime()));
					pst1.setString(3, loggedInUser);
					pst1.setTimestamp(4, new Timestamp(new Date().getTime()));
					pst1.setString(5, userID);
					int count1 = pst1.executeUpdate();
					if (count1 == 1) {
						transResponse.setTransactionStatus(true);
						transResponse.setMessage(Messages.CHANGE_PASSWORD_SUCCESS);
						con.commit();
					} else {
						con.rollback();
					}
			}
			else {
				con.rollback();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	public static UserDetails getUserDetail(String userID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		UserDetails uDetails = new UserDetails();
		try {
			con = DBHelper.getConnection();
			String query = Queries.READ_USER_DETAILS;
			pst = con.prepareStatement(query);
			pst.setString(1, userID);
			rs = pst.executeQuery();
			if (rs.next()) {
				uDetails = new UserDetails(userID, rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("CONTACT"), null, null, null);
			}
			
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
		return uDetails;
	}
	public static TransactionResponse storeTemporaryPassword(String userID, String password) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null, pst1 = null;
		TransactionResponse transResponse = new TransactionResponse();
		transResponse.setTransactionStatus(false);
		transResponse.setMessage(Messages.CHANGE_PASSWORD_FAILED);
		String salt = LogonHandlerDao.getSaltvalue(30);
		try {
			con = DBHelper.getConnection();
			String query = Queries.UPDATE_LOGON_RECORD_TEMP_PSWD;
			pst = con.prepareStatement(query);
			pst.setString(1, LogonHandlerDao.generateSecurePassword(password, salt));
			pst.setString(2, salt);
			pst.setString(3, "SYSTEM");
			pst.setTimestamp(4, new Timestamp(new Date().getTime()));
			pst.setString(5, userID);
			int count = pst.executeUpdate();
			if (count == 1) {
					query = Queries.UPDATE_USER_CHANGE_PASSWORD;
					pst1 = con.prepareStatement(query);
					pst1.setString(1, "SYSTEM");
					pst1.setTimestamp(2, new Timestamp(new Date().getTime()));
					pst1.setString(3, "SYSTEM");
					pst1.setTimestamp(4, new Timestamp(new Date().getTime()));
					pst1.setString(5, userID);
					int count1 = pst1.executeUpdate();
					if (count1 == 1) {
						transResponse.setTransactionStatus(true);
						transResponse.setMessage(Messages.CHANGE_PASSWORD_SUCCESS);
						con.commit();
					} else {
						con.rollback();
					}
			}
			else {
				con.rollback();
			}
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
}
