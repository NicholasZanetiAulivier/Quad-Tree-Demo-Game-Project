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

    public void split(){
        int childrenWidth = (int)(bounds.getWidth()/2);
        int childrenHeight = (int)(bounds.getHeight()/2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        childrenNodes = new QuadTree[4];
        byte childrenLevel = (byte)(level+1);

        //Top left
        childrenNodes[0] = new QuadTree(childrenLevel, x, y, childrenWidth, childrenHeight);
        //Top right
        childrenNodes[1] = new QuadTree(childrenLevel, x+childrenWidth, y, childrenWidth, childrenHeight);
        //Bot left
        childrenNodes[2] = new QuadTree(childrenLevel, x, y+childrenHeight, childrenWidth, childrenHeight);
        //Bot right
        childrenNodes[3] = new QuadTree(childrenLevel, x+childrenWidth, y+childrenHeight, childrenWidth, childrenHeight);
    }


    

}
