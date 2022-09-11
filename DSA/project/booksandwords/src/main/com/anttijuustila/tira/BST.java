package com.anttijuustila.tira;

public class BST {

    Node root = null;
    int uniqueWords = 0;
    int amountOfWords = 0;

    WordCount[] tmp = null;

    BST(){root=null;}

    /**
     * Adds node into BST
     */
    public void addNode(long key, String name){
        
        Node node = new Node(key, name);
        amountOfWords++;

        if(root == null){
            root = node;
            uniqueWords++;
        } else {
            uniqueWords += root.insertNode(node, key, name);
        }
    }

    /**
     * Inorder traversal through the BST and adds nodes into a array
     */
    int a = 0;
    public WordCount[] inOrder(Node node){

        //Creates right sized array for words
        if(tmp == null){
            createRightSizeArray();
        }
        
        if(node.left != null){
            inOrder(node.left);
        }

        if(a < tmp.length){
            tmp[a] = new WordCount(node.name, node.count);
            a++;
        } else {
            int tmpCount = Integer.MAX_VALUE;
            int tmpIndex = -1;

            //find the word with smallest count
            for(int i = 0; i<tmp.length;i++){
                if(tmp[i].laskuri < tmpCount){
                    tmpCount = tmp[i].laskuri;
                    tmpIndex = i;
                }
            }

            //if node count is bigger than the smallest already in array -> swap
            if(node.count > tmpCount){
                tmp[tmpIndex] = new WordCount(node.name, node.count);
            }
        }

        if(node.right != null){
            inOrder(node.right); 
        }
        return tmp;
    }

    public void createRightSizeArray(){
        if(uniqueWords < 100){
            tmp = new WordCount[uniqueWords];
        } else {
            tmp = new WordCount[100];
        }
    }

    /**
     * Calculates the BST depth using recursion
     */
    public int depth(Node root){
        if(root == null){
            return -1;
        }else {
            int leftDepth = depth(root.left);
            int rightDepth = depth(root.right);
            if(leftDepth > rightDepth){
                return (leftDepth + 1);
            }else {
                return (rightDepth + 1);
            }
        }
        
    }
}
