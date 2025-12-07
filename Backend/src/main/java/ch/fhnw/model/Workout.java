package ch.fhnw.model;

public interface Workout {

    String getName();
    int getDuration();
    String getCategory();
    double getCalories();
    int getId();
    void setId(int id);
}