package org.example.paternoster;

import org.example.Elevator;
import org.example.states.Action;
import org.example.states.Direction;

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

    @Override
    protected boolean hasNoMoreInquires() {
        return false;
    }

    @Override
    protected void doIfNoMoreInquires(int fromFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void doIfBadDirectionUserInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void standardInquiryHandling(int fromFloor, boolean upButtonPressed, Integer toFloor) {
    }

    @Override
    protected void noToFloorInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {

    }

    @Override
    protected void doIfNoMoreInquiresNoFromFloor(int toFloor) {
    }

    @Override
    protected boolean isIncorrectUserInquiryNoFromFloor(int toFloor) {
        return false;
    }

    @Override
    protected void doIfBadDirectionUserInquiryNoFromFloor(int toFloor) {

    }

    @Override
    protected void standardInquiryHandlingNoFromFloor(int toFloor) {

    }

    @Override
    protected void specificStep() {
        calculateNextAction();
        if (nextAction == Action.UP) currentFloor++;
        else if (nextAction == Action.DOWN) currentFloor--;
    }

    void calculateDestination(int numberOfPositiveFloors, int numberOfNegativeFloors){
        if(currentFloor == numberOfPositiveFloors && direction == Direction.UP) direction = Direction.DOWN;
        else if(currentFloor == -numberOfNegativeFloors && direction == Direction.DOWN) direction = Direction.UP;
    }

    @Override
    public Integer getDestinationFloor() {
        if (direction == Direction.UP) return currentFloor + 1;
        else if (direction == Direction.DOWN) return currentFloor - 1;
        else return null;
    }
}
