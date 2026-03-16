package com.studio.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.studio.backend.DBHelper;
import com.studio.bean.UserCreationStatusBean;
import com.studio.bean.UserAuthBean;
import com.studio.constants.Messages;
import com.studio.constants.Queries;

public class LogonHandlerDao {

	private static final Random random = new SecureRandom();
	private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_~+";
	private static final int iterations = 10000;
	private static final int keylength = 256;

//	 public static void main(String[] args) {
//		 StorePswdBean b = storePassword("kamal324", "Sneha@3248");
//		 System.out.print(b.getMessage());
//		 System.out.print(b.IsPswdStored());
//	 }
//	
	public static UserCreationStatusBean createUserDetails(String uid, String pswd, String email, String contact,
			String createdBy) {
		String message = null;
		Connection con = null;
		UserCreationStatusBean ucBean = new UserCreationStatusBean();
		try {
			con = DBHelper.getConnection();
			String query = Queries.CREATE_USER_DETAILS;
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, uid);
			pst.setString(2, email);
			pst.setString(3, contact);
			pst.setString(4, createdBy);
			pst.setString(5, "Y");
			int status = pst.executeUpdate();
			if (status > 0) {
				ucBean = CreateUserLogin(uid, pswd, createdBy);
			}
			con.commit();
		} catch (SQLIntegrityConstraintViolationException e) {
			message = Messages.USERID_EXISTS;
			ucBean.setMessage(message);
		} catch (Exception e) {
			message = Messages.SAVE_FAILED + e.getMessage() + Messages.CON_SYS_ADMIN;
			ucBean.setMessage(message);
		} finally {
			DBHelper.closeConnection();
		}
		return ucBean;
	}

	public static UserCreationStatusBean CreateUserLogin(String uid, String pswd, String createdBy) {
		boolean isUserCreated = false;
		String message = null;
		Connection con = null;
		try {
			con = DBHelper.getConnection();
			String saltvalue = getSaltvalue(30);
			String encryptedpassword = generateSecurePassword(pswd, saltvalue);
			String query = Queries.CREATE_USER_LOGON;
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, uid);
			pst.setString(2, encryptedpassword);
			pst.setString(3, saltvalue);
			pst.setString(4, createdBy);
			int status = pst.executeUpdate();
			if (status > 0) {
				isUserCreated = true;
			}
			con.commit();
		} catch (SQLIntegrityConstraintViolationException e) {
			message = Messages.USERID_EXISTS;
		} catch (Exception e) {
			message = Messages.SAVE_FAILED + e.getMessage() + Messages.CON_SYS_ADMIN;
		} finally {
			DBHelper.closeConnection();
		}
		return new UserCreationStatusBean(isUserCreated, message);
	}

	public static UserAuthBean isUserAuthorized(String uid, String pswd) {
		boolean isUserAuthorized = false;
		String message = Messages.AUTH_FAILED;
		PreparedStatement pst = null, pst1 = null, pst2 = null;
		ResultSet rs = null, rs1 = null;
		String lastLoginAt = "This is your first Login.";
		boolean isPSWDDefault = false;
		Connection con = null;
		try {
			con = DBHelper.getConnection();
			String query = Queries.IS_USER_AUTHORIZED;
			pst = con.prepareStatement(query);
			pst.setString(1, uid.toUpperCase());
			rs = pst.executeQuery();

			if (rs.next()) {
				isUserAuthorized = verifyUserPassword(pswd, rs.getString("PSWD"), rs.getString("SALT"));
				if (isUserAuthorized) {
					message = null;
					query = Queries.READ_LASTLOGIN_ISPSWD_DEFAULT;
					pst1 = con.prepareStatement(query);
					pst1.setString(1, uid);
					rs1 = pst1.executeQuery();
					if (rs1.next()) {
						Timestamp lastLoginAtTimestamp = rs1.getTimestamp(1);
						isPSWDDefault = (rs1.getInt(2)==1)?true:false;
						if (lastLoginAtTimestamp != null) {
							lastLoginAt = new SimpleDateFormat("dd/MM/yyyy hh:mm aa").format(lastLoginAtTimestamp)
									.toUpperCase();
						}
					}

					query = Queries.UPDATE_LAST_LOGIN;
					pst2 = con.prepareStatement(query);
					pst2.setTimestamp(1, new Timestamp(new Date().getTime()));
					pst2.setString(2, uid);
					pst2.execute();
					con.commit();
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
			message = Messages.VALIDATION_FAILED_USER_AUTH + e.getMessage();
		} finally {
			DBHelper.closeConnection();

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
				if (pst2 != null) {
					pst1.close();
				}
				if (rs1 != null) {
					rs.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new UserAuthBean(isUserAuthorized,isPSWDDefault, message, null, lastLoginAt);
	}

	/* Method to generate the salt value. */
	public static String getSaltvalue(int length) {
		StringBuilder finalval = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			finalval.append(characters.charAt(random.nextInt(characters.length())));
		}

		return new String(finalval);
	}

	/* Method to generate the hash value */
	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keylength);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError(Messages.HASH_PSWD_ERROR + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	/* Method to encrypt the password using the original password and salt value. */
	public static String generateSecurePassword(String password, String salt) {
		String finalval = null;

		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

		finalval = Base64.getEncoder().encodeToString(securePassword);

		return finalval;
	}

	/* Method to verify if both password matches or not */
	public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
		boolean finalval = false;

		/* Generate New secure password with the same salt */
		String newSecurePassword = generateSecurePassword(providedPassword, salt);

		/* Check if two passwords are equal */
		finalval = newSecurePassword.equalsIgnoreCase(securedPassword);

		return finalval;
	}
}
