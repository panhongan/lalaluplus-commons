package com.github.panhongan.util.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MysqlSession {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlSession.class);
	
	private Connection conn = null;
	
	private Statement stmt = null;
	
	public MysqlSession(Connection connection) {
		this.conn = connection;
		
		try {
			if (conn != null) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		
		try {
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		return rs;
	}
	
	public boolean executeUpdate(String sql) {
		boolean ret = false;
		
		try {
			if (stmt != null) {
				stmt.executeUpdate(sql);
				ret = true;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
		
		return ret;
	}
	
	public void disconnect() {
		MysqlUtil.closeStatement(stmt);
		stmt = null;
		
		MysqlUtil.closeConnection(conn);
		conn = null;
	}

}
 