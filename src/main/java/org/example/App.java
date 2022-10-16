package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ElevatorManager em = new ElevatorManager(1, 5, 5);
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        while (true) {
            System.out.println("---" + (i++) + "---");
            System.out.println(em);
            input = s.next();
            if (input.equals("+")) {
                em.pickUp(1, false, -5);
            } else if (input.equals("q")) break;
            else if (input.equals("n")) em.step();
        }
        s.close();
    }
}
