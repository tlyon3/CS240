package edu.byu.cs.superasteroids.model.runtime.shipparts;

/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @versoin 1.0*/
/** Class for Point objects (ex attachPoint and emitPoint)*/
public class Point {
    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
    /** X component of Point */
    private int x;
    /** Y component of Point */
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass()!=this.getClass())
            return false;
        Point point = (Point)o;
        if(point.getX()!=this.x)
            return false;
        if(point.getY()!=this.y)
            return false;
        else return true;
    }
}
