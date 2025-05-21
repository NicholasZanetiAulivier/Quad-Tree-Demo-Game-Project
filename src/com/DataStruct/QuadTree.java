package com.DataStruct;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;

//https://code.tutsplus.com/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374t

//Specifically for the game
public class QuadTree {
    public static int MAX_OBJECTS = 4;
    public static byte MAX_LEVEL = 10;
    
    private byte level;
    private DoublyLinkedList<CollisionObject> objects;

    private QuadTree[] childrenNodes;
    private Rectangle bounds;

    public QuadTree(byte l , Rectangle b){
        this.level = l;
        this.bounds = b;
        this.objects = new DoublyLinkedList<>();
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
    
    private int getIndex(float x , float y , float width , float height){
        double verticalMidpoint = bounds.getCenterX();
        double horizontalMidpoint = bounds.getCenterY();
        
        boolean top = y < horizontalMidpoint && y+height < horizontalMidpoint;
        boolean bot = y>horizontalMidpoint;

        if (x < verticalMidpoint && x+width < verticalMidpoint ){
            if (top) return 0;
            else if (bot) return 1;
        } else if (x > verticalMidpoint){
            if (top) return 2;
            else if (bot) return 3;
        }

        //Return -1 if object bounds contain the midpoint of this node's bounds 
        return -1;
    }

    public void insert(ArtificialCircle c){
        if (childrenNodes != null){
            int index = getIndex(c.getX(),c.getY(),c.getRad() , c.getRad());
            if(index != -1) childrenNodes[index].insert(c);
            return;
        }
        try{
            this.objects.append((CollisionObject)c);
        } catch(Exception e){
            System.out.println(e);
        }

        if(objects.getSize() > MAX_OBJECTS && level < MAX_LEVEL){
            if(childrenNodes==null){
                split();
            }

            Denode<CollisionObject> pt = objects.getHead();
            while(pt != null){
                int index = getIndex(c.getX(),c.getY(),c.getRad() , c.getRad());
                if(index != -1) {
                    Denode<CollisionObject> temp = pt.getNext();
                    childrenNodes[index].insert((ArtificialCircle)objects.detachDenode(pt));
                    pt = temp;
                } else{
                    pt = pt.getNext();
                }
            }
        }
    }

    public DoublyLinkedList<CollisionObject> retrieve(DoublyLinkedList<CollisionObject> list , ArtificialCircle c){
        int index = getIndex(c.getX(),c.getY(),c.getRad() , c.getRad());
        if (index != -1 && childrenNodes != null){
            childrenNodes[index].retrieve(list , c);
        }

        Denode<CollisionObject> ptr = objects.getHead();

        while(ptr != null){
            list.append(ptr.getData());
            ptr = ptr.getNext();
        }

        return list;
    }

    public void draw(Graphics g){
        ((Graphics2D)g).draw(bounds);
        if(childrenNodes != null){
            childrenNodes[0].draw(g);
            childrenNodes[1].draw(g);
            childrenNodes[2].draw(g);
            childrenNodes[3].draw(g);
        }
    }

}
