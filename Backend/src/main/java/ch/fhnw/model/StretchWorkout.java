package ch.fhnw.model;

public interface StretchWorkout extends Workout {

    String getMuscleGroup();

    default int getIntensity() {
        return 2; // Standard: mittlere Intensität
    }
}