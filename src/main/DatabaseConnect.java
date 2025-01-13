package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=FINAL_MANAGE_HOSPITAL;encrypt=false;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "1234";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
