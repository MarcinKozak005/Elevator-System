package org.example;

import org.example.utils.Triplet;
import org.example.utils.Tuple;

import java.util.ArrayList;
import java.util.TreeSet;

public class Elevator {
    private int id;
    private int currentFloor;
    private Action nextAction;
    private TreeSet<Integer> queue = new TreeSet<>();

    Elevator(int id, int currentFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.nextAction = Action.IDLE;
    }

    public Triplet<Integer, Integer, Integer> getStatus() {
        return new Triplet<>(id, currentFloor, (queue.isEmpty() ? null : queue.first()));
    }

    private void calculateNextAction() {
        if (queue.isEmpty()) nextAction = Action.IDLE;
        else if (currentFloor == queue.first()) {
            nextAction = Action.UNLOAD;
            queue.pollFirst();
        } else nextAction = (queue.first() > currentFloor) ? Action.UP : Action.DOWN;
    }

    private void configureQueueOrder(int fromFloor, int toFloor) {
        queue = (fromFloor < toFloor) ? new TreeSet<>() : new TreeSet<>((o1, o2) -> Integer.compare(o2, o1));
    }

    public void bulkPickUp(ArrayList<Tuple<Integer, Integer>> collection) {
        collection.forEach(t -> pickUp(t.getFst(), t.getSnd()));
    }

    public void pickUp(int fromFloor, int toFloor) {
        if (queue.isEmpty()) configureQueueOrder(fromFloor, toFloor);
        queue.add(toFloor);
        queue.add(fromFloor);
        calculateNextAction();
    }

    public void step() {
        if (nextAction == Action.UP) currentFloor++;
        else if (nextAction == Action.DOWN) currentFloor--;
        calculateNextAction();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Action getNextAction() {
        return nextAction;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + (queue.isEmpty() ? null : queue.first()) +
                ", nexAction=" + nextAction +
                ", floorsOrder=" + queue +
                "}\n";
    }
}
