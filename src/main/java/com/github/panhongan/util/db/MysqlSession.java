package com.github.panhongan.util.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.sql.SqlUtil;


public class MysqlSession {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlSession.class);
	
	private Connection conn = null;
	
	private Statement stmt = null;
	
	public MysqlSession(Connection connection) throws SQLException {
		this.conn = connection;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
	}
	
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		
		try {
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
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
			logger.warn(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public boolean execute(String sql) {
		boolean ret = false;
		
		try {
			if (stmt != null) {
				stmt.execute(sql);
				ret = true;
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e);
		}
		
		return ret;
	}
	
	public void close() {
		SqlUtil.closeStatement(stmt);
		SqlUtil.closeConnection(conn);
	}

}
 