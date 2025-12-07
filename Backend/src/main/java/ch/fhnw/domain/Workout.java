package ch.fhnw.domain;

/**
 * Basisinterface für alle Workout-Typen.
 * Definiert die grundlegenden Eigenschaften und Methoden,
 * die jedes Workout implementieren muss.
 *
 * @author PROG1 Team
 * @version 1.0
 */
public interface Workout {

    /**
     * Gibt den Namen des Workouts zurück.
     *
     * @return Name des Workouts als String
     */
    String getName();

    /**
     * Gibt die Dauer des Workouts in Minuten zurück.
     *
     * @return Dauer in Minuten
     */
    int getDuration();

    /**
     * Gibt die Kategorie des Workouts zurück.
     * Mögliche Werte: "Cardio", "Strength", "Stretch"
     *
     * @return Kategorie als String
     */
    String getCategory();

    /**
     * Berechnet die verbrannten Kalorien für dieses Workout.
     * Die Berechnung ist spezifisch für jeden Workout-Typ.
     *
     * @return Anzahl verbrannter Kalorien
     */
    double getCalories();

    /**
     * Gibt die eindeutige ID des Workouts zurück.
     *
     * @return Workout-ID oder -1 wenn noch nicht in DB gespeichert
     */
    int getId();

    /**
     * Setzt die ID des Workouts (wird von Repository verwendet).
     *
     * @param id Die zu setzende ID
     */
    void setId(int id);
}