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

    public Point(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }
    /** X component of Point */
    private double x;
    /** Y component of Point */
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
