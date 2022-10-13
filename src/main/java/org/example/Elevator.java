package org.example;

import org.example.utils.Triplet;

import java.util.TreeSet;

public class Elevator {
    int id;
    short currentFloor;
    short destinationFloor;
    State state;
    TreeSet<Short> floorsOrder = new TreeSet<>();

    Elevator(int id, short currentFloor, short destinationFloor) {
        update(id, currentFloor, destinationFloor);
    }

    Triplet<Integer, Short, Short> getStatus() {
        return new Triplet<>(this.id, this.currentFloor, this.destinationFloor);
    }

    void update(int id, short currentFloor, short destinationFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
        setDirection();
    }

    void setDirection() {
        this.state = currentFloor == destinationFloor ? State.IDLE :
                (currentFloor > destinationFloor ? State.DOWN : State.UP);
    }

    void pickUp(short fromFloor, short toFloor) {
        if (fromFloor == destinationFloor);
        else if (fromFloor == currentFloor ||
                state == State.DOWN && fromFloor < currentFloor && fromFloor > destinationFloor ||
                state == State.UP && fromFloor > currentFloor && fromFloor < destinationFloor) {
            floorsOrder.add(destinationFloor);
            destinationFloor = fromFloor;
        }
        else floorsOrder.add(fromFloor);
        floorsOrder.add(toFloor);
    }

    void step() {
        if (state == State.UP || state == State.DOWN) {
//            currentFloor = (currentFloor < destinationFloor) ? currentFloor++ : currentFloor--;
            currentFloor = (currentFloor < destinationFloor) ? (short)(currentFloor+1) : (short)(currentFloor-1);
            if (currentFloor == destinationFloor) state = State.UNLOAD;
        } else if (state == State.UNLOAD || state == State.IDLE) {
            if (!floorsOrder.isEmpty()) {
                destinationFloor = floorsOrder.first();
                floorsOrder.remove(floorsOrder.first());
                setDirection();
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
                '}';
    }
}
