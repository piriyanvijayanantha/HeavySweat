package ch.fhnw.domain;

/**
 * Interface für Cardio-Workouts.
 * Erweitert das Basis-Workout-Interface um Distanz-Informationen.
 *
 * @author PROG1 Team
 * @version 1.0
 */
public interface CardioWorkout extends Workout {

    /**
     * Gibt die zurückgelegte Distanz in Kilometern zurück.
     *
     * @return Distanz in Kilometern
     */
    double getDistance();

    /**
     * Berechnet die durchschnittliche Geschwindigkeit.
     *
     * @return Geschwindigkeit in km/h
     */
    default double getAverageSpeed() {
        if (getDuration() == 0) return 0.0;
        return (getDistance() / getDuration()) * 60.0;
    }
}