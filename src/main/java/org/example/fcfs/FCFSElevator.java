package org.example.fcfs;

import org.example.Elevator;

import java.util.ArrayDeque;
import java.util.Deque;

public class FCFSElevator extends Elevator {
    private Deque<Integer> queue = new ArrayDeque<>();
    public FCFSElevator(int id, int currentFloor) {
        super(id, currentFloor);
    }

    public int getQueueSize(){
        return queue.size();
    }

    @Override
    protected boolean hasNoMoreInquires() {return queue.isEmpty();}

    @Override
    protected void doIfNoMoreInquires(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        standardInquiryHandling(fromFloor, upButtonPressed, toFloor);
    }

    @Override
    protected void doIfBadDirectionUserInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {}

    @Override
    protected void standardInquiryHandling(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        queue.add(fromFloor);
        queue.add(toFloor);
    }

    @Override
    protected void noToFloorInquiry(int fromFloor, boolean upButtonPressed, Integer toFloor) {
        queue.add(fromFloor);
    }

    @Override
    protected void doIfNoMoreInquiresNoFromFloor(int toFloor) {
        queue.add(toFloor);
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
        queue.add(toFloor);
    }

    @Override
    protected void specificStep() {
        if (!queue.isEmpty()) {
            // Standard step
            if (currentFloor == queue.peekFirst()) queue.pollFirst();
            else if (currentFloor < queue.peekFirst()) currentFloor++;
            else if (currentFloor > queue.peekFirst()) currentFloor--;
        }
    }

    @Override
    public Integer getDestinationFloor() {
        return (queue.isEmpty() ? null : queue.peekFirst());
    }
}
