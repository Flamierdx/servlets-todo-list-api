package com.flamierd.servletstodolist.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.flamierd.servletstodolist.utils.PropertiesUtil.getProperty;

public final class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    getProperty(URL_KEY),
                    getProperty(USERNAME_KEY),
                    getProperty(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
