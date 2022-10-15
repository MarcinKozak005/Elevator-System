package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        // Test: 1
        ElevatorManager em = new ElevatorManager(2, 5,  5);
        em.pickUp( 3,true, 5);

        // Simulation loop
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        System.out.println(em);
        while (true) {
            em.step();
            System.out.println("---"+(i++)+"---");
            System.out.println(em);
            input = s.next();
            if (input.equals("+")){em.pickUp(-2,true,1);}
            else if(input.equals("q")) break;
        }
        s.close();
    }
}
