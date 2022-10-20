package org.example;


import org.example.fcfs.FCFSElevator;
import org.example.fcfs.FCFSManager;
import org.example.states.Action;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FCFSManagerTest {

    @Test
    public void managerCreation(){
        FCFSManager fcfsm = new FCFSManager(1,5,5);
        assertEquals(1, fcfsm.getElevators().size());
    }

    @Test
    public void oneElevatorTwoCalls(){
        FCFSElevator e;
        FCFSManager fcfsm = new FCFSManager(1,5,5);
        fcfsm.pickUp(1,true,3);
        fcfsm.pickUp(2,true,5);
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(1,e.getCurrentFloor());
        assertEquals(Action.UNLOAD,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(1,e.getCurrentFloor());
        assertEquals(Action.UP,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(2,e.getCurrentFloor());
        assertEquals(Action.UP,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(3,e.getCurrentFloor());
        assertEquals(Action.UNLOAD,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(3,e.getCurrentFloor());
        assertEquals(Action.DOWN,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(2,e.getCurrentFloor());
        assertEquals(Action.UNLOAD,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(2,e.getCurrentFloor());
        assertEquals(Action.UP,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(3,e.getCurrentFloor());
        assertEquals(Action.UP,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(4,e.getCurrentFloor());
        assertEquals(Action.UP,e.getNextAction());
        fcfsm.step();
        e = fcfsm.getElevators().get(0);
        assertEquals(5,e.getCurrentFloor());
        assertEquals(Action.UNLOAD,e.getNextAction());
    }

    @Test
    public void twoElevatorsTwoCalls(){
        FCFSElevator e1,e2;
        FCFSManager fcfsm = new FCFSManager(2,5,5);
        fcfsm.pickUp(1,true,3);
        fcfsm.pickUp(2,true,5);
        fcfsm.step();
        e1 = fcfsm.getElevators().get(0);
        assertEquals(1,e1.getCurrentFloor());
        assertEquals(Action.UNLOAD,e1.getNextAction());
        e2 = fcfsm.getElevators().get(1);
        assertEquals(1,e2.getCurrentFloor());
        assertEquals(Action.UP,e2.getNextAction());
        fcfsm.step();
        e1 = fcfsm.getElevators().get(0);
        assertEquals(1,e1.getCurrentFloor());
        assertEquals(Action.UP,e1.getNextAction());
        e2 = fcfsm.getElevators().get(1);
        assertEquals(2,e2.getCurrentFloor());
        assertEquals(Action.UNLOAD,e2.getNextAction());
    }
}
