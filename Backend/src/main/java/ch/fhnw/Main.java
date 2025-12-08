package ch.fhnw;

import ch.fhnw.repository.DatabaseConnection;
import ch.fhnw.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        try {
            ConsoleUI ui = new ConsoleUI();
            ui.start();

        } catch (Exception e) {
            System.err.println("\n✗ Unerwarteter Fehler aufgetreten!");
            e.printStackTrace();

        } finally {
            DatabaseConnection.getInstance().close();
        }
    }
}