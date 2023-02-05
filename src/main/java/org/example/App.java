package org.example;

import org.example.fcfs.FCFSManager;
import org.example.nearestindirection.NearestInDirectionManager;
import org.example.paternoster.NeverStopManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (FileInputStream fs = new FileInputStream("src/main/resources/strings.properties")) {
            properties.load(fs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printString(properties,"header.elevatorSystem");
        Scanner s = new Scanner(System.in);
        SystemManager systemManager = new SystemManager();
        System.out.println();
        int input;
        while (true) {
            printString(properties,"elevatorGroup.explanation");
            printString(properties,"enter.elevatorGroupNumber");
            try {
                input = Integer.parseInt(s.nextLine());
                if (input < 0) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                printString(properties,"warning.enterNumberGreaterThan0");
            }
        }

        int type, numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors;
        for (int i = 0; i < input; i++) {
            printFormattedString(properties, "header.group", i);
            try {
                printString(properties,"enter.managerType");
                printString(properties,"ns.manager");
                printString(properties,"nid.manager");
                printString(properties,"fcfs.manager");
                type = Integer.parseInt(s.nextLine());
                printString(properties,"enter.numberOfElevators");
                numberOfElevators = Integer.parseInt(s.nextLine());
                printString(properties,"enter.numberOfPositiveFloors");
                numberOfPositiveFloors = Integer.parseInt(s.nextLine());
                printString(properties, "enter.numberOfNegativeFloors");
                numberOfNegativeFloors = Integer.parseInt(s.nextLine());
                switch (type) {
                    case 0:
                        systemManager.addGroup(new NeverStopManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                    case 1:
                        systemManager.addGroup(new NearestInDirectionManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                    case 2:
                        systemManager.addGroup(new FCFSManager(numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors));
                        break;
                    default:
                        throw new IllegalArgumentException(MessageFormat.format(properties.getProperty("error.inputBetween0and2"),type));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i--;
            }
        }
        printString(properties,"header.endOfConfig");
        System.out.println(systemManager);
        printString(properties,"header.simulation");
        printAvailableCommands();
        String[] command;
        while (true) {
            try {
                printString(properties,"enter.command");
                command = s.nextLine().split(" ");
                if (command.length == 1) {
                    if (command[0].equals("s")) systemManager.step();
                    else if (command[0].equals("h")) {
                        printAvailableCommands();
                        continue;
                    } else if (command[0].equals("q")) break;
                } else if (command.length == 3) {
                    systemManager.pickUp(Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Integer.parseInt(command[2]));
                } else if (command.length == 4) {
                    systemManager.pickUp(Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Boolean.parseBoolean(command[2]),
                            acceptNullableInteger(command[3]));
                } else throw new IllegalArgumentException(properties.getProperty("error.unknownCommand"));
                System.out.println(systemManager);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        printString(properties,"quiting");
    }

    static Integer acceptNullableInteger(String numberToParse) {
        try {
            return Integer.parseInt(numberToParse);
        } catch (Exception e) {
            return null;
        }
    }

    static void printAvailableCommands() {
        System.out.println("Available commands:\n" +
                "s - next step of the simulation\n" +
                "X Y Z W - to the group number X, add a inquiry from floor Y to floor W (Z indicates if upButton was pressed)\n" +
                "\t Example: 1 3 true 5\n" +
                "X Y Z  - to the group number X, add a inquiry for elevator Y to go to floor Z\n" +
                "\t Example: 1 2 10\n" +
                "h - show available commands\n" +
                "q - quit");
    }

    static void printString(Properties p, String propertyKey){
        System.out.println(p.getProperty(propertyKey));
    }

    static void printFormattedString(Properties p, String propertyKey, Object o){
        System.out.println(MessageFormat.format(p.getProperty(propertyKey), o));
    }
}
