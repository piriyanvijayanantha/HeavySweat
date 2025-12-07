package ch.fhnw.domain.impl;

import com.fitnesstracker.domain.CardioWorkout;

/**
 * Konkrete Implementierung eines Lauf-Workouts.
 * Implementiert das CardioWorkout-Interface.
 *
 * @author PROG1 Team - Person 2
 * @version 1.0
 */
public class Running implements CardioWorkout {

    private int id;
    private String name;
    private int duration; // in Minuten
    private double distance; // in Kilometern

    /**
     * Konstruktor für ein Running-Workout.
     *
     * @param name Name des Workouts
     * @param duration Dauer in Minuten
     * @param distance Distanz in Kilometern
     */
    public Running(String name, int duration, double distance) {
        this.id = -1; // noch nicht in DB gespeichert
        this.name = name;
        this.duration = duration;
        this.distance = distance;
    }

    /**
     * Konstruktor für Workout aus Datenbank (mit ID).
     *
     * @param id Workout-ID
     * @param name Name des Workouts
     * @param duration Dauer in Minuten
     * @param distance Distanz in Kilometern
     */
    public Running(int id, String name, int duration, double distance) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.distance = distance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getCategory() {
        return "Cardio";
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public double getCalories() {
        // Formel: Distanz × 60 (ca. 60 kcal pro km beim Laufen)
        return distance * 60.0;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %d min, %.2f km, %.0f kcal, %.1f km/h",
                getCategory(), name, duration, distance, getCalories(), getAverageSpeed());
    }
}