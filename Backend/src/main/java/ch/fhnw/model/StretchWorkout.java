package ch.fhnw.model;

/**
 * Interface für Dehnungs-Workouts.
 * Erweitert das Basis-Workout-Interface um Muskelgruppen-Informationen.
 *
 * @author PROG1 Team
 * @version 1.0
 */
public interface StretchWorkout extends Workout {

    /**
     * Gibt die betroffene Muskelgruppe zurück.
     *
     * @return Name der Muskelgruppe (z.B. "Hamstrings", "Quadriceps")
     */
    String getMuscleGroup();

    /**
     * Gibt die Intensität der Dehnung zurück.
     *
     * @return Intensität (1=leicht, 2=mittel, 3=intensiv)
     */
    default int getIntensity() {
        return 2; // Standard: mittlere Intensität
    }
}