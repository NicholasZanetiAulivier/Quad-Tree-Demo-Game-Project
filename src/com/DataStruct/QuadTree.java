package com.DataStruct;

import java.awt.Rectangle;

//https://code.tutsplus.com/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374t
public class QuadTree<T> {
    public static int MAX_OBJECTS = 4;
    public static byte MAX_LEVEL = 4;
    
    private byte level;
    private LinkedList<T> objects;

    private QuadTree<T>[] childrenNodes;
    private Rectangle bounds;

    public QuadTree(byte l , Rectangle b){
        this.level = l;
        this.bounds = b;
    }

    public QuadTree(byte l , int x , int y , int width , int height ){
        this(l,new Rectangle(x, y, width, height));
    }
    

}
