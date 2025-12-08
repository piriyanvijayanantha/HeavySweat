package ch.fhnw.ui;

import ch.fhnw.model.Workout;
import ch.fhnw.model.impl.BenchPress;
import ch.fhnw.model.impl.HamstringStretch;
import ch.fhnw.model.impl.Running;
import ch.fhnw.repository.WorkoutRepository;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final WorkoutRepository repository;
    private final Scanner scanner;
    private boolean running;

    public ConsoleUI() {
        this.repository = new WorkoutRepository();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void start() {
        printWelcome();

        while (running) {
            printMenu();
            int choice = readInt("Deine Wahl: ");
            System.out.println();

            switch (choice) {
                case 1 -> addWorkout();
                case 2 -> showAllWorkouts();
                case 3 -> showByCategory();
                case 4 -> showStatistics();
                case 5 -> exit();
                default -> System.out.println("⚠ Ungültige Eingabe! Bitte 1-5 wählen.\n");
            }
        }
    }

    private void printWelcome() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   🏋️  FITNESSTRACKER MINI v1.0  🏃   ║");
        System.out.println("║      PROG1 Projekt - Team XY        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
    }

    private void printMenu() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  HAUPTMENÜ");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  1 → Workout erfassen");
        System.out.println("  2 → Alle Workouts anzeigen");
        System.out.println("  3 → Nach Kategorie filtern");
        System.out.println("  4 → Statistiken anzeigen");
        System.out.println("  5 → Programm beenden");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private void addWorkout() {
        System.out.println("═══ WORKOUT ERFASSEN ═══\n");
        System.out.println("Wähle Kategorie:");
        System.out.println("  1 → Cardio (z.B. Running)");
        System.out.println("  2 → Strength (z.B. BenchPress)");
        System.out.println("  3 → Stretch (z.B. Dehnung)");
        System.out.println();

        int category = readInt("Kategorie: ");
        System.out.println();

        Workout workout = switch (category) {
            case 1 -> createRunning();
            case 2 -> createBenchPress();
            case 3 -> createStretch();
            default -> {
                System.out.println("⚠ Ungültige Kategorie!\n");
                yield null;
            }
        };

        if (workout != null) {
            if (repository.add(workout)) {
                System.out.println("✓ Workout erfolgreich gespeichert!");
                System.out.println("  " + workout);
            } else {
                System.out.println("✗ Fehler beim Speichern!");
            }
        }

        System.out.println();
    }

    private Running createRunning() {
        System.out.println("─── Cardio: Running ───");
        String name = readString("Name (z.B. 'Morgen-Joggen'): ");
        int duration = readInt("Dauer (Minuten): ");
        double distance = readDouble("Distanz (km): ");

        return new Running(name, duration, distance);
    }

    private BenchPress createBenchPress() {
        System.out.println("─── Strength: BenchPress ───");
        String name = readString("Name (z.B. 'Bankdrücken Heavy'): ");
        int duration = readInt("Dauer (Minuten): ");
        int sets = readInt("Anzahl Sätze: ");
        int reps = readInt("Wiederholungen pro Satz: ");

        return new BenchPress(name, duration, sets, reps);
    }

    private HamstringStretch createStretch() {
        System.out.println("─── Stretch: Dehnung ───");
        String name = readString("Name (z.B. 'Bein-Dehnung'): ");
        int duration = readInt("Dauer (Minuten): ");
        String muscleGroup = readString("Muskelgruppe (z.B. 'Hamstrings'): ");

        return new HamstringStretch(name, duration, muscleGroup);
    }

    private void showAllWorkouts() {
        System.out.println("═══ ALLE WORKOUTS ═══\n");

        List<Workout> workouts = repository.getAll();

        if (workouts.isEmpty()) {
            System.out.println("  Noch keine Workouts erfasst.\n");
            return;
        }

        for (int i = 0; i < workouts.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, workouts.get(i));
        }

        System.out.println("\nGesamt: " + workouts.size() + " Workouts\n");
    }

    private void showByCategory() {
        System.out.println("═══ NACH KATEGORIE FILTERN ═══\n");
        System.out.println("  1 → Cardio");
        System.out.println("  2 → Strength");
        System.out.println("  3 → Stretch");
        System.out.println();

        int choice = readInt("Kategorie: ");
        System.out.println();

        String category = switch (choice) {
            case 1 -> "Cardio";
            case 2 -> "Strength";
            case 3 -> "Stretch";
            default -> {
                System.out.println("⚠ Ungültige Wahl!\n");
                yield null;
            }
        };

        if (category == null) return;

        List<Workout> workouts = repository.getByCategory(category);

        System.out.println("─── " + category + "-Workouts ───\n");

        if (workouts.isEmpty()) {
            System.out.println("  Keine Workouts in dieser Kategorie.\n");
            return;
        }

        for (int i = 0; i < workouts.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, workouts.get(i));
        }

        System.out.println("\nGesamt: " + workouts.size() + " Workouts\n");
    }

    private void showStatistics() {
        System.out.println("═══ STATISTIKEN ═══\n");

        int totalWorkouts = repository.count();
        int totalMinutes = repository.totalDuration();

        if (totalWorkouts == 0) {
            System.out.println("  Noch keine Workouts erfasst.\n");
            return;
        }

        List<Workout> allWorkouts = repository.getAll();
        double totalCalories = allWorkouts.stream()
                .mapToDouble(Workout::getCalories)
                .sum();

        int cardioCount = repository.getByCategory("Cardio").size();
        int strengthCount = repository.getByCategory("Strength").size();
        int stretchCount = repository.getByCategory("Stretch").size();

        System.out.println("┌────────────────────────────────────┐");
        System.out.printf("│ Gesamt Workouts:     %13d │%n", totalWorkouts);
        System.out.printf("│ Gesamtdauer:         %10d min │%n", totalMinutes);
        System.out.printf("│ Kalorien gesamt:     %10.0f kcal │%n", totalCalories);
        System.out.println("├────────────────────────────────────┤");
        System.out.printf("│ Cardio:              %13d │%n", cardioCount);
        System.out.printf("│ Strength:            %13d │%n", strengthCount);
        System.out.printf("│ Stretch:             %13d │%n", stretchCount);
        System.out.println("└────────────────────────────────────┘");
        System.out.println();
    }

    private void exit() {
        System.out.println("═══ PROGRAMM BEENDEN ═══\n");
        System.out.println("Danke fürs Nutzen des Fitnesstrackers!");
        System.out.println("Bleib fit! 💪\n");
        running = false;
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Bitte eine gültige Zahl eingeben!");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().replace(',', '.');
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Bitte eine gültige Zahl eingeben!");
            }
        }
    }
}