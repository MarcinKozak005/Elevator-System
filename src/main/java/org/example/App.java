package org.example;

import org.example.elevatormanager.ElevatorManager;
import org.example.elevatormanager.ElevatorsGroup;
import org.example.nearestindirection.NearestInDirectionElevator;
import org.example.nearestindirection.NearestInDirectionManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ElevatorManager<NearestInDirectionElevator> em = new NearestInDirectionManager();
        ElevatorsGroup<NearestInDirectionElevator> eg = new ElevatorsGroup<>(1, 15, 15,em);
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        while (true) {
            System.out.println("---" + (i++) + "---");
            System.out.println(eg);
            input = s.next();
            if (input.equals("+")) {
                eg.pickUp(1, false, -5);
            }
            else if (input.equals("1")) eg.pickUp(10,false, 5);
            else if (input.equals("2")) eg.pickUp(7,true, 13);
            else if (input.equals("q")) break;
            else if (input.equals("s")) eg.step();
        }
        s.close();
    }
}
