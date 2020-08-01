package com.github.panhongan.utils.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.github.panhongan.utils.conf.Config;
import com.github.panhongan.utils.sql.SqlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * lalalu plus
 */

public class MysqlUtils {

	private static final Logger logger = LoggerFactory.getLogger(MysqlUtils.class);

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	private static final int DEFAULT_LOGIN_TIMEOUT = 10;    // seconds

	public static MysqlPool createMysqlPool(Config conf) {
		MysqlPool pool = new MysqlPool();
		if (!pool.create(conf)) {
			pool.close();
			throw new RuntimeException("create pool failed : " + conf);
		}
		return pool;
	}

	public static MysqlPool createMysqlPool(String confFile) {
		Config conf = new Config();
		if (conf.parse(confFile)) {
			return MysqlUtils.createMysqlPool(conf);
		} else {
			throw new RuntimeException("parse mysql config file failed" + confFile);
		}
	}

	public static void closeMysqlPool(MysqlPool pool) {
		if (pool != null) {
			pool.close();
		}
	}

	// jdbc:mysql://<host>:<port>/<database_name>?property1=value1&property2=value2
	public static String getJDBCUrl(Config jdbc_config) {
		String str = "jdbc:mysql://";
		str += jdbc_config.getString("sql.server");
		str += ":";
		str += jdbc_config.getString("sql.port");
		str += "/";
		str += jdbc_config.getString("sql.db");
		str += "?user=";
		str += jdbc_config.getString("sql.user");
		;
		str += "&password=";
		str += jdbc_config.getString("sql.password");
		;
		str += "&socketTimeout=600000&characterEncoding=";
		str += jdbc_config.getString("sql.charset");
		;
		return str;
	}

	public static MysqlSession createMysqlSession(String confFile) throws SQLException {
		Config conf = new Config();
		if (conf.parse(confFile)) {
			return MysqlUtils.createMysqlSession(conf);
		} else {
			throw new RuntimeException("parse mysql config file failed : " + confFile);
		}
	}

	public static MysqlSession createMysqlSession(Config conf) throws SQLException {
		String url = MysqlUtils.getJDBCUrl(conf);
		return new MysqlSession(MysqlUtils.createConnection(MYSQL_DRIVER, url));
	}

	public static void closeMysqlSession(MysqlSession session) {
		if (session != null) {
			session.close();
		}
	}

	public static Connection createConnection(String driver, String url) {
		return createConnection(driver, url, DEFAULT_LOGIN_TIMEOUT);
	}

	public static Connection createConnection(String driver, String url, int login_timeout) {
		try {
			Class.forName(driver);
			DriverManager.setLoginTimeout(login_timeout);
			return DriverManager.getConnection(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// class MysqlPool
	public static class MysqlPool {

		private static final String DATA_SOURCE_NAME = "mysql_connection_pool";

		private ComboPooledDataSource pool = null;

		public boolean create(Config conf) {
			try {
				String user = conf.getString("sql.user");
				String passwd = conf.getString("sql.password");
				int timeout = conf.getInt("sql.login.timeout", 10);

				int min_pool_size = conf.getInt("min.pool.size", 10);
				int max_pool_size = conf.getInt("max.pool.size", 20);
				int init_pool_size = conf.getInt("init.pool.size", 10);
				int increment_num = conf.getInt("increment.num", 5);
				int max_idle_time = conf.getInt("max.idle.time", 300);

				pool = new ComboPooledDataSource(true);
				pool.setDataSourceName(DATA_SOURCE_NAME + "_" + System.currentTimeMillis());
				pool.setJdbcUrl(MysqlUtils.getJDBCUrl(conf));
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

				if (!this.validatePool(pool)) {
					this.close();
					pool = null;
				}
			} catch (Exception e) {
				logger.warn("", e);
				this.close();
				pool = null;
			}

			return (pool != null);
		}

		public Connection getConnection() throws SQLException {
			return pool.getConnection();
		}

		public void close() {
			if (pool != null) {
				try {
					pool.close();
					pool = null;
				} catch (Exception e) {
					logger.warn("", e);
				}
			}
		}

		private boolean validatePool(ComboPooledDataSource pool) {
			boolean ok = false;

			if (pool != null) {
				Connection conn = null;
				try {
					conn = pool.getConnection();
					if (conn != null) {
						ok = true;
					}
				} catch (Exception e) {
					logger.warn("", e);
				} finally {
					SqlUtils.closeConnection(conn);
				}
			}

			return ok;
		}
	}

}
