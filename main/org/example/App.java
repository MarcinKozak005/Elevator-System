package org.example;

import org.example.fcfs.FCFSManager;
import org.example.nearestindirection.NearestInDirectionManager;
import org.example.paternoster.NeverStopManager;
import org.example.utils.IntValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Scanner;

public class App {

    static Scanner scanner;
    static Properties properties;
    static SystemManager systemManager;

    static {
        scanner = new Scanner(System.in);
        properties = new Properties();
        systemManager = new SystemManager();
        try (FileInputStream fs = new FileInputStream("resources/strings.properties")) {
            properties.load(fs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        printString("header.elevatorSystem");
        System.out.println();

        printString("elevatorGroup.explanation");
        int input = parsePositiveInteger("enter.elevatorGroupNumber");

        // System configuration
        for (int groupNumber = 0; groupNumber < input; groupNumber++) {
            System.out.println(formatPropertyString("header.group", groupNumber));
            try {
                int type = parsePositiveInteger("enter.managerType", new IntValidator(n -> (n < 1 || n > 3), properties.getProperty("error.inputTypeBetween1and3")));
                int numberOfElevators = parsePositiveInteger("enter.numberOfElevators");
                int numberOfPositiveFloors = parsePositiveInteger("enter.numberOfPositiveFloors");
                int numberOfNegativeFloors = parsePositiveInteger("enter.numberOfNegativeFloors");
                switch (type) {
                    case 1:
                        systemManager.addGroup(new NeverStopManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                    case 2:
                        systemManager.addGroup(new NearestInDirectionManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                    case 3:
                        systemManager.addGroup(new FCFSManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                groupNumber--;
            }
        }
        printString("header.endOfConfig");

        // Entering commands
        System.out.println(systemManager);
        printString("header.simulation");
        printAvailableCommands();
        String[] command;
        while (true) {
            try {
                printString("enter.command");
                command = scanner.nextLine().split(" ");
                if (command.length == 1) {
                    if (command[0].equals("s")) {
                        systemManager.step();
                    } else if (command[0].equals("h")) {
                        printAvailableCommands();
                        continue;
                    } else if (command[0].equals("q")) break;
                    else
                        throw new IllegalArgumentException(properties.getProperty("error.unknownCommand"));
                } else if (command.length == 3) {
                    systemManager.pickUp(
                            Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Integer.parseInt(command[2]));
                } else if (command.length == 4) {
                    systemManager.pickUp(
                            Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Boolean.parseBoolean(command[2]),
                            acceptNullableInteger(command[3]));
                } else {
                    throw new IllegalArgumentException(properties.getProperty("error.unknownCommand"));
                }
                System.out.println(systemManager);
            } catch (Exception e) {
                System.out.println(formatPropertyString("error.placeholder", e.getMessage()));
            }
        }
        printString("quiting");
    }

    private static Integer acceptNullableInteger(String numberToParse) {
        try {
            return Integer.parseInt(numberToParse);
        } catch (Exception e) {
            return null;
        }
    }

    private static void printAvailableCommands() {
        System.out.println("Available commands:\n" +
                "h - show available commands\n" +
                "q - quit\n" +
                "s - next step of the simulation\n" +
                "Pick up orders:\n" +
                "\tN FROM UP_BUTTON_PRESSED TO - to the group number N, add a inquiry from the FROM floor, to the TO floor (UP_BUTTON_PRESSED indicates if upButton was pressed)\n" +
                "\t\t Example: 1 3 true 5\n" +
                "\tN E TO - to the group number N, add a inquiry for elevator E to go to the floor TO\n" +
                "\t\t Example: 1 2 10");
    }

    private static void printString(String propertyKey) {
        System.out.println(properties.getProperty(propertyKey));
    }

    private static String formatPropertyString(String propertyKey, Object o) {
        return MessageFormat.format(properties.getProperty(propertyKey), o);
    }

    private static int parsePositiveInteger(String propertyKey) {
        return parsePositiveInteger(propertyKey, IntValidator.emptyValidator());
    }

    private static int parsePositiveInteger(String propertyKey, IntValidator intValidator) {
        int result;
        while (true) {
            try {
                printString(propertyKey);
                result = Integer.parseInt(scanner.nextLine());
                if (result <= 0)
                    throw new IllegalArgumentException();
                intValidator.validate(result);
                break;
            } catch (NumberFormatException e) {
                printString("error.mustBeGreaterThan0");
            } catch (IllegalArgumentException e) {
                printString("error.integerMustBeGreaterThan0");
            } catch (Exception e) {
                System.out.println(formatPropertyString("error.placeholder", e.getMessage()));
            }
        }
        return result;
    }
}
