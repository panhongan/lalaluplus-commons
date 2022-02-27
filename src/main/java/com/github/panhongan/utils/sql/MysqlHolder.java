package com.github.panhongan.utils.sql;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lalaluplus
 * @since 2022.1.10
 */

@Slf4j
public class MysqlHolder implements Serializable {

    private Connection connection;

    public void openConnection(String driverClassName, String jdbcUrl, String user, String password) throws Exception {
        Class.forName(driverClassName);
        connection = DriverManager.getConnection(jdbcUrl, user, password);
    }

    public <T> List<T> query(String sql, Map<Integer, String> values, Function<ResultSet, T> func) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        try {
            statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                statement.setString(entry.getKey(), entry.getValue());
            }

            rs = statement.executeQuery();
            while (rs.next()) {
                T val = func.apply(rs);
                list.add(val);
            }

            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(statement);
        }
    }

    public void query(String sql, Map<Integer, String> values, Consumer<ResultSet> consumer) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                statement.setString(entry.getKey(), entry.getValue());
            }

            rs = statement.executeQuery();
            while (rs.next()) {
                consumer.accept(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            SqlUtils.closeResultSet(rs);
            SqlUtils.closeStatement(statement);
        }
    }

    public int update(String sql, Map<Integer, String> values) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            for (Map.Entry<Integer, String> entry : values.entrySet()) {
                statement.setString(entry.getKey(), entry.getValue());
            }

            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            SqlUtils.closeStatement(statement);
        }
    }

    public void closeConnection() {
        SqlUtils.closeConnection(this.connection);
    }
}
