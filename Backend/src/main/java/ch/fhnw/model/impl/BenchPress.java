package ch.fhnw.model.impl;

import ch.fhnw.model.StrengthWorkout;

public class BenchPress implements StrengthWorkout {

    private int id;
    private String name;
    private int duration; // in Minuten
    private int sets;
    private int reps;

    public BenchPress(String name, int duration, int sets, int reps) {
        this.id = -1;
        this.name = name;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
    }

    public BenchPress(int id, String name, int duration, int sets, int reps) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.sets = sets;
        this.reps = reps;
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
        return "Strength";
    }

    @Override
    public int getSets() {
        return sets;
    }

    @Override
    public int getReps() {
        return reps;
    }

    @Override
    public double getCalories() {
        return sets * reps * 0.5;
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
        return String.format("[%s] %s - %d min, %d Sets × %d Reps = %d total, %.0f kcal",
                getCategory(), name, duration, sets, reps, getTotalReps(), getCalories());
    }
}