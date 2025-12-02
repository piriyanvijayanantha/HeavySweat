package ch.fhnw.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_PATH = "db/mydatabase.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    private static Connection connection;

    // Singleton: Eine Connection pro Anwendung
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
                System.out.println("Connected to SQLite database.");
            } catch (SQLException e) {
                System.err.println("Error connecting to SQLite database:");
                e.printStackTrace();
            }
        }
        return connection;
    }

}
