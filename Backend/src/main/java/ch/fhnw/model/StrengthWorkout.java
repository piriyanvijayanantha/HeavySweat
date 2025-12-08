package ch.fhnw.model;

public interface StrengthWorkout extends Workout {

    int getSets();

    int getReps();

    default int getTotalReps() {
        return getSets() * getReps();
    }
}