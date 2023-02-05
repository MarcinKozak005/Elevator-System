package org.example;

import org.example.nearestindirection.NearestInDirectionElevator;
import org.example.states.Action;
import org.example.states.Direction;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestInDirectionElevatorTest {

    @Test
    public void elevatorCreation() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.NONE, e.getDirection());
        assertEquals(Action.IDLE, e.getNextAction());
    }

    @Test
    public void noPickUpStep() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.NONE, e.getDirection());
        assertEquals(Action.IDLE, e.getNextAction());
    }

    @Test
    public void pickCurrentStep() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(0, true, 2);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UP, e.getNextAction());
    }

    @Test
    public void pickAboveStep() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(2, true, 3);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UP, e.getNextAction());
    }

    @Test
    public void pickBelowStep() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(-2, false, -3);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(-1, e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        assertEquals(Action.DOWN, e.getNextAction());
    }

    @Test
    public void arriveToUnload() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(1, true, 2);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UNLOAD, e.getNextAction());
    }

    @Test
    public void callOnCurrentFloor() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(0, true, 1);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UNLOAD, e.getNextAction());
    }

    @Test
    public void noToFloorCall() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(1, true, null);
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UNLOAD, e.getNextAction());
        e.step();
        assertEquals(1, e.getId());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Direction.NONE, e.getDirection());
        assertEquals(Action.IDLE, e.getNextAction());
    }

    @Test
    public void noFromFloorCall0() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(0);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        assertEquals(Action.UNLOAD, e.getNextAction());
    }

    @Test
    public void noFromFloorCall1() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(1);
        assertEquals(1, e.getId());
        assertEquals(0, e.getCurrentFloor());
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(Action.UP, e.getNextAction());
    }

    @Test
    public void callToGoUpButUserGoesDown() {
        NearestInDirectionElevator e = new NearestInDirectionElevator(1, 0);
        e.pickUp(1, true, 4);
        e.pickUp(2, true, -3);
        e.step(); // 1 UNLOAD
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Action.UNLOAD, e.getNextAction());
        e.step(); // 1 UP
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(1, e.getCurrentFloor());
        assertEquals(Action.UP, e.getNextAction());
        e.step(); // 2 UNLOAD
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(2, e.getCurrentFloor());
        assertEquals(Action.UNLOAD, e.getNextAction());
        e.step(); // 2 UP
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(2, e.getCurrentFloor());
        assertEquals(Action.UP, e.getNextAction());
        e.step(); // 3 UP
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(3, e.getCurrentFloor());
        assertEquals(Action.UP, e.getNextAction());
        e.step(); // 4 UNLOAD
        assertEquals(Direction.UP, e.getDirection());
        assertEquals(4, e.getCurrentFloor());
        assertEquals(Action.UNLOAD, e.getNextAction());
        e.step(); // 4 DOWN
        assertEquals(4, e.getCurrentFloor());
        assertEquals(Direction.DOWN, e.getDirection());
        assertEquals(OptionalInt.of(-3), e.getDestinationFloor());
    }

}
