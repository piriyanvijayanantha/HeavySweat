package ch.fhnw;

import com.fitnesstracker.repository.DatabaseConnection;
import com.fitnesstracker.ui.ConsoleUI;

/**
 * Hauptklasse des Fitnesstracker-Programms.
 * Startet die Anwendung und verwaltet den Lebenszyklus.
 *
 * @author PROG1 Team
 * @version 1.0
 */
public class Main {

    /**
     * Main-Methode - Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        try {
            // UI starten
            ConsoleUI ui = new ConsoleUI();
            ui.start();

        } catch (Exception e) {
            System.err.println("\n✗ Unerwarteter Fehler aufgetreten!");
            e.printStackTrace();

        } finally {
            // Datenbankverbindung ordnungsgemäß schließen
            DatabaseConnection.getInstance().close();
        }
    }
}