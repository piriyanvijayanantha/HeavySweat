package ch.fhnw.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton-Klasse für die SQLite-Datenbankverbindung.
 * Verwaltet die Verbindung zur Datenbank und erstellt die benötigten Tabellen.
 *
 * @author PROG1 Team - Person 3
 * @version 1.0
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:fitnesstracker.db";
    private static DatabaseConnection instance;
    private Connection connection;

    /**
     * Privater Konstruktor (Singleton Pattern).
     * Initialisiert die Datenbankverbindung und erstellt Tabellen.
     */
    private DatabaseConnection() {
        try {
            // SQLite JDBC Treiber laden
            Class.forName("org.sqlite.JDBC");

            // Verbindung zur Datenbank herstellen
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("✓ Datenbankverbindung hergestellt: " + DB_URL);

            // Tabellen erstellen falls nicht vorhanden
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

    /**
     * Gibt die Singleton-Instanz zurück.
     *
     * @return Die DatabaseConnection-Instanz
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Gibt die aktive Datenbankverbindung zurück.
     *
     * @return Connection-Objekt
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Erstellt die benötigten Tabellen in der Datenbank.
     * Verwendet IF NOT EXISTS, damit existierende Tabellen nicht überschrieben werden.
     */
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

    /**
     * Schließt die Datenbankverbindung.
     * Sollte beim Beenden der Anwendung aufgerufen werden.
     */
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