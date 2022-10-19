package org.example.elevator;

import org.example.states.Direction;

import java.util.ArrayList;
import java.util.TreeSet;

public class DirectionInOrderElevator extends Elevator {
    private Direction direction = Direction.NONE;
    private ArrayList<Integer> buffer = new ArrayList<>();
    private TreeSet<Integer> queue = new TreeSet<>();

    public DirectionInOrderElevator(int id, int currentFloor) {
        super(id, currentFloor);
    }

    @Override
    boolean hasNoMoreInquires() {
        return queue.isEmpty();
    }

    void doIfNoMoreInquires(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        configureQueueOrder(upButtonPressed);
        direction = (upButtonPressed) ? Direction.UP : Direction.DOWN;
        queue.add(toFloor);
        queue.add(fromFloor);
    }

    void doIfBadDirectionUserInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        queue.add(fromFloor);
        buffer.add(toFloor);
    }

    void standardInquiryHandling(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        queue.add(toFloor);
        queue.add(fromFloor);
    }

    @Override
    void noToFloorInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        toFloor = fromFloor;
        if (hasNoMoreInquires()) doIfNoMoreInquires(fromFloor, upButtonPressed, toFloor);
        else standardInquiryHandling(fromFloor, upButtonPressed, toFloor);
    }

    @Override
    void doIfNoMoreInquiresNoFromFloor(int toFloor) {
        configureQueueOrder(currentFloor < toFloor);
        direction = (currentFloor < toFloor) ? Direction.UP : Direction.DOWN;
        queue.add(toFloor);
    }

    @Override
    boolean isIncorrectUserInquiryNoFromFloor(int toFloor) {
        return direction == Direction.UP && currentFloor > toFloor ||
                direction == Direction.DOWN && currentFloor < toFloor;
    }

    @Override
    void doIfBadDirectionUserInquiryNoFromFloor(int toFloor) {
        buffer.add(toFloor);
    }

    @Override
    void standardInquiryHandlingNoFromFloor(int toFloor) {
        queue.add(toFloor);
    }

    @Override
    void specificStep() {
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
    }

    @Override
    public Integer getDestinationFloor() {
        return (queue.isEmpty() ? null : queue.first());
    }

    private void configureQueueOrder(boolean upButtonPressed) {
        queue = (upButtonPressed) ? new TreeSet<>() : new TreeSet<>((o1, o2) -> Integer.compare(o2, o1));
    }

    @Override
    public String toString() {
        return super.toString() +
                ", direction=" + direction +
                ", floorsOrder=" + queue +
                "}\n";
    }
}
