package com.DataStruct;

public class ArrayList<T> {
    protected T[] arr ;
    protected int maxSize;
    protected int currentSize;

    public ArrayList(int size){
        this.arr = (T[])(new Object[size]);
        this.maxSize = size;
        this.currentSize = 0;
    }

    public int getCurrentSize(){
        return this.currentSize;
    }

    public int getMaxSize(){
        return this.maxSize;
    }

    public T[] getArray(){
        return this.arr;
    }
    
    public void addElement(T e) throws Error{
        if(currentSize == maxSize) throw new Error("ArrayList already has maximum capacity");
        this.arr[currentSize] = e;
        currentSize++;
    }

    public void removeElement(int index){
        if (index < currentSize){
            for(int i = index ; i < currentSize-1 ; i++){
                this.arr[i] = this.arr[i+1];
            }
            this.arr[currentSize-1] = null;
            currentSize--;
        }
    }
}
