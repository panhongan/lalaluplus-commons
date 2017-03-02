package com.github.panhongan.util.hive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.sql.SqlUtil;

import org.apache.hive.jdbc.HiveConnection;

public class HiveUtil {

	private static final Logger logger = LoggerFactory.getLogger(HiveUtil.class);

	public static final String HIVE_DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public static HiveSession createHiveSession(String jdbc_conf_file, boolean is_new) throws SQLException {
		HiveSession session = null;
		Config conf = new Config();
		if (conf.parse(jdbc_conf_file)) {
			session = HiveUtil.createHiveSession(conf, null, is_new);
		}
		return session;
	}
	
	public static HiveSession createHiveSession(String jdbc_conf_file, Config hive_conf, boolean is_new) throws SQLException {
		HiveSession session = null;
		Config conf = new Config();
		if (conf.parse(jdbc_conf_file)) {
			session = HiveUtil.createHiveSession(conf, hive_conf, is_new);
		}
		return session;
	}

	public static HiveSession createHiveSession(Config jdbc_config, Config hive_conf, boolean is_new) throws SQLException {
		String url = HiveUtil.getJdbcUrl(jdbc_config, null, hive_conf, null, true);
		return new HiveSession(HiveUtil.createHiveConnection(url, new Properties()));
	}

	public static void closeHiveSession(HiveSession session) {
		if (session != null) {
			session.close();
		}
	}
	
	public static Connection createHiveConnection(String url, Properties p) throws SQLException {
		return new HiveConnection(url, p);
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
			session = HiveUtil.createHiveSession(config, null, true);
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

	// hive 0.11.0(包含)以后的版本
	// jdbc:hive2://<host>:<port>/dbName;sess_var_list?hive_conf_list#hive_var_list
	
	// hive 0.11.0之前的版本
	// jdbc:hive://<host>:<port>/dbName;sess_var_list?hive_conf_list#hive_var_list
	public static String getJdbcUrl(Config jdbc_config, Config session_config, Config hive_conf, Config hive_var, boolean is_new) {
		String str = "jdbc:";
		if (is_new) {
			str += "hive2://";
		} else {
			str += "hive://";
		}
		str += jdbc_config.getString("sql.server");
		str += ":";
		str += jdbc_config.getString("sql.port");
		str += "/";
		str += jdbc_config.getString("sql.db");
		
		
		if (session_config == null) {
			session_config = new Config();
			session_config.addProperty("user", jdbc_config.getString("sql.user"));
			session_config.addProperty("password", jdbc_config.getString("sql.password"));
		}
		
		// session_config
		if (session_config != null && !session_config.isEmpty()) {
			str += ";";
			for (String key : session_config.keySet()) {
				str += key;
				str += "=";
				str += session_config.getString(key);
				str += "&";
			}
			str = StringUtil.trimSuffix(str, "&");
		}

		// hive_config
		if (hive_conf != null && !hive_conf.isEmpty()) {
			str += "?";
			for (String key : hive_conf.keySet()) {
				str += key;
				str += "=";
				str += hive_conf.getString(key);
				str += "&";
			}
			str = StringUtil.trimSuffix(str, "&");
		}

		// hive_var
		if (hive_var != null && !hive_var.isEmpty()) {
			str += "#";
			for (String key : hive_var.keySet()) {
				str += key;
				str += "=";
				str += hive_var.getString(key);
				str += "&";
			}
			str = StringUtil.trimSuffix(str, "&");
		}

		return str;
	}

}
