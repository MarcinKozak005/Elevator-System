package org.example.paternoster;

import org.example.Elevator;
import org.example.states.Action;
import org.example.states.Direction;

import java.util.OptionalInt;

/**
 * Elevator realising the Paternoster scheduling
 * It stops on every floor
 */
public class NeverStopElevator extends Elevator {
    private Direction direction;

    public NeverStopElevator(int id, int currentFloor, Direction direction) {
        super(id, currentFloor);
        this.direction = direction;
        calculateNextAction();
    }

    public Direction getDirection() {
        return direction;
    }

    public void calculateDestination(int numberOfPositiveFloors, int numberOfNegativeFloors) {
        if (currentFloor == numberOfPositiveFloors && direction == Direction.UP) direction = Direction.DOWN;
        else if (currentFloor == -numberOfNegativeFloors && direction == Direction.DOWN) direction = Direction.UP;
    }

    @Override
    public OptionalInt getDestinationFloor() {
        if (direction == Direction.UP) return OptionalInt.of(currentFloor + 1);
        else if (direction == Direction.DOWN) return OptionalInt.of(currentFloor - 1);
        else return OptionalInt.empty();
    }

    @Override
    public String toString() {
        return super.toString() + ", direction=" + direction + "}\n";
    }

    @Override
    protected void elevatorSpecificPickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void elevatorSpecificPickUp(int toFloor) {
    }

    @Override
    protected void elevatorSpecificStep() {
        calculateNextAction();
        if (nextAction == Action.UP) currentFloor++;
        else if (nextAction == Action.DOWN) currentFloor--;
    }

}
