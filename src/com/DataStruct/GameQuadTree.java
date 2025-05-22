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
    public static int MAX_OBJECTS = 4;
    public static int MAX_LEVEL = 7;

    public static int TL = 0;
    public static int TR = 1;
    public static int BL = 2;
    public static int BR = 3;
    
    public Rectangle2D.Float bounds;
    public Area boundingArea;
    public GameQuadTree[] children;
    public DoublyLinkedList<CollisionObject> objects;
    public int level;

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
        this.children = new GameQuadTree[4];

        float x = (float)this.bounds.getX();
        float y = (float)this.bounds.getY();
        float w2 = (float)this.bounds.getWidth()/2;
        float h2 = (float)this.bounds.getHeight()/2;
        int l = this.level+1;
        this.children[TL] = new GameQuadTree(x,y,w2,h2 ,l);
        this.children[TR] = new GameQuadTree(x+w2,y,w2,h2 ,l);
        this.children[BL] = new GameQuadTree(x,y+h2,w2,h2 ,l);
        this.children[BR] = new GameQuadTree(x+w2,y+h2,w2,h2 ,l);
    }

    public void insert(CollisionObject c){
        Rectangle2D item = c.getBounds();

        if (!(this.boundingArea.intersects(item)||this.boundingArea.contains(item))) return;

        if (this.children != null){
            for (GameQuadTree now : this.children){
                now.insert(c);
            }
            return;
        }
        this.objects.append(c);

        if(this.objects.getSize() > MAX_OBJECTS && level < MAX_LEVEL){
            subDivide();
            while (this.objects.getHead() != null){
                CollisionObject toInsert = this.objects.removeBack();
                for (GameQuadTree now : this.children){
                    now.insert(toInsert);
                }
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
        if(this.children != null){
            for (GameQuadTree now : this.children){
                result.concat(now.retrieveAllCollisions());
            }
        }
        if(this.objects != null) result.append(this.objects);
        return result;
    }

    public HashSet<CollisionObject> retrieve( CollisionObject c){
        Rectangle2D item = c.getBounds();
        HashSet<CollisionObject> res = new HashSet<>();

        if (!(this.boundingArea.intersects(item)||this.boundingArea.contains(item))) return res;

        if (this.children != null){
            for (GameQuadTree now : this.children){
                res.addAll(now.retrieve(c));
            }
            return res;
        }

        Denode<CollisionObject> pt = this.objects.getHead();
        while(pt != null){
            CollisionObject s = pt.getData();
            if(s == c) {
                Denode<CollisionObject> temp = pt.getNext();
                objects.detachDenode(pt);
                pt = temp;
            }else{
                res.add(s);
                pt = pt.getNext();
            }
        }
        return res;
    }

    public Object[] getCollisionArray(CollisionObject c){
        return retrieve(c).toArray();
    }

    public DoublyLinkedList<CollisionObject> getObjects(){
        return this.objects;
    }


    public void draw(Graphics g){
        g.setColor(Color.CYAN);
        ((Graphics2D)g).draw(bounds);

        if(children != null) for(GameQuadTree c : children){
            c.draw(g);
        }
    }
}