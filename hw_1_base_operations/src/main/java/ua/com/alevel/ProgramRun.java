package ua.com.alevel;

import ua.com.alevel.endLessons.EndLessons;
import ua.com.alevel.sumNumbers.SumNumbersFromString;
import ua.com.alevel.takesAndSortingChar.TakesAndSortingCharWithString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramRun {

    public static void run() throws IOException {
        preview();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;

            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1": {
                        SumNumbersFromString sumNumbersFromString = new SumNumbersFromString(reader);
                        sumNumbersFromString.run();
                    }
                    break;
                    case "2": {
                        TakesAndSortingCharWithString takesAndSortingCharWithString = new TakesAndSortingCharWithString(reader);
                        takesAndSortingCharWithString.run();
                    }
                    break;
                    case "3": {
                        EndLessons endLessons = new EndLessons(reader);
                        endLessons.run();
                    }
                    break;
                    case "0": {
                        System.exit(0);
                    }
                    break;
                    default: {
                        run();
                    }
                }
            }

    }

    public static void isExit(String numberTask) {
        System.out.println("\nIf you want continue, please select " + numberTask +
                "\nIf you want exit to the main menu, please select enter.");
    }

    private static void preview() {
        System.out.println("if you want run first task (Sum numbers from string), please select 1");
        System.out.println("if you want run second task(Count char from string), please select 2");
        System.out.println("if you want run third task(End Lessons), please select 3");
        System.out.println("if you want run exit, please select 0");
        System.out.println("Make your choice:");
    }
}
