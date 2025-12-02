package ch.fhnw.ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInteraction {
    private static final Pattern ADD_PATTERN =
            Pattern.compile("^add\\s+(.+)\\s*/\\s*(.+)$");

    public void run() {
        System.out.println("""
                ---------
                HeavySweat App
                ---------
                Available commands:
                  load <resource>      Loads flashcards from the given resource, in TSV format,
                                       e.g., "flashcard/modules.tsv"
                  add <front> / <back> Adds a new flashcard with the given front and back text
                  learn                Starts a learning session
                  exit                 Exits the app. Warning: Flashcards will be lost!
                """);

        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print("flashcard> ");
            input = scanner.nextLine();
            if (input.matches("load .+")) {
                String path = input.substring(5);
            } else if (input.equals("learn")) {

            } else if ((input.matches(ADD_PATTERN.pattern()))) {
                Matcher m = ADD_PATTERN.matcher(input);
                if (m.matches()) {
                    String front = m.group(1);
                    String back = m.group(2);
                } else {
                    System.out.println("Wrong command");
                }
            } else if (!input.equals("exit")) {
                System.out.println("Unknown command");
            }

        }
        while (!input.equals("exit"));
    }
}
