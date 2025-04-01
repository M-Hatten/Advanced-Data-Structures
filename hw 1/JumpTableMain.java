/* Milo Hatten
 * A2- jump tables
 */

import java.util.*;
import java.io.*;

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
    //initializing hashmaps
    private State state;
    private HashMap<State, StateEnterExitMeth> stateEnterMeths;
    private HashMap<State, StateStayMeth> stateStayMeths;
    private HashMap<State, StateEnterExitMeth> stateExitMeths;

    //initializing file reading objects and scanner oobjects
    File StackObj = new File("stack.txt");
    File queueObj = new File("queue.txt");
    File listObj = new File("list.txt");
    public static Scanner stackScanner;
    public static Scanner queueScanner;
    public static Scanner listScanner;

    //scanner input object
    Scanner userInputScanner = new Scanner(System.in); 

    public Screen() {
        //Initializing Enter hashmap and PUTTIN STUFF IN THERE
        stateEnterMeths = new HashMap<>();
        stateEnterMeths.put(State.IDLE, () -> { StateEnterIdle();});
        stateEnterMeths.put(State.STACK, () -> { StateEnterStack();});
        stateEnterMeths.put(State.QUEUE, () -> { StateEnterQueue();});
        stateEnterMeths.put(State.LIST, () -> { StateEnterList();});

        //initializing Stay hashmap and putting stuff in there
        stateStayMeths = new HashMap<>();
        stateStayMeths.put(State.IDLE, () -> { StateStayIdle(); return StateStayIdle();});
        stateStayMeths.put(State.STACK, () -> { StateStayStack(); return StateStayStack();});
        stateStayMeths.put(State.QUEUE, () -> { StateStayQueue(); return StateStayQueue();});
        stateStayMeths.put(State.LIST, () -> { StateStayList(); return StateStayList();});

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
        boolean test = stateStayMeths.get(state).invoke();
        System.out.println("Test is: " + test);
        return test;
    }

    //Enter
    /*
     * These methods:
     * - read the text file
     */
    private void StateEnterIdle(){
        //i haven't really found that this is ever used but I'm throwing the UI in here jic
        System.out.println("State Enter Idle");
        // System.out.println("1. Stack\n 2. Queue\n 3. List\n 4. Quit\n ? ");
        stateStayMeths.get(state).invoke();
    }
    private void StateEnterStack(){
        System.out.println("State Enter Stack");
        //Read the file
        try{ //the compiler refused to run unless I through a try catch method :/
            stackScanner = new Scanner(StackObj);  
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please Try again.");
        }
        stateStayMeths.get(state).invoke();
    }
    private void StateEnterQueue(){
        System.out.println("State Enter Queue");
        stateStayMeths.get(state).invoke();
    }
    private void StateEnterList(){
        System.out.println("State Enter List");
        stateStayMeths.get(state).invoke();
    }

    //Stay
    /*
     * - display ui
     * - take user input
     */
    private boolean StateStayIdle(){
        System.out.println("State Stay Idle");
        System.out.println("1. Stack\n 2. Queue\n 3. List\n 4. Quit\n ? ");
        String newInput = userInputScanner.nextLine();
        while (true){
            if (newInput.equals("1")){
                changeState(State.STACK);
                stateEnterMeths.get(state).invoke();
            }
            else if (newInput.equals("2")){
                changeState(State.QUEUE);
                stateEnterMeths.get(state).invoke();
            }
            else if (newInput.equals("3")){
                changeState(State.LIST);
                stateEnterMeths.get(state).invoke();
            }
            else if (newInput.equals("4")){
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Please select a proper option");
            }
        }
        return false;
    }
    private boolean StateStayStack(){
        System.out.println("State Stay Stack");
        String newInput;
        String data = stackScanner.nextLine();
        Stack<Character> stack = new Stack<>();
        while (true){
            //load up the stack
            for (int i = 0; i < data.length(); i++){
                if (data.charAt(i) != ','){
                    stack.push(data.charAt(i));
                }
            }

            //menu and user input
            System.out.println("1. Push\n 2. Pop\n 3. Save and Move to Queue\n 4. Save and Move to List\n 5. Quit\n ? ");
            newInput = userInputScanner.nextLine();
            if (newInput.charAt(0) == '1'){
                String curstack = data;
            }
            else if (newInput.charAt(0) == '2'){

            }
            else if (newInput.charAt(0) == '3'){

            }
            else if (newInput.charAt(0) == '4'){

            }
            else if (newInput.charAt(0) == '5'){
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Please enter a valid input");
            }
        }
        changeState(State.IDLE);
        return false;
    }
    private boolean StateStayQueue(){
        System.out.println("State Stay Queue");
        return true;
    }
    private boolean StateStayList(){
        System.out.println("State Stay List");
        return true;
    }

    //Exit
    /*
     * write new data to the file
     */
    private void StateExitIdle(){
        System.out.println("State Exit Idle");
    }
    private void StateExitStack(){
        System.out.println("State Exit Stack");
    }
    private void StateExitQueue(){
        System.out.println("State Exit Queue");
    }
    private void StateExitList(){
        System.out.println("State Exit List");
    }

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