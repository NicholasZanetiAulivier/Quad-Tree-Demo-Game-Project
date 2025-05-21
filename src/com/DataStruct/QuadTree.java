package com.DataStruct;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.CollisionObject;

//https://code.tutsplus.com/quick-tip-use-quadtrees-to-detect-likely-collisions-in-2d-space--gamedev-374t
//https://github.com/timohausmann/quadtree-js/

//Specifically for the game
public class QuadTree {
    public static int MAX_OBJECTS = 10;
    public static byte MAX_LEVEL = 4;
    
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
    
    private LinkedList<Integer> getIndex(float x , float y , float width , float height){
        LinkedList<Integer> result = new LinkedList<>();
        double middleX = bounds.getX() + bounds.getWidth()/2;
        double middleY = bounds.getY() + bounds.getHeight()/2;
        
        boolean north=y<middleY;
        boolean west=x<middleX;
        boolean east=x+width>middleX;
        boolean south=y+height>middleY;


        if (north){
            if(east) result.add(1);
            if(west) result.add(0);
        }
        if(south){
            if(east)result.add(3);
            if(west)result.add(2);
        }
        //Return -1 if object bounds contain the midpoint of this node's bounds 
        return result;
    }

    public void insert(ArtificialCircle c){
        if (childrenNodes != null){
            LinkedList<Integer> index = getIndex(c.getX(),c.getY(),c.getRad()*2 , c.getRad()*2);
            Node<Integer> indexNode = index.getHead();
            while(indexNode != null){
                childrenNodes[indexNode.getData()].insert(c);
                indexNode = indexNode.getNext();
            }
            return;
        }
        
        objects.append((ArtificialCircle)c);

        if(objects.getSize() > MAX_OBJECTS && level < MAX_LEVEL){
            if(childrenNodes==null){
                split();
            }

            Denode<CollisionObject> pt = objects.getHead();
            Denode<CollisionObject> next;
            while(pt != null){
                next = pt.getNext();
                ArtificialCircle s = (ArtificialCircle)objects.detachDenode(pt);
                LinkedList<Integer> index = getIndex(s.getX(),s.getY(),s.getRad()*2 , s.getRad()*2);
                Node<Integer> indexNode = index.getHead();
                while(indexNode!=null){
                    childrenNodes[indexNode.getData()].insert((ArtificialCircle)pt.getData());
                    indexNode = indexNode.getNext();
                }
                pt = next;
            }
        }
    }

    public DoublyLinkedList<CollisionObject> retrieve(ArtificialCircle c){
        DoublyLinkedList<CollisionObject> list = new DoublyLinkedList<>();

        LinkedList<Integer> index = getIndex(c.getX(),c.getY(),c.getRad()*2 , c.getRad()*2);
        Node<Integer> indexNode = index.getHead();
        if (childrenNodes != null){
            DoublyLinkedList<CollisionObject> toAppend = childrenNodes[indexNode.getData()].retrieve(c);
            while(toAppend.getSize() > 0){
                list.append(toAppend.removeBack());
            }
            indexNode = indexNode.getNext();
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
