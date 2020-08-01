package com.github.panhongan.utils.hive;

import java.sql.ResultSet;

import com.github.panhongan.utils.conf.Config;
import com.github.panhongan.utils.sql.SqlUtils;

/**
 * lalalu plus
 */

public class TestHiveUtils {

	public static void main(String[] args) {
		Config jdbc_config = new Config();
		jdbc_config.parse("conf/hive.properties");
		System.out.println(HiveUtils.getJdbcUrl(jdbc_config, null, null, null, true));
		System.out.println(HiveUtils.getJdbcUrl(jdbc_config, null, null, null, false));
		Config hive_conf = new Config();
		hive_conf.addProperty("hive.metastore.client.socket.timeout", String.valueOf(300));
		System.out.println(HiveUtils.getJdbcUrl(jdbc_config, null, hive_conf, null, true));

		Config session_config = new Config();
		session_config.addProperty("connect", String.valueOf(30));
		System.out.println(HiveUtils.getJdbcUrl(jdbc_config, session_config, hive_conf, null, true));

		HiveSession session = null;

		try {
			session = HiveUtils.createHiveSession("conf/hive.properties", true);
			String sql = "select * from t_article_profile limit 1;";
			ResultSet rs = session.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}

			SqlUtils.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HiveUtils.closeHiveSession(session);
		}

	}

}
