package com.github.panhongan.util.db;

import java.sql.Connection;
import java.sql.ResultSet;

import com.github.panhongan.util.StringUtil;
import com.github.panhongan.util.conf.Config;
import com.github.panhongan.util.db.MysqlSession;
import com.github.panhongan.util.db.MysqlUtil;
import com.github.panhongan.util.db.MysqlUtil.MysqlPool;
import com.github.panhongan.util.sql.SqlUtil;

public class TestMysqlUtil {
	
	public static void main(String [] args) {
		Config config = new Config();
		if (config.parse("conf/mysql.properties")) {
			System.out.println(config.toString());
			
			System.out.println(MysqlUtil.getJDBCUrl(config));
		} else {
			System.err.println("parse conf file failed");
			return;
		}
		
		MysqlPool pool = MysqlUtil.createMysqlPool(config);
		if (pool != null) {
			try {
				Connection conn = pool.getConnection();
				System.out.println(StringUtil.toString(conn));
				
				MysqlSession session = new MysqlSession(conn);
				String sql = "select * from t_history_task limit 1";
				ResultSet rs = session.executeQuery(sql);
				while (rs.next()) {
					System.out.println(rs.getString(2));
				}
				
				SqlUtil.closeResultSet(rs);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			MysqlUtil.closeMysqlPool(pool);
		}
	}

}
