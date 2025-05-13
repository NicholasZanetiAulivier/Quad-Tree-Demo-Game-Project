package com.DataStruct;

public class DoublyLinkedList<T> {
    private Denode<T> head;
    private Denode<T> tail;
    private int size;

    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Denode<T> getHead(){
        return this.head;
    }

    public Denode<T> getTail(){
        return this.tail;
    }

    //Append to back of list
    public void append(T data){
        Denode<T> newNode = new Denode<>(data);

        if(head == null){
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            newNode.setPrev(this.tail);
            this.tail = newNode;
        }
        this.size++;
    }

    //Push to front of list
    public void push(T data){
        Denode<T> newNode = new Denode<>(data);

        if(head == null){
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.setNext(this.head);
            this.head.setPrev(newNode);
            this.head = newNode;
        }
        this.size++;
    }

    //Pop from front of list
    public Denode<T> popFront(){
        Denode<T> node = this.head;
        node.setNext(null);
        this.head = this.head.getNext();
        this.head.setPrev(null);
        return node;
    } 

    //Pop from back of list
    public Denode<T> popBack(){
        Denode<T> node = this.tail;
        node.setPrev(null);
        this.tail = this.tail.getPrev();
        this.tail.setNext(null);
        return node;
    }

    public T removeFront(){
        return this.popFront().getData();
    }

    public T removeBack(){
        return this.popBack().getData();
    }

    public static <T> void detachDenode(Denode<T> node){
        Denode<T> prev = node.getPrev();
        Denode<T> next = node.getNext();
        
        if (prev != null) prev.setNext(next);
        if (next != null) next.setPrev(prev);
    }
}
