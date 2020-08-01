package com.github.panhongan.utils.hive;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.panhongan.utils.sql.SqlUtils;
import com.google.common.base.Preconditions;

/**
 * lalalu plus
 */

public class HiveSession implements Closeable {

	private Connection conn;

	private Statement stmt;

	public HiveSession(Connection connection) throws SQLException {
		this.conn = connection;
		Preconditions.checkNotNull(conn);

		stmt = conn.createStatement();
		Preconditions.checkNotNull(stmt);
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}

	public boolean executeUpdate(String sql) throws SQLException {
		return (stmt.executeUpdate(sql) >= 0);
	}

	public boolean execute(String sql) throws SQLException {
		return stmt.execute(sql);
	}

	@Override
	public void close() {
		SqlUtils.closeStatement(stmt);
		SqlUtils.closeConnection(conn);
	}

}
 