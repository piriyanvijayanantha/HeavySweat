package ch.fhnw.domain.impl;

import ch.fhnw.domain.StretchWorkout;

/**
 * Konkrete Implementierung eines Hamstring-Dehnungs-Workouts.
 * Implementiert das StretchWorkout-Interface.
 *
 * @author PROG1 Team - Person 2
 * @version 1.0
 */
public class HamstringStretch implements StretchWorkout {

    private int id;
    private String name;
    private int duration; // in Minuten
    private String muscleGroup;

    /**
     * Konstruktor für ein HamstringStretch-Workout.
     *
     * @param name Name des Workouts
     * @param duration Dauer in Minuten
     * @param muscleGroup Betroffene Muskelgruppe
     */
    public HamstringStretch(String name, int duration, String muscleGroup) {
        this.id = -1;
        this.name = name;
        this.duration = duration;
        this.muscleGroup = muscleGroup;
    }

    /**
     * Konstruktor für Workout aus Datenbank (mit ID).
     *
     * @param id Workout-ID
     * @param name Name des Workouts
     * @param duration Dauer in Minuten
     * @param muscleGroup Betroffene Muskelgruppe
     */
    public HamstringStretch(int id, String name, int duration, String muscleGroup) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.muscleGroup = muscleGroup;
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
        return "Stretch";
    }

    @Override
    public String getMuscleGroup() {
        return muscleGroup;
    }

    @Override
    public double getCalories() {
        // Formel: Dauer × 2 (Dehnung verbrennt wenig Kalorien)
        return duration * 2.0;
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
        return String.format("[%s] %s - %d min, Muskel: %s, %.0f kcal",
                getCategory(), name, duration, muscleGroup, getCalories());
    }
}