package com.github.panhongan.util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;

public class SqlUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlUtil.class);
	
	private static final int DEFAULT_LOGIN_TIMEOUT = 10;	// seconds
	
	public static Connection createConnection(String driver, String source_type, String server, 
			int port, String db, String user, String passwd, String charset, int login_timeout) {
		Connection conn = null;

		try {
			Class.forName(driver);
			DriverManager.setLoginTimeout(login_timeout);
			conn = DriverManager.getConnection(SqlUtil.getJDBCUrl(source_type, server, port, db, charset), 
					user, passwd);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
		
		return conn;
	}
	
	public static Connection createConnection(Config conf) {
		Connection conn = null;
		
		try {
			conn = SqlUtil.createConnection(conf.getString("sql.driver"),
					conf.getString("sql.source"),
					conf.getString("sql.server"), 
					conf.getInt("sql.port"), 
					conf.getString("sql.db"), 
					conf.getString("sql.user"), 
					conf.getString("sql.password"), 
					conf.getString("sql.charset"),
					conf.getInt("sql.login.timeout", DEFAULT_LOGIN_TIMEOUT));
		 } catch (Exception e) {
			 logger.warn(e.getMessage(), e);
		 }
		
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
	}
	
	public static String getJDBCUrl(String source_type, String server, int port, String db, String charset) {
		return ("jdbc:" + source_type + "://" 
				+ server + ":"+ port  + "/" + db
				+ "?socketTimeout=300000&characterEncoding=" + charset);
	}

}
