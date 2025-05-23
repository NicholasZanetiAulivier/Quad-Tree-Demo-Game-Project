package com.DataStruct;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;

/*
 * Game Quad Tree for collision Detection
 */

public class GameQuadTree{
    public static int MAX_OBJECTS = 10;
    public static int MAX_LEVEL = 7;
    // public static int MAX_LEVEL = 5;
    
    public Rectangle2D.Float bounds;
    public Area boundingArea;
    
    public GameQuadTree topLeft = null;
    public GameQuadTree topRight = null;
    public GameQuadTree bottomLeft = null;
    public GameQuadTree bottomRight = null;

    public DoublyLinkedList<CollisionObject> objects;
    public int level;
    public boolean hasSplit = false;

    public GameQuadTree(Rectangle2D.Float b , int l){
        this.bounds = b;
        this.boundingArea = new Area(b);
        this.level = l;
        this.objects = new DoublyLinkedList<>();
    }

    public GameQuadTree(float x , float y , float width , float height , int l){
        this(new Rectangle2D.Float(x,y,width,height) , l);
    }

    public void subDivide(){
        hasSplit = true;

        float x = (float)this.bounds.getX();
        float y = (float)this.bounds.getY();
        float w2 = (float)this.bounds.getWidth()/2;
        float h2 = (float)this.bounds.getHeight()/2;
        int l = this.level+1;
        topLeft = new GameQuadTree(x,y,w2,h2 ,l);
        topRight = new GameQuadTree(x+w2,y,w2,h2 ,l);
        bottomLeft = new GameQuadTree(x,y+h2,w2,h2 ,l);
        bottomRight = new GameQuadTree(x+w2,y+h2,w2,h2 ,l);
    }

    public void insert(CollisionObject c){
        Rectangle2D item = c.getBounds();

        if (!(this.boundingArea.intersects(item)||this.boundingArea.contains(item))) return;

        if (hasSplit){
            topLeft.insert(c);
            topRight.insert(c);
            bottomLeft.insert(c);
            bottomRight.insert(c);
            return;
        }
        this.objects.append(c);

        if(this.objects.getSize() > MAX_OBJECTS && level < MAX_LEVEL){
            subDivide();
            while (this.objects.getHead() != null){
                CollisionObject toInsert = this.objects.removeBack();
                topLeft.insert(toInsert);
                topRight.insert(toInsert);
                bottomLeft.insert(toInsert);
                bottomRight.insert(toInsert);
            }
            this.objects = null;
        }
    }

    public void insert(ArtificialCircle c){
        insert((CollisionObject)c);
    }


    /*
     * Method to return a doubly linked list of lists of objects that may collide with each other
     * BAD SOLUTION: Theres a lot of duplicate collisions. for items between leaves
     */
    public DoublyLinkedList<DoublyLinkedList<CollisionObject>> retrieveAllCollisions(){
        DoublyLinkedList<DoublyLinkedList<CollisionObject>> result = new DoublyLinkedList<>();
        if(hasSplit){
            result.concat(topLeft.retrieveAllCollisions());
            result.concat(topRight.retrieveAllCollisions());
            result.concat(bottomLeft.retrieveAllCollisions());
            result.concat(bottomRight.retrieveAllCollisions());
        }
        if(this.objects != null) result.append(this.objects);
        return result;
    }

    public DoublyLinkedList<CollisionObject> retrieve( CollisionObject c){
        Rectangle2D item = c.getBounds();
        DoublyLinkedList<CollisionObject> res = new DoublyLinkedList<>();
        if (!(this.boundingArea.intersects(item)||this.boundingArea.contains(item))) return res;

        if (hasSplit){
            res.concat(topLeft.retrieve(c));
            res.concat(topRight.retrieve(c));
            res.concat(bottomLeft.retrieve(c));
            res.concat(bottomRight.retrieve(c));
            return res;
        }

        Denode<CollisionObject> ptr = this.objects.getHead();
        while(ptr != null){
            CollisionObject p = ptr.getData();
            if(p == c){
                Denode<CollisionObject> temp = ptr.getNext();
                this.objects.detachDenode(ptr);
                ptr = temp;
            } else{
                res.append(p);
                ptr = ptr.getNext();
            }
        }
        return res;
    }

    public DoublyLinkedList<CollisionObject> getObjects(){
        return this.objects;
    }

    public void clear(){
        if(hasSplit) {
            topLeft.clear();
            topLeft = null;
            topRight.clear();
            topRight = null;
            bottomLeft.clear();
            bottomLeft = null;
            bottomRight.clear();
            bottomRight = null;
        }
    }


    public void draw(Graphics g){
        g.setColor(Color.CYAN);
        ((Graphics2D)g).draw(bounds);

        if(hasSplit){
            topLeft.draw(g);
            bottomLeft.draw(g);
            topRight.draw(g);
            bottomRight.draw(g);
        }
    }
}