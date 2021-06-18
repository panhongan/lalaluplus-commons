package com.github.panhongan.utils.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lalalu plus
 * @version 1.0
 * @since 2017.7.10
 */

@Slf4j
public class SqlUtils {

    /**
     * @param conn Connection
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }

    /**
     * @param stmt Statement
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }

    /**
     * @param rs ResultSet
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }
}
