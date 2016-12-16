package com.github.panhongan.util.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.sql.SqlUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class HiveUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HiveUtil.class);
	
	public static final String HIVE_DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";
	
	public static final String SOURCE_TYPE = "hive2";
	
	public static HiveSession createHiveSession(String hive_conf_file) throws SQLException {
		Config conf = new Config();
		conf.parse(hive_conf_file);
		return new HiveSession(SqlUtil.createConnection(conf));
	}
	
	public static HiveSession createHiveSession(Config conf) throws SQLException {
		return new HiveSession(SqlUtil.createConnection(conf));
	}
	
	public static void closeHiveSession(HiveSession session) {
		if (session != null) {
			session.close();
		}
	}
	
	public static boolean validateHiveConfig(String hive_conf_file) {
		Config config = new Config();
		config.parse(hive_conf_file);
		return HiveUtil.validateHiveConfig(config);
	}
	
	public static boolean validateHiveConfig(Config config) {
		boolean is_ok = false;
		
		HiveSession session = null;
		try {
			session = HiveUtil.createHiveSession(config);
			String sql = "show databases";
			ResultSet rs = session.executeQuery(sql);
			if (rs.next()) {
				is_ok = true;
			}
			SqlUtil.closeResultSet(rs);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		} finally {
			HiveUtil.closeHiveSession(session);
		}
		
		return is_ok;
	}
	
}
