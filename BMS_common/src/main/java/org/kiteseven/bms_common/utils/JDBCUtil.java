package org.kiteseven.bms_common.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtil {
    private static DruidDataSource dataSource;

    static {
        try {
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/BMS");
            dataSource.setUsername("root");
            dataSource.setPassword("240356ks");
            dataSource.setInitialSize(5); // 初始化连接池大小
            dataSource.setMaxActive(10);  // 最大连接数
            dataSource.setMinIdle(5);     // 最小空闲连接数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 关闭连接
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
