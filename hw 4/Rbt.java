class RbtNode {
    private int data;
    private byte color;
    public static final byte CL_RED = 0;
    public static final byte CL_BLACK = 1;
    private RbtNode left;
    private RbtNode right;
    private RbtNode parent;

    public RbtNode (int data){
        this.data =  data;
    }
    //accessors
    public int getData(){return data;}
    public byte getColor(){return color;}
    public RbtNode getLeft() {return left;}
    public RbtNode getRight(){return right;}
    public RbtNode getPatent(){return parent;}

    //mutators
    public void setData(int data){this.data = data;}
    public void setColor(byte color) {this.color = color;}
    public void setLeft(RbtNode left) {this.left = left;}
    public void setRight(RbtNode right){this.right = right;}
    public void setParent(RbtNode parent) {this.parent = parent;}
}

public class Rbt {
    public RbtNode root;
    public int nodeNumber = 0;

    public Rbt (){
        this.root = null;
    }

    //does what it says on the tin
    //also checks and calls funcs to solve RB violations
    public void insert(int value){
        nodeNumber += 1;
        RbtNode temp = root;
        RbtNode valNode = new RbtNode(value);
        if (root == null){
            root.setData(value); 
        }
        else{
            //inserting the node
            while (true){
                if (temp.getData() < value){
                    if (temp.getRight() == null){
                        temp.setRight(valNode);
                        break;
                    }
                    else{
                        temp = temp.getRight();
                    }
                }
                else if (temp.getData() > value){
                    if (temp.getLeft() == null){
                        temp.setLeft(valNode);
                        break;
                    }
                    else{
                        temp = temp.getLeft();
                    }
                }
                else{
                    System.out.println("Error! temp.getData() is having issues!");
                    break;
                }
            }
            if (!checkIfBalanced()){
                balanceThatTree();
            }
        }
    }

    public boolean checkIfBalanced(){
        
        return true;
    }

    public void balanceThatTree(){

    }

    //returns true if node is in the tree, false otherwise
    public boolean search(int value){
        RbtNode val = new RbtNode(value);
        RbtNode temp = root;
        while (true){
            if (temp == null){
                return false;
            }
            else if (val.getData() == temp.getData()){
                return true;
            }
            else if (val.getData() > temp.getData()){
                temp = temp.getRight();
            }
            else if (val.getData() < temp.getData()){
                temp = temp.getLeft();
            }
            else{
                System.out.println("You done goofed!");
                return false;
            }
        }
    }

    //smallest value in the tree
    public int min(){
        return -1;
    }

    //largest value in the tree
    public int max(){
        return -1;
    }

    //number of nodes in the tree
    public int size(){
        if (root == null){
            return 0;
        }
        else{
            return nodeNumber;
        }
    }
    public String inorder(){
        String result = "";
        return result;
    }
}