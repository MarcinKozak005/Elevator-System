package org.example;

import org.example.paternoster.NeverStopElevator;
import org.example.paternoster.NeverStopManager;
import org.example.states.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NeverStopManagerTest {

    @Test
    public void managerCreation() {
        NeverStopManager asm = new NeverStopManager(1, 5, 5);
        assertEquals(1, asm.getElevators().size());
    }

    @Test
    public void topDirectionSwap() {
        NeverStopElevator e;
        NeverStopManager asm = new NeverStopManager(1, 3, 3);
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(1,e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(2,e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(3,e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(2,e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
    }

    @Test
    public void bottomDirectionSwap(){
        NeverStopElevator e;
        NeverStopManager asm = new NeverStopManager(1,0,3);
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(-1,e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(-2,e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(-3,e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        asm.step();
        e = asm.getElevators().get(0);
        assertEquals(-2,e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());

    }
}
