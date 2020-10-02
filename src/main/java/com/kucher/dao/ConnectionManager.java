package com.kucher.dao;

import com.kucher.util.DBResourcesManager;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static MysqlDataSource datasource;

    private ConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        if (datasource == null) {
            initializeDataSource();
        }
        return datasource.getConnection();
    }

    private static void initializeDataSource() {
        datasource = new MysqlDataSource();

        String user = DBResourcesManager.getPropertyByName("jdbc.user");
        String password = DBResourcesManager.getPropertyByName("jdbc.password");
        String serverName = DBResourcesManager.getPropertyByName("jdbc.serverName");
        String dbName = DBResourcesManager.getPropertyByName("jdbc.dbName");
        String timezone = DBResourcesManager.getPropertyByName("jdbc.timezone");

        datasource.setUser(user);
        datasource.setPassword(password);
        datasource.setServerName(serverName);
        datasource.setDatabaseName(dbName);
        try {
            datasource.setServerTimezone(timezone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
