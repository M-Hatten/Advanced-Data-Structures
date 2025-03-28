/*
 * 
 */

import java.util.*;
import java.io.File;
import java.io.FileWriter;

interface StateEnterExitMeth {
    public void invoke();
}

interface StateStayMeth{
    public boolean invoke();
}

enum State {
    IDLE,
    STACK,
    QUEUE,
    LIST
}

class Screen {
    private State state;
    private HashMap<State, StateEnterExitMeth> stateEnterMeths;
    private HashMap<State, StateStayMeth> stateStayMeths;
    private HashMap<State, StateEnterExitMeth> stateExitMeths;

    public Screen() {
        //Initializing Enter hashmap and PUTTIN STUFF IN THERE
        stateEnterMeths = new HashMap<>();
        stateEnterMeths.put(State.IDLE, () -> { StateEnterIdle();});
        stateEnterMeths.put(State.STACK, () -> { StateEnterStack();});
        stateEnterMeths.put(State.QUEUE, () -> { StateEnterQueue();});
        stateEnterMeths.put(State.LIST, () -> { StateEnterList();});

        //initializing Stay hashmap and putting stuff in there
        stateStayMeths = new HashMap<>();
        stateStayMeths.put(State.IDLE, () -> { StateStayIdle();});
        stateStayMeths.put(State.STACK, () -> { StateStayStack();});
        stateStayMeths.put(State.QUEUE, () -> { StateStayQueue();});
        stateStayMeths.put(State.LIST, () -> { StateStayList();});

        //Initializing Exit hashmap and puttign stuff in there
        stateExitMeths = new HashMap<>();
        stateExitMeths.put(State.IDLE, () -> { StateExitIdle();});
        stateExitMeths.put(State.STACK, () -> { StateExitStack();});
        stateExitMeths.put(State.QUEUE, () -> { StateExitQueue();});
        stateExitMeths.put(State.LIST, () -> { StateExitList();});

        //The state we start in
        state = State.IDLE;
        changeState(State.IDLE);
    }

    //STATE METHODS BELOW

    //change state method
    public void changeState(State newState){
        if (state != newState) {
            state = newState;
            if (stateEnterMeths.containsKey(newState)){
                stateEnterMeths.get(newState).invoke();
            }
        }
    }

    //Do the state!
    public boolean doState(){
        if (stateEnterMeths.containsKey(state)){
            stateEnterMeths.get(state).invoke();
        }
        if (stateStayMeths.containsKey(state)){
            stateStayMeths.get(state).invoke();
        }
        if (stateExitMeths.containsKey(state)){
            stateExitMeths.get(state).invoke();
        }
        return false;
    }

    //Enter
    private void StateEnterIdle(){}
    private void StateEnterStack(){}
    private void StateEnterQueue(){}
    private void StateEnterList(){}

    //Stay
    private void StateStayIdle(){}
    private void StateStayStack(){}
    private void StateStayQueue(){}
    private void StateStayList(){}

    //Exit
    private void StateExitIdle(){}
    private void StateExitStack(){}
    private void StateExitQueue(){}
    private void StateExitList(){}

}

public class JumpTableMain {
    public static void main(String[] args){
        Screen screen = new Screen();
        boolean keepRunning = true;
        while (keepRunning){
            keepRunning = screen.doState();
        }
    }
}