package ch.fhnw.repository;

import ch.fhnw.model.Workout;
import ch.fhnw.model.impl.BenchPress;
import ch.fhnw.model.impl.HamstringStretch;
import ch.fhnw.model.impl.Running;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository-Klasse für Workout-Verwaltung.
 * Implementiert die Datenbankoperationen (CRUD) für Workouts.
 *
 * @author PROG1 Team - Person 3
 * @version 1.0
 */
public class WorkoutRepository {

    private final Connection connection;

    /**
     * Konstruktor initialisiert Repository mit Datenbankverbindung.
     */
    public WorkoutRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();

        // Sicherheitscheck
        if (this.connection == null) {
            throw new IllegalStateException("Datenbankverbindung ist null! Bitte SQLite JDBC Driver hinzufügen.");
        }
    }

    /**
     * Fügt ein neues Workout zur Datenbank hinzu.
     *
     * @param workout Das hinzuzufügende Workout
     * @return true bei Erfolg, false bei Fehler
     */
    public boolean add(Workout workout) {
        String sql = """
            INSERT INTO workouts (category, name, duration, distance, sets, reps, muscle_group)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, workout.getCategory());
            pstmt.setString(2, workout.getName());
            pstmt.setInt(3, workout.getDuration());

            // Kategorie-spezifische Felder
            if (workout.getCategory().equals("Cardio")) {
                pstmt.setDouble(4, ((Running) workout).getDistance());
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setNull(7, Types.VARCHAR);
            } else if (workout.getCategory().equals("Strength")) {
                pstmt.setNull(4, Types.REAL);
                pstmt.setInt(5, ((BenchPress) workout).getSets());
                pstmt.setInt(6, ((BenchPress) workout).getReps());
                pstmt.setNull(7, Types.VARCHAR);
            } else { // Stretch
                pstmt.setNull(4, Types.REAL);
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.INTEGER);
                pstmt.setString(7, ((HamstringStretch) workout).getMuscleGroup());
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // ID aus Datenbank holen - SQLite-spezifische Methode
                String lastIdSql = "SELECT last_insert_rowid()";
                try (Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery(lastIdSql)) {
                    if (rs.next()) {
                        workout.setId(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Hinzufügen des Workouts!");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Lädt alle Workouts aus der Datenbank.
     *
     * @return Liste aller Workouts
     */
    public List<Workout> getAll() {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts ORDER BY created_at DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Workout workout = createWorkoutFromResultSet(rs);
                if (workout != null) {
                    workouts.add(workout);
                }
            }

        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Laden der Workouts!");
            e.printStackTrace();
        }

        return workouts;
    }

    /**
     * Lädt alle Workouts einer bestimmten Kategorie.
     *
     * @param category Die Kategorie (Cardio, Strength, Stretch)
     * @return Liste der Workouts in dieser Kategorie
     */
    public List<Workout> getByCategory(String category) {
        List<Workout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM workouts WHERE category = ? ORDER BY created_at DESC";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Workout workout = createWorkoutFromResultSet(rs);
                if (workout != null) {
                    workouts.add(workout);
                }
            }

        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Laden der Kategorie-Workouts!");
            e.printStackTrace();
        }

        return workouts;
    }

    /**
     * Zählt die Anzahl aller Workouts.
     *
     * @return Anzahl Workouts
     */
    public int count() {
        String sql = "SELECT COUNT(*) FROM workouts";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Zählen der Workouts!");
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Berechnet die Gesamtdauer aller Workouts.
     *
     * @return Gesamtdauer in Minuten
     */
    public int totalDuration() {
        String sql = "SELECT SUM(duration) FROM workouts";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("✗ Fehler beim Berechnen der Gesamtdauer!");
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Erstellt ein Workout-Objekt aus einem ResultSet.
     * Demonstriert Polymorphie: Rückgabe als Workout-Interface.
     *
     * @param rs ResultSet mit Workout-Daten
     * @return Workout-Objekt oder null bei Fehler
     */
    private Workout createWorkoutFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        String name = rs.getString("name");
        int duration = rs.getInt("duration");

        // Polymorphie: Je nach Kategorie wird die passende Klasse instanziert
        return switch (category) {
            case "Cardio" -> new Running(
                    id,
                    name,
                    duration,
                    rs.getDouble("distance")
            );
            case "Strength" -> new BenchPress(
                    id,
                    name,
                    duration,
                    rs.getInt("sets"),
                    rs.getInt("reps")
            );
            case "Stretch" -> new HamstringStretch(
                    id,
                    name,
                    duration,
                    rs.getString("muscle_group")
            );
            default -> null;
        };
    }
}