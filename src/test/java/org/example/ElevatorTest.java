package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ElevatorTest {

    @Test
    public void elevatorCreation() {
        Elevator e = new Elevator(1, 0);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Action.IDLE, e.getNextAction());
    }

    @Test
    public void noPickUp_step() {
        Elevator e = new Elevator(1, 0);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Action.IDLE, e.getNextAction());
    }

    @Test
    public void pickAbove_step() {
        Elevator e = new Elevator(1, 0);
        e.pickUp(2, 3);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Action.UP, e.getNextAction());
    }

    @Test
    public void pickBelow_step() {
        Elevator e = new Elevator(1, 0);
        e.pickUp(-2, -3);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(-1, e.getCurrentFloor());
        assertEquals(Action.DOWN, e.getNextAction());
    }

    @Test
    public void arriveToUnload() {
        Elevator e = new Elevator(1, 0);
        e.pickUp(1, 2);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Action.UNLOAD, e.getNextAction());
    }

    @Test
    public void callOnCurrentFloor() {
        Elevator e = new Elevator(1, 0);
        e.pickUp(0, 1);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Action.UNLOAD, e.getNextAction());
    }

    @Test
    public void callToGoUpButGoesDown() {
        Elevator e = new Elevator(1, 0);
        e.pickUp(1, 4);
        e.pickUp(2, -3);
        e.step(); // 1 UNLOAD
        e.step(); // 1 UP
        e.step(); // 2 UNLOAD
        e.step(); // 2 UP
        e.step(); // 3 UP
        e.step(); // 4 UNLOAD
        e.step(); // 4 DOWN
        assertEquals(4, e.getCurrentFloor());
        assertEquals(Action.DOWN, e.getNextAction());
        assertEquals(-3, (Object) e.getDestinationFloor());
    }

}
