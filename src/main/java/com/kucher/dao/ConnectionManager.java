package com.kucher.dao;

import com.kucher.util.DBResourcesManager;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class.getName());

    private static MysqlDataSource datasource;

    private ConnectionManager() {
    }

    public static synchronized Connection getConnection() throws SQLException {
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
        String encoding = DBResourcesManager.getPropertyByName("jdbc.encoding");

        datasource.setUser(user);
        datasource.setPassword(password);
        datasource.setServerName(serverName);
        datasource.setDatabaseName(dbName);
        try {
            datasource.setCharacterEncoding(encoding);
            datasource.setServerTimezone(timezone);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            e.printStackTrace();
        }
    }
}
