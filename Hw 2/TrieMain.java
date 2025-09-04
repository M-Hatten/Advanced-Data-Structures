import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.lang.runtime.*;
import java.util.HashSet;


class Node{
    //fields
    private Node children[];
    private boolean isTerminal;

    //constructor
    Node(int size, boolean term) {
        this.children = new Node[size];
        this.isTerminal = term;
    }
    public void addNode(int character, int size, boolean terminal){
        this.children[character] = new Node(size, terminal);
    }

    public Node searchNode(int search){
        //System.out.println("Searching: " + search + "  C val: " + Character.getNumericValue(search));
        Node ret = this.children[search];
        return ret;
    }

    public void setTerminal(boolean flag){
        this.isTerminal = flag;
    }

    public Boolean getTerminal(){
        return this.isTerminal;
    }
}
class Trie {
    private Node root;
    private int SIZE = 128;
    private String printBuffer = "";

    Trie() {
        Node root = null;
    }

    public void insert(String word){
        char chars[] = word.toCharArray();
        

        // if the root doesnt exist, make it
        if(root == null){
            root = new Node(SIZE, false);
        }

        Node currentNode = root;

        // loop, making new nodes if needed
        for (char c : chars){
            if (currentNode.searchNode((int) c) == null) {
                currentNode.addNode((int) c, SIZE, false);
            }
            currentNode = currentNode.searchNode(c);
        }

        //mark end of word
        currentNode.setTerminal(true);
    }

    public Boolean search(String word){
        char chars[] = word.toCharArray();
        Node currentNode = root;
        boolean exists = false;

        for (char c : chars){
            if (currentNode.searchNode((int) c) != null) {
                currentNode = currentNode.searchNode((int) c);
            }
            // if we're at the end of the word and its terminal, it exists
            if ((c == chars[chars.length - 1]) && currentNode.getTerminal()){
                exists = true;
            }
        }
        return exists;
    }

    public void printAllWords(){
        printBuffer = "";
        printAllWordsHelper(root, "");
        System.out.println(printBuffer);
    }

    public void printAllWords(String pre){
        char chars[] = pre.toCharArray();
        Node currentNode = root;

        for (char c : chars){
            if (currentNode.searchNode((int) c) != null) {
                currentNode = currentNode.searchNode((int) c);
            }  
        }

        //love me a good buffer
        printBuffer = "";
        printAllWordsHelper(currentNode, pre);
        System.out.println(printBuffer);

    }

    private void printAllWordsHelper(Node currentNode, String currentWord) {
        // If the current node is null, return
        if (currentNode == null) {
            return;
        }
    
        // If the current node is terminal, print the word
        if (currentNode.getTerminal()) {
            printBuffer += currentWord + ", ";
        }
    
        // Recursively go through all children
        for (int i = 0; i < SIZE; i++) {
            //System.out.println("Searching for: " + i);
            Node childNode = currentNode.searchNode(i);
            if (childNode != null) {
                // Append the character to the current word and recurse
                printAllWordsHelper(childNode, currentWord + (char) i);
            }
        }
    }

}
public class TrieMain{
    public static void main(String[] args) throws Exception{
        
        if (args[0].equals("test")) {
            boolean run = true;

            Trie t = new Trie();
            t.insert("banana");
            t.insert("bandana");
            t.insert("bandaid");
            t.insert("bandage");
            t.insert("letter");
            t.insert("lettuce");
            t.insert("let");
            t.insert("tool");
            t.insert("toy");
            t.insert("toilet");

            clearScreen();
            BufferedReader inputHandler = new BufferedReader(new InputStreamReader(System.in));

            while (run){
                clearScreen();
                
                System.out.println("{add WORD}: Add a word");
                System.out.println("{printAll}: Print all words");
                System.out.println("{startsWith PREFIX}: Print all words that start with");
                System.out.println("{search WORD}: Match a specific word");
                System.out.println("{quit}: Quit");
                System.out.print("\n\n? ");

                // read input
                String input = inputHandler.readLine();
                String inputs[] = input.split(" ");
                String first = inputs[0];
                String second = null;
                if (inputs.length == 2){
                    second = inputs[1];
                }

                // use input
                if (first.equals("quit")){
                    run = false;
                }
                else if (first.equals("add") && second != null){
                    t.insert(second);
                }
                else if (first.equals("printAll")){
                    t.printAllWords();
                    System.out.println("\n");
                    String input2 = inputHandler.readLine();
                }
                else if (first.equals("startsWith") && second != null){
                    t.printAllWords(second);
                    System.out.println("\n");
                    String input2 = inputHandler.readLine();
                }
                else if (first.equals("search") && second != null){
                    if (t.search(second)){
                        System.out.println("word '" + second + "' was found in the trie");
                    }
                    System.out.println("\n");
                    String input2 = inputHandler.readLine();
                }
                else {
                    System.out.println("Something wasnt right with: " + first + second + "\ntry again\n\n");
                    String input2 = inputHandler.readLine();
                }
            }

            


        }

        if (args[0].equals("memuse")){
            System.out.println("for my own memusement lol");
            BufferedReader inputHandler = new BufferedReader(new InputStreamReader(System.in));
            Trie t = new Trie();
            int wordCount = 0;
            Runtime rt = Runtime.getRuntime();
            long heapMemoryInBytes;

            Display.setYMax(900);
            Display.setYInc(15);
            
            String input = inputHandler.readLine();

            while (!input.equals(null)) {
                t.insert(input);
                wordCount += 1;
                heapMemoryInBytes = rt.totalMemory() - rt.freeMemory();
                /*for some reason it's not registering the bonus as false in the Display class file
                The exact error I'm getting here is: Exception in thread "main" java.lang.NoSuchMethodError: 'boolean Trie.search(java.lang.String)'
                at Display.show(Display.java:37)
                at TrieMain.main(TrieMain.java:224)

                There's no way for me to edit a class file to fix the error, but I did find a workaround
                I opened the class file with a decompiler and copied the code into its own seperate file and removed "if (!foundBonus) {" at line 37 and the subsequent "}" that came with it
                Doing so made the program work properly, surprisingly
                Since that line of code is meant to trigger if the bonus is NOT found and I clearly didn't find the bonus, I'm assuming it's okay that I removed that single line of code

                I'm okay with not getting the bonus since I did have to edit a file and remove a line of code to get everything working
                I'm totally okay coming to your office to discuss this if need be
                */
                Display.show(t, heapMemoryInBytes, wordCount);
            }
            
        }
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
}