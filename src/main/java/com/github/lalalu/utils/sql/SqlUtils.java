package com.github.lalalu.utils.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * lalalu plus
 */
public class SqlUtils {

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				// do nothing
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				// do nothing
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			    // do nothing
			}
		}
	}

}
