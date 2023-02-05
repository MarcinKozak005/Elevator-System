package org.example;

import org.example.states.Action;
import org.example.utils.ElevatorStatus;
import org.example.utils.PickUpOrder;

import java.util.List;
import java.util.OptionalInt;

public abstract class Elevator {
    protected int id;
    protected int currentFloor;
    protected Action nextAction;

    public Elevator(int id, int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.nextAction = Action.IDLE;
    }

    public ElevatorStatus getStatus() {
        return new ElevatorStatus(id, currentFloor, getDestinationFloor());
    }

    protected void calculateNextAction() {
        OptionalInt destinationFloor = getDestinationFloor();
        if (!destinationFloor.isPresent()) nextAction = Action.IDLE;
        else if (currentFloor == destinationFloor.getAsInt()) nextAction = Action.UNLOAD;
        else if (currentFloor < destinationFloor.getAsInt()) nextAction = Action.UP;
        else /*(currentFloor > destinationFloor)*/ nextAction = Action.DOWN;
    }

    public void bulkPickUp(List<PickUpOrder> collection) {
        collection.forEach(order -> pickUp(order.getCallingFloor(), order.isUpButtonPressed(), order.getToFloor()));
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

    public abstract OptionalInt getDestinationFloor();

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
