package org.example;

import org.example.elevatormanager.ElevatorsGroup;
import org.example.elevatormanager.GroupManager;
import org.example.elevatormanager.SystemManager;
import org.example.nearestindirection.NearestInDirectionElevator;
import org.example.nearestindirection.NearestInDirectionManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        GroupManager<NearestInDirectionElevator> gm = new NearestInDirectionManager();
        ElevatorsGroup<NearestInDirectionElevator> eg = new ElevatorsGroup<>(1, 15, 15, gm);
        SystemManager sm = new SystemManager(eg);
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        while (true) {
            System.out.println("===" + (i++) + "===");
            System.out.println(sm);
            input = s.next();
            if (input.equals("+")) {
                sm.pickUp(0, 1, false, -5);
            } else if (input.equals("1")) sm.pickUp(0, 10, false, 5);
            else if (input.equals("2")) sm.pickUp(0, 7, true, 13);
            else if (input.equals("q")) break;
            else if (input.equals("s")) sm.step();
        }
        s.close();
    }
}
