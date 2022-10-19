package org.example;

import org.example.states.Action;
import org.example.states.Direction;
import org.example.utils.Triplet;

import java.util.ArrayList;
import java.util.TreeSet;

public class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private Action nextAction;
    private TreeSet<Integer> queue = new TreeSet<>();
    private ArrayList<Integer> buffer = new ArrayList<>();

    Elevator(int id, int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.direction = Direction.NONE;
        this.nextAction = Action.IDLE;
    }

    public Triplet<Integer, Integer, Integer> getStatus() {
        return new Triplet<>(id, currentFloor, (queue.isEmpty() ? null : queue.first()));
    }

    private void calculateNextAction() {
        if (queue.isEmpty()) nextAction = Action.IDLE;
        else if (currentFloor == queue.first()) nextAction = Action.UNLOAD;
        else if (currentFloor < queue.first()) nextAction = Action.UP;
        else if (currentFloor > queue.first()) nextAction = Action.DOWN;
    }

    private void configureQueueOrder(boolean upButtonPressed) {
        queue = (upButtonPressed) ? new TreeSet<>() : new TreeSet<>((o1, o2) -> Integer.compare(o2, o1));
    }

    public void bulkPickUp(ArrayList<Triplet<Integer, Boolean, Integer>> collection) {
        collection.forEach(t -> pickUp(t.getFst(), t.getSnd(), t.getTrd()));
        calculateNextAction();
    }

    public void pickUp(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        if (toFloor == null) toFloor = fromFloor;
        if (queue.isEmpty()) {
            configureQueueOrder(upButtonPressed);
            direction = (upButtonPressed) ? Direction.UP : Direction.DOWN;
            queue.add(toFloor);
            queue.add(fromFloor);
        } else if (fromFloor < toFloor && !upButtonPressed || fromFloor > toFloor && upButtonPressed) {
            // user called to go UP/DOWN, but goes DOWN/UP (in opposite direction)
            queue.add(fromFloor);
            buffer.add(toFloor);
        } else {
            // Standard case
            queue.add(toFloor);
            queue.add(fromFloor);
        }
        calculateNextAction();
    }

    public void pickUp(int toFloor) {
        if (queue.isEmpty()) {
            configureQueueOrder(currentFloor < toFloor);
            direction = (currentFloor < toFloor) ? Direction.UP : Direction.DOWN;
            queue.add(toFloor);
        } else if (direction == Direction.UP && currentFloor > toFloor ||
                direction == Direction.DOWN && currentFloor < toFloor) {
            buffer.add(toFloor);
        } else {
            queue.add(toFloor);
        }
        calculateNextAction();
    }

    public void step() {
        if (!queue.isEmpty()) {
            // Standard step
            if (currentFloor == queue.first()) queue.pollFirst();
            else if (currentFloor < queue.first()) currentFloor++;
            else if (currentFloor > queue.first()) currentFloor--;
        }
        if (queue.isEmpty() && !buffer.isEmpty()) {
            // Take inquiries from the buffer
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
            configureQueueOrder(direction == Direction.UP);
            queue.addAll(buffer);
            buffer.clear();
        } else if (queue.isEmpty()) {
            direction = Direction.NONE;
        }
        calculateNextAction();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public Integer getDestinationFloor() {
        return queue.isEmpty() ? null : queue.first();
    }

    public Action getNextAction() {
        return nextAction;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + getDestinationFloor() +
                ", direction=" + direction +
                ", nextAction=" + nextAction +
                ", floorsOrder=" + queue +
                "}\n";
    }
}
