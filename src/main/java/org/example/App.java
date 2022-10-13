package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        // Test: 1
        ElevatorManager em = new ElevatorManager((byte) 1, (short) 5, (short) 5);
        em.pickUp((short) 3,true, (short)5);

        // Run loop
        Scanner s = new Scanner(System.in);
        String input;
        int i = 0;
        System.out.println(em);
        while (true) {
            em.step();
            System.out.println("---"+(i++)+"---");
            System.out.println(em);
            input = s.next();
            if (input.equals("+")){em.pickUp((short) -2,true, (short)1);}
            else if(input.equals("q")) break;
        }
        s.close();
    }
}
