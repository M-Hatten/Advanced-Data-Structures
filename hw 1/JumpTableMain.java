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
    public static Scanner stackScanner;
    public static Scanner queueScanner;
    public static Scanner listScanner;

    private static Stack<Character> stack = new Stack<Character>();
    private static Queue<Character> queue = new LinkedList<Character>();
    private static ArrayList<Character> list = new ArrayList<Character>();


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
        stateStayMeths.put(State.IDLE, () -> { return StateStayIdle();});
        stateStayMeths.put(State.STACK, () -> { return StateStayStack();});
        stateStayMeths.put(State.QUEUE, () -> { return StateStayQueue();});
        stateStayMeths.put(State.LIST, () -> {  return StateStayList();});

        //Initializing Exit hashmap and puttign stuff in there
        stateExitMeths = new HashMap<>();
        stateExitMeths.put(State.IDLE, () -> { StateExitIdle();});
        stateExitMeths.put(State.STACK, () -> { StateExitStack();});
        stateExitMeths.put(State.QUEUE, () -> { StateExitQueue();});
        stateExitMeths.put(State.LIST, () -> { StateExitList();});

        //The state we start in
        state = State.IDLE;
    }

    //STATE METHODS BELOW

    //change state method
    public void changeState(State newState){
        if (state != newState){
            if (state != State.IDLE) {
                stateExitMeths.get(state).invoke();
            }
            state = newState;
            stateEnterMeths.get(state).invoke();
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
        //System.out.println("State Enter Idle");
    }
    private void StateEnterStack(){
        //System.out.println("State Enter Stack");
        //Read the file
        try{ //the compiler refused to run unless I through a try catch method :/
            File StackObj = new File("stack.txt");
            stackScanner = new Scanner(StackObj);  
            String data = stackScanner.nextLine();
            //load up the stack
            for (int i = 0; i < data.length(); i++){
                if (data.charAt(i) != ','){
                    stack.push(data.charAt(i));
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please Try again.");
        }
    }
    private void StateEnterQueue(){
        //System.out.println("State Enter Queue");
        try{ //the compiler refused to run unless I through a try catch method :/
            File QueueObj = new File("queue.txt");
            queueScanner = new Scanner(QueueObj);
            String data = queueScanner.nextLine();
            for (int i = 0; i < data.length(); i++){
                if (data.charAt(i) != ','){
                    queue.add(data.charAt(i));
                }
            }  
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please Try again.");
        }
    }
    private void StateEnterList(){
        //System.out.println("State Enter List");
        try{ //the compiler refused to run unless I through a try catch method :/
            File ListObj = new File("list.txt");
            listScanner = new Scanner(ListObj);  
            String data = listScanner.nextLine();
            for (int i = 0; i < data.length(); i++){
                if (data.charAt(i) != ',') {
                    list.add(data.charAt(i));
                }
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found. Please Try again.");
        }
    }

    //Stay
    /*
     * - display ui
     * - take user input
     */

    private boolean StateStayIdle(){
        //System.out.println("State Stay Idle");
        System.out.println("1. Stack\n2. Queue\n3. List\n4. Quit\n? ");
        String newInput = userInputScanner.nextLine();

        if (newInput.equals("1")){
            changeState(State.STACK);
        }
        else if (newInput.equals("2")){
            changeState(State.QUEUE);
        }
        else if (newInput.equals("3")){
            changeState(State.LIST);
        }
        else if (newInput.equals("4")){
            System.out.println("Goodbye!");
            return false;
        }
        else {
            System.out.println("Please select a proper option");
        }
        return true;
    }
    private boolean StateStayStack(){
        //System.out.println("State Stay Stack");
        Stack<Character> tempStack = new Stack<>();
        tempStack.addAll(stack);

        System.out.println("|   |\n|---|");

        while(!tempStack.isEmpty()){
            for (int j = tempStack.size() - 1; j >= 0; j--){
                System.out.println("| " + tempStack.pop() + " |");
                System.out.println("|---|");
            }
        }
        System.out.println("1. Push\n2. Pop\n3. Save and Move to Queue\n4. Save and Move to List\n5. Quit\n? ");
        String newInput = userInputScanner.nextLine();
        //push
        if (newInput.charAt(0) == '1'){
            stack.push(newInput.charAt(2));
        }
        //pop
        else if (newInput.charAt(0) == '2'){
            if (!stack.isEmpty()){
                stack.pop();
            }
        }
        //save and move to queue
        else if (newInput.charAt(0) == '3'){
            changeState(State.QUEUE);
        }
        //save and move to list
        else if (newInput.charAt(0) == '4'){
            changeState(State.LIST);
        }
        //quit
        else if (newInput.charAt(0) == '5'){
            System.out.println("Goodbye!");
            return false;
        }
        else {
            System.out.println("Please enter a valid input");
        }
        return true; 
    }
    private boolean StateStayQueue(){
        //System.out.println("State Stay Queue");
        //draw queue
        Queue<Character> tempQueue = new LinkedList<>();
        tempQueue.addAll(queue);
        String line = "| ";
        while (!tempQueue.isEmpty()){
            line = line + tempQueue.remove() + " | ";
        }

        System.out.println(line);
        System.out.println("1. Enqueue\n2. Dequeue\n3. Save and Move to Stack\n4. Save and Move to List\n5. Quit\n? ");

        String newInput = userInputScanner.nextLine();
        //enqueue
        if (newInput.charAt(0) == '1'){
            queue.add(newInput.charAt(2));
        }
        //dequeue
        else if (newInput.charAt(0) == '2'){
            if (!queue.isEmpty()){
                queue.remove();
            }
        }
        //save and move to stack
        else if (newInput.charAt(0) == '3'){
            changeState(State.STACK);
        }
        //save and move to list
        else if (newInput.charAt(0) == '4'){
            changeState(State.LIST);
        }
        //quit
        else if (newInput.charAt(0) == '5'){
            System.out.println("Goodbye!");
            return false;
        }
        else {
            System.out.println("Please enter a valid input");
        }
        return true;
    }
    private boolean StateStayList(){
        //System.out.println("State Stay List");
        String line = "{ ";
        for (int i = 0; i < list.size(); i++){
            line = line + list.get(i) + ", ";
        }
        line = line + " }";

        System.out.println(line);
        System.out.println("1. Append\n2. Remove\n3. Save and Move to Stack\n4. Save and Move to Queue\n5. Quit\n? ");
        String newInput = userInputScanner.nextLine();
        //add to the list
        if (newInput.charAt(0) == '1'){
            list.add(newInput.charAt(2));
        }
        //remove
        else if (newInput.charAt(0) == '2'){
            if (!list.isEmpty()){
                list.remove(list.size()-1);
            }
        }
        //save and move to stack
        else if (newInput.charAt(0) == '3'){
            changeState(State.STACK);
        }
        //save and move to queue
        else if (newInput.charAt(0) == '4'){
            changeState(State.QUEUE);
        }
        //quit
        else if (newInput.charAt(0) == '5'){
            System.out.println("Goodbye!");
            return false;
        }
        else {
            System.out.println("Please enter a valid input");
        }
        return true;
    }

    //Exit
    /*
     * write new data to the file
     */
    private void StateExitIdle(){
        //System.out.println("State Exit Idle");
    }
    private void StateExitStack(){
        //System.out.println("State Exit Stack");
        Stack<Character> tempStack = new Stack<>();
        while (!stack.isEmpty()){
            tempStack.push(stack.pop());
        }
        try {
            File file = new File("stack.txt");
            FileWriter fw = new FileWriter(file);
            String text = "";
            while (!tempStack.isEmpty()){
                text = text + tempStack.pop() + ",";
            }
            for (int i = 0; i < text.length(); i++){
                fw.write(text.charAt(i));
            }
            fw.close();
        }
        catch (IOException e) {
            System.out.println("File not found. Please Try again.");
        }
    }
    private void StateExitQueue(){
        //System.out.println("State Exit Queue");
        try {
            File file = new File("queue.txt");
            FileWriter fw = new FileWriter(file);
            String text = "";
            while (!queue.isEmpty()){
                text = text + queue.remove() + ",";
            }
            for (int i = 0; i < text.length(); i++) 
                fw.write(text.charAt(i)); 
            fw.close();
        } catch (IOException e) {
            System.err.println("File not found :(");
        }
    }
    private void StateExitList(){ 
        //System.out.println("State Exit List");
        try {
            File file = new File("list.txt");
            FileWriter fw = new FileWriter(file);
            String text = "";
            for (int i = 0; i < list.size(); i++){
                if (list.get(i) != ',' && list.get(i) != ' '){
                    text = text + list.remove(i) + ",";
                }
            }
            for (int i = 0; i < text.length(); i++) 
                fw.write(text.charAt(i)); 
            fw.close();
        } catch (IOException e) {
            System.err.println("File not found :(");
        }
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