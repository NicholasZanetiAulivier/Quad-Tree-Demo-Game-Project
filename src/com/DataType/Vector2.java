package com.DataType;

import java.util.Vector;

/*
 * Vector2 class provides vector operations in 2D. 
 * This class uses floats
 */

public class Vector2 {
    protected float x;
    protected float y;

    public Vector2(float x , float y){
        this.x = x;
        this.y = y;
    }

    public Vector2(){
        this(0,0);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getLength(){
        return (float)Math.sqrt(x*x+y*y);
    }

    public static Vector2 add(Vector2 n ,Vector2 v){
        return new Vector2(n.getX()+v.getX(),n.getY()+v.getY());
    }
    public static Vector2 subtract(Vector2 n ,Vector2 v){
        return new Vector2(n.getX()-v.getX(),n.getY()-v.getY());
    }

    public static Vector2 scale(Vector2 n , float p){
        return new Vector2(p*n.getX(),p*n.getY());
    }

    public static float dot(Vector2 n , Vector2 v){
        return n.getX()*v.getX()+n.getY()*v.getY();
    }

    public static float getCosBetween(Vector2 n, Vector2 v){
        return (float)(Vector2.dot(n, v)/(n.getLength()*v.getLength()));
    }

    public static float getDistance(Vector2 n , Vector2 v){
        return (float)Math.sqrt(Math.pow(n.getX()-v.getX(), 2)+Math.pow(n.getY()-v.getY(), 2));
    }

    public float getDirection(){
        return (float)Math.atan(y/x);
    }

}
