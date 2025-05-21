package com.DataStruct;

public class LinkedList<T> {
    public Node<T> head;
    public Node<T> tail;

    public LinkedList(){
        this.head = null;
    }

    public void add(T item) throws Exception{
        Node<T> newNode = new Node<>(item);
        if(head == null){
            this.head = newNode;
            this.tail = newNode;
        } else{
            this.tail.setNext(newNode);
        }
    }
}
