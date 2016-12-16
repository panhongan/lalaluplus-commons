package com.github.panhongan.util.hive;

import java.sql.ResultSet;

import com.github.panhongan.util.sql.SqlUtil;

public class TestHiveUtil {
	
	public static void main(String [] args) {
		HiveSession session = null;
		
		try {
			session = HiveUtil.createHiveSession("conf/hive.properties");
			String sql = "select * from t_article_profile limit 1;";
			ResultSet rs = session.executeQuery(sql);
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
			
			SqlUtil.closeResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HiveUtil.closeHiveSession(session);
		}
			
	}

}
