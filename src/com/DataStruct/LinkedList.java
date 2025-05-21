package com.DataStruct;

public class LinkedList<T> {
    public Node<T> head;
    public Node<T> tail;

    public int size;

    public LinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T item) throws Exception{
        Node<T> newNode = new Node<>(item);
        if(head == null){
            this.head = newNode;
            this.tail = newNode;
        } else{
            this.tail.setNext(newNode);
        }
        size++;
    }

    public int getSize(){
        return size;
    }
}
