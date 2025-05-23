package com.DataStruct;

public class DoublyLinkedList<T> {
    private Denode<T> head;
    private Denode<T> tail;
    private int size;

    public DoublyLinkedList(){
        this.head = null;
        this.tail = null;
        size = 0;
    }

    public Denode<T> getHead(){
        return this.head;
    }

    public Denode<T> getTail(){
        return this.tail;
    }

    public int getSize(){
        return size;
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

        size++;
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
        size++;
    }

    //Pop from front of list
    public Denode<T> popFront(){
        Denode<T> node = this.head;
        this.head = node.getNext();
        if(this.head == null){
            this.tail = null;
        } else{
            this.head.setPrev(null);
        }
        size--;
        return node;
    } 

    //Pop from back of list
    public Denode<T> popBack(){
        Denode<T> node = this.tail;
        this.tail = node.getPrev();
        if(this.tail == null){
            this.head = null;
        }else{
            this.tail.setNext(null);
        }
        size--;
        return node;
    }

    public T removeFront(){
        return this.popFront().getData();
    }

    public T removeBack(){
        return this.popBack().getData();
    }

    //Just assume denode is already a part of the list
    public T detachDenode(Denode<T> node){
        Denode<T> prev = node.getPrev();
        Denode<T> next = node.getNext();
        
        if (prev != null) prev.setNext(next);
        if (next != null) next.setPrev(prev);

        size--;

        return node.getData();
    }

    /*
     * method to concat 2 lists. just assume that other will never be used
     */
    public void concat(DoublyLinkedList<T> other){
        this.size += other.getSize();
        if(this.head == null){
            this.head = other.getHead();
            this.tail = other.getTail();
            return;
        }

        this.tail.setNext(other.getHead());
        other.getHead().setPrev(this.tail);
        this.tail = other.getTail();
    }

    public String toString(){
        String res = "[";
        Denode<T> pt = getHead();
        while(pt!= null){
            res+=" "+pt.getData();
            pt = pt.getNext();
        }
        return res + "]";
    }
}
