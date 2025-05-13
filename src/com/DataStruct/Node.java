package com.DataStruct;

public class Node<T> {
    protected T data;
    protected Node<T> next;

    public Node(T data , Node<T> next){
        this.data = data;
        this.next = next;
    }

    public Node(T data){
        this(data,null);
    }

    public Node(){
        this(null,null);
    }

    public T getData(){
        return this.data;
    }

    public Node<T> getNext(){
        return this.next;
    }

    public void setData(T data){
        this.data = data;
    }

    public void setNext(Node<T> next) throws Exception{
        this.next = next;
    }
}
