package org.example;

import org.example.fcfs.FCFSManager;
import org.example.nearestindirection.NearestInDirectionManager;
import org.example.paternoster.NeverStopManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        SystemManager systemManager = new SystemManager();
        System.out.println("=== Elevator System ===");
        int input;
        while (true) {
            System.out.print("An elevator group is at least one elevator working with a mutual inquiries scheduling algorithm\n" +
                    "Enter the number of elevator groups:");
            try {
                input = Integer.parseInt(s.nextLine());
                if (input < 0) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                System.out.println("Enter a number greater than 0");
            }
        }

        int type, numberOfElevators, numberOfPositiveFloors, numberOfNegativeFloors;
        for (int i = 0; i < input; i++) {
            System.out.println("-- Group " + i + " ---");
            try {
                System.out.println("Enter Manager type:\n0: NeverStopManager\n1: NearestInDirectionManager\n2: FCFSManager");
                type = Integer.parseInt(s.nextLine());
                System.out.print("Enter numberOfElevators: ");
                numberOfElevators = Integer.parseInt(s.nextLine());
                System.out.print("Enter numberOfPositiveFloors: ");
                numberOfPositiveFloors = Integer.parseInt(s.nextLine());
                System.out.print("Enter numberOfNegativeFloors: ");
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
                        throw new IllegalArgumentException("Type (" + type + ") must be between 0 and 2");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                i--;
            }
        }
        System.out.println("+++ End of configuration +++");
        System.out.println(systemManager);
        System.out.println("---Simulation---");
        printAvailableCommands();
        String[] command;
        while (true) {
            try {
                System.out.println("Enter a command");
                command = s.nextLine().split(" ");
                if (command.length == 1) {
                    if (command[0].equals("s")) systemManager.step();
                    else if (command[0].equals("h")){
                        printAvailableCommands();
                        continue;
                    }
                    else if (command[0].equals("q")) break;
                } else if (command.length == 3) {
                    systemManager.pickUp(Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Integer.parseInt(command[2]));
                } else if (command.length == 4) {
                    systemManager.pickUp(Integer.parseInt(command[0]),
                            Integer.parseInt(command[1]),
                            Boolean.parseBoolean(command[2]),
                            acceptNullableInteger(command[3]));
                } else throw new IllegalArgumentException("Unknown command");
                System.out.println(systemManager);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Quiting ...");
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
}
