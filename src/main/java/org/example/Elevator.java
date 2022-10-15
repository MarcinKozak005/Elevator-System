package org.example;

import org.example.utils.Triplet;
import org.example.utils.Tuple;

import java.util.Collection;
import java.util.TreeSet;

public class Elevator {
    int id;
    int currentFloor;
    int destinationFloor;
    State state;
    TreeSet<Integer> floorsOrder = new TreeSet<>();

    Elevator(int id, int currentFloor, int destinationFloor) {
        update(id, currentFloor, destinationFloor);
    }

    Triplet<Integer, Integer, Integer> getStatus() {
        return new Triplet<>(this.id, this.currentFloor, this.destinationFloor);
    }

    void update(int id, int currentFloor, int destinationFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        calculateState();
    }

    void calculateState() {
        this.state = currentFloor == destinationFloor ? State.IDLE :
                (currentFloor > destinationFloor ? State.DOWN : State.UP);
    }

    void bulkPickUp(Collection<Tuple<Integer, Integer>> collection) {
        collection.forEach(t -> {
            floorsOrder.add(t.getFst());
            floorsOrder.add(t.getSnd());
        });
    }

    void pickUp(int fromFloor, int toFloor) {
        floorsOrder.add(toFloor);
        if (fromFloor == destinationFloor) return;
        else if (state == State.DOWN && fromFloor <= currentFloor && fromFloor > destinationFloor ||
                state == State.UP && fromFloor >= currentFloor && fromFloor < destinationFloor) {
            // fromFloor is between currentFloor and destinationFloor
            floorsOrder.add(destinationFloor);
            destinationFloor = fromFloor;
        } else floorsOrder.add(fromFloor);
    }

    void step() {
        if (state == State.UP || state == State.DOWN) {
            currentFloor = (currentFloor < destinationFloor) ? (currentFloor + 1) : (currentFloor - 1);
            if (currentFloor == destinationFloor) state = State.UNLOAD;
        } else if (state == State.UNLOAD || state == State.IDLE) {
            if (!floorsOrder.isEmpty()) {
                destinationFloor = floorsOrder.first();
                floorsOrder.remove(floorsOrder.first());
                calculateState();
            } else state = State.IDLE;
        }
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", destinationFloor=" + destinationFloor +
                ", state=" + state +
                ", floorsOrder=" + floorsOrder +
                "}\n";
    }
}
