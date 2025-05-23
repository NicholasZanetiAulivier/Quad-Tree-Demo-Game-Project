package com.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class ArtificialCircle implements Entity, Drawable, CollisionObject{
    public Vector2 position; //Top left corner of circle
    public Vector2 velocity;
    public float rad;

    private boolean colliding = false; //Temp

    public ArtificialCircle(float x , float y , float vX , float vY,float rad){
        this.position = new Vector2(x,y);
        this.rad = rad;
        this.velocity = new Vector2(vX, vY);
    }

    public short getType(){
        return CollisionObject.CIRCLE;
    }

    public boolean hasCollisions(){
        return true;
    }

    public void setColliding(boolean t){
        colliding = t;
    }

    public float getX(){
        return position.getX();
    }

    
    public float getY(){
        return position.getY();
    }
    
    public Vector2 getPosition(){
        return this.position;
    }
    
    public float getXVelocity(){
        return velocity.getX();
    }
    
    public float getYVelocity(){
        return velocity.getY();
    }
    
    public Vector2 getVelocity(){
        return this.velocity;
    }
    
    public float getRad(){
        return this.rad;
    }

    public float getCenterX(){
        return getX() + getRad();
    }

    public float getCenterY(){
        return getY()+getRad();
    }

    public Rectangle2D getBounds(){
        return new Rectangle2D.Float(getX(),getY(),getRad()*2,getRad()*2);
    }
    
    public void setY(float y){
        this.position.setY(y);
    }

    public void setX(float x){
        this.position.setX(x);
    }

    public void setYVelocity(float v){
        this.velocity.setY(v);
    }

    public void setXVelocity(float v){
        this.velocity.setX(v);
    }

    public void isColliding(CollisionObject c){
        if(c.getType() == CollisionObject.CIRCLE) isColliding((ArtificialCircle)c);
    }

    public void isColliding(ArtificialCircle c){
        setColliding(true);
        c.setColliding(true);
    }

    public void update(float dt){
        float x = getX();
        float y = getY();

        if(x < 0 || x > Global.realWidth-2*rad) {
            velocity.setX(-velocity.getX());
            // System.out.println("Bounced at x: "+x);
        };
        if(y < 0 || y > Global.realHeight-2*rad) {
            velocity.setY(-velocity.getY());
            // System.out.println("Bounced at y: "+y);
        }
        
        position.setX(x+velocity.getX()*dt);
        position.setY(y+velocity.getY()*dt);
    }

    public void draw(Graphics g , ImageObserver o){
        if (!colliding)g.setColor(Color.BLUE);
        else g.setColor(Color.RED);
        g.fillOval((int)position.getX() , (int)position.getY(),(int)rad*2,(int)rad*2);
        colliding = false;
    }

    //Assume radius is always the same between the two circles(it's a simulation after all)
    public boolean checkCollision(ArtificialCircle c){
        return Math.pow((getX()-c.getX()),2)+Math.pow((getY()-c.getY()),2) < Math.pow(rad*2,2);
    }

    public boolean checkCollision(CollisionObject c){
        if (c.getType() == CollisionObject.CIRCLE){
            return checkCollision((ArtificialCircle)c);
        }
        else return false;
    }

    public String toString(){
        return getBounds().toString();
    }
}
