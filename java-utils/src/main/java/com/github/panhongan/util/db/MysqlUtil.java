package com.github.panhongan.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MysqlUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MysqlUtil.class);
	
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	
	private static final String DATA_SOURCE_NAME = "mysql_connection_pool";
	
	private static int pool_id = 0;
	
	public static MysqlPool createMysqlPool(Config conf) {
		MysqlPool pool = new MysqlPool();
		if (!pool.createInnerPool(conf)) {
			pool.closeInnerPool();
			pool = null;
		}
		
		return pool;
	}
	
	public static MysqlPool createMysqlPool(String mysql_conf_file) {
		MysqlPool mysql_pool = null;
		
		Config conf = new Config();
		if (conf.parse(mysql_conf_file)) {
			mysql_pool = MysqlUtil.createMysqlPool(conf);
		} else {
			logger.warn("parse {} failed", mysql_conf_file);
		}
		
		return mysql_pool;
	}
	
	public static void closeMysqlPool(MysqlPool pool) {
		if (pool != null) {
			pool.closeInnerPool();
		}
	}
	
	public static Connection createConnection(String server, int port, String db, 
			String user, String passwd, String charset) {
		Connection conn = null;

		try {
			Class.forName(MYSQL_DRIVER);
			DriverManager.setLoginTimeout(60);
			conn = DriverManager.getConnection(MysqlUtil.getJDBCUrl(server, port, db, charset), 
					user, passwd);
		} catch (Exception e) {
			logger.warn(e.getMessage());
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
	
	public static void closeMysqlSession(MysqlSession session) {
		if (session != null) {
			session.disconnect();
		}
	}
	
	public static String getJDBCUrl(String server, int port, String db, String charset) {
		return ("jdbc:mysql://" 
				+ server + ":"+ port  + "/" + db
				+ "?socketTimeout=300000&characterEncoding=" + charset);
	}
	
	// class MysqlPool
	public static class MysqlPool {
		
		private ComboPooledDataSource pool = null;
		
		public boolean createInnerPool(Config conf) {
			try {
				String server = conf.getString("mysql.server");
				int port = conf.getInt("mysql.port");
				String db = conf.getString("mysql.db");
				String user = conf.getString("mysql.user");
				String passwd = conf.getString("mysql.password");
				String charset = conf.getString("mysql.charset", "utf8");
				int timeout = conf.getInt("mysql.login.timeout", 60);
				
				int min_pool_size = conf.getInt("min.pool.size", 10);
				int max_pool_size = conf.getInt("max.pool.size", 20);
				int init_pool_size = conf.getInt("init.pool.size", 10);
				int increment_num = conf.getInt("increment.num", 5);
				int max_idle_time = conf.getInt("max.idle.time", 300);
				
				pool = new ComboPooledDataSource(true);
				pool.setDataSourceName(DATA_SOURCE_NAME + "_" + (pool_id++));
				pool.setJdbcUrl(MysqlUtil.getJDBCUrl(server, port, db, charset));
				pool.setLoginTimeout(timeout);
				pool.setDriverClass(MYSQL_DRIVER); 
				pool.setUser(user);
				pool.setPassword(passwd);
				pool.setMaxPoolSize(max_pool_size);
				pool.setMinPoolSize(min_pool_size);
				pool.setInitialPoolSize(init_pool_size);  
				pool.setAcquireIncrement(increment_num);  
				pool.setMaxIdleTime(max_idle_time);
				pool.setTestConnectionOnCheckout(true);
				pool.setAutoCommitOnClose(true);
				
				if (!MysqlPool.validatePool(pool)) {
					this.closeInnerPool();
					pool = null;
				}
			} catch (Exception e) {
				logger.warn(e.getMessage());
				this.closeInnerPool();
				pool = null;
			}
			
			return (pool != null);
		}
		
		public ComboPooledDataSource getInnerPool() {
			return pool;
		}
		
		public void closeInnerPool() {
			if (pool != null) {
				try {
					pool.close();
				} catch (Exception e) {
					logger.warn(e.getMessage());
				}
			}
		}
		
		
		public static boolean validatePool(ComboPooledDataSource pool) {
			boolean is_valid = false;
			
			if (pool != null) {
				Connection conn = null;
				try {
					conn = pool.getConnection();
					if (conn != null) {
						is_valid = true;
					}
				} catch (Exception e) {
					logger.warn(e.getMessage());
				} finally {
				}
			}
			
			return is_valid;
		}
	}

}
