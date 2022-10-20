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


    public void pickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        elevatorSpecificPickUp(fromFloor, upButtonPressed, toFloor);
        calculateNextAction();
    }

    protected abstract void elevatorSpecificPickUp(int fromFloor, boolean upButtonPressed, Integer toFloor);

    protected abstract void elevatorSpecificPickUp(int toFloor);


    public void pickUp(int toFloor) {
        elevatorSpecificPickUp(toFloor);
        calculateNextAction();
    }

    public void step() {
        elevatorSpecificStep();
        calculateNextAction();
    }

    protected abstract void elevatorSpecificStep();

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
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + getDestinationFloor() +
                ", nextAction=" + nextAction;
    }
}
