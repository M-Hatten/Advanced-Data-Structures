/*
 * 
 */

 import java.util.*;
 import java.io.File;
 import java.io.FileWriter;
 
 interface dataStructure {
     public int invoke();
 }
 
 public class extracode {
     public static Scanner userInput = new Scanner(System.in);
     private static int pickedStack() { //if the user chose Stack
         char stackInput = '0'; //initializing the char outside the while loop to make it easier on the memory
         while (true){
             System.out.println("1. Push \n 2. Pop \n 3. Save and Move to Queue \n 4. Save and Move to List \n 5. Quit \n ? ");
             String stackChoice = userInput.nextLine();
             if (stackChoice.charAt(0) == '1'){  //user selects push
                 stackInput = stackChoice.charAt(2); //reads char being pushed
                 System.out.println("You've pushed: " + stackInput);
             }
             break;
         }
         return 0; 
     }
     private static int pickedQueue() { 
         return 0; 
     }
     private static int pickedList() { 
         return 0; 
     }
     
     
     public static void main (String[] args){
         
         dataStructure[] dataIndex = new dataStructure[3];
         dataIndex[0] = () -> { return pickedStack(); };
         dataIndex[1] = () -> { return pickedQueue(); };
         dataIndex[2] = () -> { return pickedList(); };
 
         while (true){
             System.out.println("1. Stack \n 2. Queue \n 3. List \n 4. Quit \n ? ");
             String choice = userInput.nextLine();
             System.out.println ("You said: " + choice);
             if (choice.charAt(0) == '1'){
                 dataIndex[0].invoke();
             }
             if (choice.charAt(0) == '2'){
                 dataIndex[1].invoke();
             }
             if (choice.charAt(0) == '3'){
                 dataIndex[2].invoke();
             }
             if (choice.charAt(0) == '4'){
                 System.out.println("Goodbye!");
                 break;
             }
         }
     }
 }