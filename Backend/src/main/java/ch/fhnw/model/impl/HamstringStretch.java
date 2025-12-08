package ch.fhnw.model.impl;

import ch.fhnw.model.StretchWorkout;

public class HamstringStretch implements StretchWorkout {

    private int id;
    private String name;
    private int duration; // in Minuten
    private String muscleGroup;

    public HamstringStretch(String name, int duration, String muscleGroup) {
        this.id = -1;
        this.name = name;
        this.duration = duration;
        this.muscleGroup = muscleGroup;
    }

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