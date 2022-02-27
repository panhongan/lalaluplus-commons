package com.github.panhongan.utils.sql;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

/**
 * @author lalaluplus
 * @since 2022.1.10
 */

@Ignore
public class MysqlHolderTest {

    @Test
    public void test() throws Exception {
        MysqlHolder mysqlHolder = new MysqlHolder();

        try {
            mysqlHolder.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/dev_quality?useUnicode=true&characterEncoding=utf-8&useCursorFetch=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8", "root", "");

            // query
            String sql = "select * from t_repo_pull_request where id=1";
            System.out.println(mysqlHolder.query(sql, Collections.emptyMap(), rs -> {
                try {
                    return rs.getInt(1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysqlHolder.closeConnection();
        }
    }
}
