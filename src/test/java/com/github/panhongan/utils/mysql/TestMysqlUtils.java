package com.github.panhongan.utils.mysql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.github.panhongan.utils.conf.Config;
import com.github.panhongan.utils.sql.SqlUtils;

/**
 * panhongan plus
 */

public class TestMysqlUtils {

	public static void main(String[] args) {
		Config config = new Config();
		config.parse("conf/mysql.properties");
		System.out.println(config.toString());
		System.out.println(MysqlUtils.getJDBCUrl(config));

		MysqlUtils.MysqlPool pool = MysqlUtils.createMysqlPool(config);
		if (pool != null) {
			try {
				Connection conn = pool.getConnection();
				MysqlSession session = new MysqlSession(conn);
				String sql = "select * from t_history_task limit 1";
				ResultSet rs = session.executeQuery(sql);
				while (rs.next()) {
					System.out.println(rs.getString(2));
				}

				SqlUtils.closeResultSet(rs);
			} catch (Exception e) {
				e.printStackTrace();
			}

			MysqlUtils.closeMysqlPool(pool);
		}
	}

}
