package ch.fhnw.model;

public interface CardioWorkout extends Workout {

    double getDistance();

    default double getAverageSpeed() {
        if (getDuration() == 0) return 0.0;
        return (getDistance() / getDuration()) * 60.0;
    }
}