package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ElevatorManager em = new ElevatorManager(1, 15, 15);
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        while (true) {
            System.out.println("---" + (i++) + "---");
            System.out.println(em);
            input = s.next();
            if (input.equals("+")) {
                em.pickUp(1, false, -5);
            }
            else if (input.equals("1")) em.pickUp(10,false, 5);
            else if (input.equals("2")) em.pickUp(7,true, 13);
            else if (input.equals("q")) break;
            else if (input.equals("s")) em.step();
        }
        s.close();
    }
}
