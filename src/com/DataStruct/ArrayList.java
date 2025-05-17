package com.DataStruct;

public class ArrayList<T> {
    protected Object[] arr ;
    protected int maxSize;
    protected int currentSize;

    public ArrayList(int size){
        this.arr = new Object[size];
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
        return (T[])this.arr;
    }
    
    public void addElement(T e) throws Exception{
        if(currentSize == maxSize) throw new Exception("ArrayList already has maximum capacity");
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
