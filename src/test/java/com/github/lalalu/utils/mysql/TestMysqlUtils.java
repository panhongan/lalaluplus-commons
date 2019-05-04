package com.github.lalalu.utils.mysql;

import java.sql.Connection;
import java.sql.ResultSet;

import com.github.lalalu.utils.conf.Config;
import com.github.lalalu.utils.mysql.MysqlUtils.MysqlPool;
import com.github.lalalu.utils.sql.SqlUtils;

/**
 * lalalu plus
 */

public class TestMysqlUtils {

	public static void main(String[] args) {
		Config config = new Config();
		if (config.parse("conf/mysql.properties")) {
			System.out.println(config.toString());

			System.out.println(MysqlUtils.getJDBCUrl(config));
		} else {
			System.err.println("parse conf file failed");
			return;
		}

		MysqlPool pool = MysqlUtils.createMysqlPool(config);
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
