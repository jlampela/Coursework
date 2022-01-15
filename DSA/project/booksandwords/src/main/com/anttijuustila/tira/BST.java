package com.anttijuustila.tira;

public class BST {

    int uniqueWords = 0;
    int amountOfWords = 0;
    int wordsToIgnore = 0;
    int wordsIgnored = 0;
    Node[] top100 = new Node[100000];

    public class Node{
        long key;
        String name;
        int count;
        Node left;
        Node right;

        Node(long key, String name){
            this.key = key;
            this.name = name;
            count = 1;
            left = right = null;
        }
    }

    Node root = null;

    /**
     * Adds node into BST
     */
    public void addNode(long key, String name){

        if(!etsi(root, key)){
            amountOfWords++;
            return;
        }
        
        Node newNode = new Node(key, name);
        Node x = root;
        Node tmp = null;

        while(x != null){
            tmp = x;
            if(key < x.key){
                x = x.left;
            } else if(key > x.key){
                x = x.right;
            } else{
                amountOfWords++;
                return;
            }
        }
        if(root == null){
            root = newNode;
        } else if(key < tmp.key){
            tmp.left = newNode;
        } else{
            tmp.right = newNode;
        }
        amountOfWords++;
        uniqueWords++;
        return;
        
    }
    /**
     * Finds given key from BST
     */
    public boolean etsi(Node root, long key){
        if(root == null){
            return true;    
        } else if (root.key == key){
            root.count++;
            return false;
        }
        if (root.key < key){
            return etsi(root.right, key);
        } else {
            return etsi(root.left, key);    
        }
    }

    /**
     * Inorder traversal through the BST and adds nodes into a array
     */
    int a = 0;
    public void inordered(Node node){
        if(node.left != null){
            inordered(node.left);
        }
        top100[a] = node;
        a++;
        if(node.right != null){
            inordered(node.right); 
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

    /*Works but WAAAY too slooooow O(n^2)*/
    public void bubblesort(){
        int n = uniqueWords;
        for(int a = 0; a < n-1; a++){
            for(int b = 0; b < n-a-1; b++){
                if(top100[b].count < top100[b+1].count){
                    Node tmp = top100[b];
                    top100[b] = top100[b+1];
                    top100[b+1] = tmp;
                }
            }
        }
    }
    /**
     * Recursive quicksort
     */
    private void quicksort(int low, int high) {
        if (low < high){
           int pivot = partition(low, high);
           quicksort(low, pivot-1);
           quicksort(pivot+1, high);
        }
     }
  
    private int partition(int low, int high) {
        Node x = top100[high];
        int i = low - 1;
     
        for(int j = low; j < high; j++){
           if(top100[j].count > x.count){
              i++;
              Node tmp = top100[i];
              top100[i] = top100[j];
              top100[j] = tmp;
           }
        }
        Node tmp = top100[i+1];
        top100[i+1] = top100[high];
        top100[high] = tmp;
        return i + 1;
    }
    /*Not working 
    public void iteMergeSort(Node[] node){
        int mid = uniqueWords / 2;
        for(int a = 1; a < mid; a++){
            int left = 0;
            int asd = left + a - 1;
            int right = asd + a;
            while(right < mid){
                merge(node, left, asd, right);
                left = right + 1;
                asd = left + a - 1;
                right = mid + a;
            }
            if(left < mid && asd < mid){
                merge(node, left, asd, mid - 1);
            }
        }
    }

    public void merge(Node[] node, int left, int asd, int right){
        Node[] arr = new Node[right-left+1];
        int l = left;
        int r = asd + 1;
        int k = 0;
        while(l <= asd && r <= right){
            if(node[l].count < node[r].count){
                arr[k++] = node[l++];
            } else {
                arr[k++] = node[r++];
            }
        }
        while(l <= asd){
            arr[k++] = node[l++];
        }
        while(r <= right){
            arr[k++] = node[r++];
        }
        k = 0;
        while(k < arr.length){
            node[left++] = arr[k++];
        }
    }
    */

    public void toArray(Node root){    
        inordered(root);
        quicksort(0, uniqueWords-1);
        //bubblesort();
    }

    /**
     * Simple print method
     */
    public void printTop(){
        if(uniqueWords < 100){
            System.out.println("Listing " + uniqueWords + " most common words");
        } else {
            System.out.println("Listing 100 most common words");
        }
        if(uniqueWords < 100){
            for(int j = 0; j < uniqueWords; j++){
                System.out.println((j+1) + ". " + top100[j].name + " : " + top100[j].count);
            }
        } else {
            for(int j = 0; j < 100; j++){
                System.out.println((j+1) + ". " + top100[j].name + " : " + top100[j].count);
            }
        }
    }
}
