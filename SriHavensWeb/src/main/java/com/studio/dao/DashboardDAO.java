package com.studio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.studio.backend.DBHelper;
import com.studio.bean.UserDetails;
import com.studio.constants.Queries;

public class DashboardDAO {
	
	public static UserDetails loadUserDetails(String loggedInUser) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		UserDetails userDetails = new UserDetails();
		try 
		{
			con = DBHelper.getConnection();
			String query = Queries.LOAD_USER_DETAILS;
			pst = con.prepareStatement(query);
			pst.setString(1, loggedInUser);
			rs = pst.executeQuery();
			if(rs.next()) {
				userDetails = new UserDetails(rs.getString("UID"),
						rs.getString("NAME"),
						rs.getString("EMAIL"),
						rs.getString("CONTACT"),
						null,
						rs.getString("RNAME"),
						null);
			}
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
		return userDetails;
	}
	
}
