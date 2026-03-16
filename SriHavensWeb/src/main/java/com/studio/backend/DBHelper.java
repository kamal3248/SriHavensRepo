package com.studio.backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
	static String url = "jdbc:mysql://localhost:3306/studio";
	static String username = "root";
	static String password = "Raghu@3248";
	static Connection con = null;

	public static Connection getConnection() {
//		if (con == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url, username, password);
				con.setAutoCommit(false);
			} catch (Exception e) {	
				e.printStackTrace();
			}
//		}
		return con;
	}
	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
