package ch.fhnw.model;

/**
 * Interface für Krafttraining-Workouts.
 * Erweitert das Basis-Workout-Interface um Sets und Repetitions.
 *
 * @author PROG1 Team
 * @version 1.0
 */
public interface StrengthWorkout extends Workout {

    /**
     * Gibt die Anzahl der Sätze zurück.
     *
     * @return Anzahl Sätze
     */
    int getSets();

    /**
     * Gibt die Anzahl der Wiederholungen pro Satz zurück.
     *
     * @return Anzahl Wiederholungen
     */
    int getReps();

    /**
     * Berechnet die Gesamtanzahl der Wiederholungen.
     *
     * @return Gesamte Wiederholungen (Sets × Reps)
     */
    default int getTotalReps() {
        return getSets() * getReps();
    }
}