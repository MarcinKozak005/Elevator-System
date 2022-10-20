package org.example;

import org.example.states.Action;
import org.example.utils.Triplet;

import java.util.ArrayList;

public abstract class Elevator {
    protected int id;
    protected int currentFloor;
    protected Action nextAction;

    public Elevator(int id, int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.nextAction = Action.IDLE;
    }

    public Triplet<Integer, Integer, Integer> getStatus() {
        return new Triplet<>(id, currentFloor, getDestinationFloor());
    }

    protected void calculateNextAction() {
        Integer destinationFloor = getDestinationFloor();
        if (destinationFloor == null) nextAction = Action.IDLE;
        else if (currentFloor == destinationFloor) nextAction = Action.UNLOAD;
        else if (currentFloor < destinationFloor) nextAction = Action.UP;
        else /*(currentFloor > destinationFloor)*/ nextAction = Action.DOWN;
    }

    public void bulkPickUp(ArrayList<Triplet<Integer, Boolean, Integer>> collection) {
        collection.forEach(t -> pickUp(t.getFst(), t.getSnd(), t.getTrd()));
        calculateNextAction();
    }

    protected abstract boolean hasNoMoreInquires();

    protected abstract void doIfNoMoreInquires(int fromFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void doIfBadDirectionUserInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void standardInquiryHandling(int fromFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void noToFloorInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor);

    boolean isIncorrectUserInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        // user called to go UP/DOWN, but goes DOWN/UP (in opposite direction)
        return fromFloor < toFloor && !upButtonPressed || fromFloor > toFloor && upButtonPressed;
    }

    public void pickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        if (toFloor == null)
            noToFloorInquiry(fromFloor, upButtonPressed, toFloor);
        else if (hasNoMoreInquires())
            doIfNoMoreInquires(fromFloor, upButtonPressed, toFloor);
        else if (isIncorrectUserInquiry(fromFloor, upButtonPressed, toFloor))
            doIfBadDirectionUserInquiry(fromFloor, upButtonPressed, toFloor);
        else standardInquiryHandling(fromFloor, upButtonPressed, toFloor);
        calculateNextAction();
    }

    protected abstract void doIfNoMoreInquiresNoFromFloor(int toFloor);

    protected abstract boolean isIncorrectUserInquiryNoFromFloor(int toFloor);

    protected abstract void doIfBadDirectionUserInquiryNoFromFloor(int toFloor);

    protected abstract void standardInquiryHandlingNoFromFloor(int toFloor);

    public void pickUp(int toFloor) {
        if (hasNoMoreInquires())
            doIfNoMoreInquiresNoFromFloor(toFloor);
        else if (isIncorrectUserInquiryNoFromFloor(toFloor))
            doIfBadDirectionUserInquiryNoFromFloor(toFloor);
        else
            standardInquiryHandlingNoFromFloor(toFloor);
        calculateNextAction();
    }

    protected abstract void specificStep();

    public void step() {
        specificStep();
        calculateNextAction();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public abstract Integer getDestinationFloor();

    public Action getNextAction() {
        return nextAction;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + getDestinationFloor() +
                ", nextAction=" + nextAction;
    }
}
