package com.github.lalalu.utils.hive;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.lalalu.utils.conf.Config;
import com.github.lalalu.utils.sql.SqlUtils;

import org.apache.hive.jdbc.HiveConnection;

/**
 * lalalu plus
 */

public class HiveUtils {

	private static final Logger logger = LoggerFactory.getLogger(HiveUtils.class);

	public static final String HIVE_DRIVER = "org.apache.hadoop.hive.jdbc.HiveDriver";

	public static HiveSession createHiveSession(String jdbcConfFile, boolean isNew) throws SQLException {
		Config conf = new Config();
		if (conf.parse(jdbcConfFile)) {
			return HiveUtils.createHiveSession(conf, null, isNew);
		} else {
			throw new RuntimeException("parse config file failed : " + jdbcConfFile);
		}
	}
	
	public static HiveSession createHiveSession(String jdbcConfFile, Config hiveConf, boolean isNew) throws SQLException {
		Config conf = new Config();
		if (conf.parse(jdbcConfFile)) {
			return HiveUtils.createHiveSession(conf, hiveConf, isNew);
		} else {
            throw new RuntimeException("parse config file failed : " + jdbcConfFile);
        }
	}

	public static HiveSession createHiveSession(Config jdbcConfig, Config hiveConf, boolean isNew) throws SQLException {
		String url = HiveUtils.getJdbcUrl(jdbcConfig, null, hiveConf, null, true);
		return new HiveSession(HiveUtils.createHiveConnection(url, new Properties()));
	}

	public static void closeHiveSession(HiveSession session) {
		if (session != null) {
			session.close();
		}
	}
	
	public static Connection createHiveConnection(String url, Properties p) throws SQLException {
		return new HiveConnection(url, p);
	}

	public static boolean validateHiveConfig(String hiveConfFile) {
		Config config = new Config();
		config.parse(hiveConfFile);
		return HiveUtils.validateHiveConfig(config);
	}

	public static boolean validateHiveConfig(Config config) {
		boolean ok = false;
		HiveSession session = null;
		
		try {
			session = HiveUtils.createHiveSession(config, null, true);
			String sql = "show databases";
			ResultSet rs = session.executeQuery(sql);
			if (rs.next()) {
                ok = true;
			}
			SqlUtils.closeResultSet(rs);
		} catch (Exception e) {
			logger.warn("", e);
		} finally {
			HiveUtils.closeHiveSession(session);
		}

		return ok;
	}

	// hive 0.11.0(包含)以后的版本
	// jdbc:hive2://<host>:<port>/dbName;sess_var_list?hiveConf_list#hive_var_list
	
	// hive 0.11.0之前的版本
	// jdbc:hive://<host>:<port>/dbName;sess_var_list?hiveConf_list#hive_var_list
	public static String getJdbcUrl(Config jdbcConfig, Config sessionConfig, Config hiveConf, Config hiveVar, boolean isNew) {
		String str = "jdbc:";
		if (isNew) {
			str += "hive2://";
		} else {
			str += "hive://";
		}
		str += jdbcConfig.getString("sql.server");
		str += ":";
		str += jdbcConfig.getString("sql.port");
		str += "/";
		str += jdbcConfig.getString("sql.db");
		
		if (sessionConfig == null) {
            sessionConfig = new Config();
            sessionConfig.addProperty("user", jdbcConfig.getString("sql.user"));
            sessionConfig.addProperty("password", jdbcConfig.getString("sql.password"));
		}
		
		// session_config
		if (sessionConfig != null && sessionConfig.isNotEmpty()) {
			str += ";";
			for (String key : sessionConfig.keySet()) {
				str += key;
				str += "=";
				str += sessionConfig.getString(key);
				str += "&";
			}
			str = str.substring(0, str.lastIndexOf('&'));
		}

		// hiveConfig
		if (hiveConf != null && !hiveConf.isEmpty()) {
			str += "?";
			for (String key : hiveConf.keySet()) {
				str += key;
				str += "=";
				str += hiveConf.getString(key);
				str += "&";
			}
			str = str.substring(0, str.lastIndexOf('&'));
		}

		// hive_var
		if (hiveVar != null && hiveVar.isNotEmpty()) {
			str += "#";
			for (String key : hiveVar.keySet()) {
				str += key;
				str += "=";
				str += hiveVar.getString(key);
				str += "&";
			}
			str = str.substring(0, str.lastIndexOf('&'));
		}

		return str;
	}

}
