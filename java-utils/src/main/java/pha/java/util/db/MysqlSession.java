package pha.java.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class MysqlSession {
	
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private Logger logger = Logger.getLogger(MysqlSession.class);
	
	private MysqlConfig config = null;
	
	private Connection conn = null;
	
	private Statement stmt = null;

	
	public MysqlSession(MysqlConfig config) {
		this.config = config;
		assert(config != null);
	}
	
	public boolean connect() {
		boolean ret = false;
		
		try {
			Class.forName(MYSQL_DRIVER);
			DriverManager.setLoginTimeout(60);
			conn = DriverManager.getConnection("jdbc:mysql://" 
					+ config.ip + ":"+ config.port 
					+ "/" + config.db + "?socketTimeout=360000", 
					config.user, config.passwd);
			if (conn != null) {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				ret = true;
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
		
		return ret;
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
	
	public void executeUpdate(String sql) {
		try {
			if (stmt != null) {
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			logger.warn(e.getMessage());
		}
	}
	
	public void disconnect() {
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
		
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
 