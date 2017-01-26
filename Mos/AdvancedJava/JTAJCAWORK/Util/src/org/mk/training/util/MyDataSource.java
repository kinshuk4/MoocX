package org.mk.training.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataSource {

    private MyDataSource() {
    }
    private static Connection con = null;

    public static Connection getConnection() throws SQLException {
        if (con == null) {
            con = DriverManager.getConnection(DataSourceConfig.URL, DataSourceConfig.USERNAME, DataSourceConfig.PASSWORD);
        }
        return con;
    }

    public static Connection getConnection(boolean newcon) throws SQLException {
        if (newcon) {
            return DriverManager.getConnection(DataSourceConfig.URL, DataSourceConfig.USERNAME, DataSourceConfig.PASSWORD);
        } else {
            return con;
        }
    }

    static {
        try {
            Class s = Class.forName(DataSourceConfig.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
