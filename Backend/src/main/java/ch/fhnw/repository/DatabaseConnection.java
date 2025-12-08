package ch.fhnw.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:fitnesstracker.db";
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection(DB_URL);
            System.out.println("✓ Datenbankverbindung hergestellt: " + DB_URL);

            createTables();

        } catch (ClassNotFoundException e) {
            System.err.println("✗ SQLite JDBC Treiber nicht gefunden!");
            System.err.println("  Füge sqlite-jdbc-3.43.0.0.jar zu deinem Projekt hinzu.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Verbinden zur Datenbank!");
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS workouts (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                category TEXT NOT NULL,
                name TEXT NOT NULL,
                duration INTEGER NOT NULL,
                distance REAL,
                sets INTEGER,
                reps INTEGER,
                muscle_group TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("✓ Datenbank-Tabellen bereit");
        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Erstellen der Tabellen!");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Datenbankverbindung geschlossen");
            }
        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Schließen der Verbindung!");
            e.printStackTrace();
        }
    }
}