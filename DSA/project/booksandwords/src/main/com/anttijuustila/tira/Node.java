package com.anttijuustila.tira;

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
    }

    //Recursive way to add a new key into BST
    public int insertNode(Node node, long key, String name){

        if(key == this.key){
            count++;
            return 0;
        } else {
            if(node.key < this.key){
                if(this.left == null){
                    this.left = node;
                } else {
                    return this.left.insertNode(node, key, name);
                }
            } else if(node.key > this.key){
                if(this.right == null){
                    this.right = node;
                } else {
                    return this.right.insertNode(node, key, name);
                }
            } else {this.count++; return 0;}
            return 1;
        }
    }
}