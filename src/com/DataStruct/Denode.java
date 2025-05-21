package com.DataStruct;

/*
 * Double ended Node to serve as a node for a 
 * Doubly linked list
 */

public class Denode<T> extends Node<T>{
    protected Denode<T> prev;
    protected Denode<T> next;

    public Denode(T data , Denode<T> prev , Denode<T> next){
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Denode(T data){
        this(data,null,null);
    }

    @Override
    public Denode<T> getNext(){
        return this.next;
    }

    public Denode<T> getPrev(){
        return this.prev;
    }

    public void setNext(Denode<T> next){
        this.next = next;
    }

    public void setNext(Node<T> next){
        System.out.println("Wrong parameter: setNext on Denode has to be given a Denode parameter");
    }

    public void setPrev(Denode<T> prev){
        this.prev = prev;
    }

}
