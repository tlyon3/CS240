package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */


/** All objects that have a position on the screen */
public class ObjectWithPosition extends Visible {
    public ObjectWithPosition(){
        xCoordinate=0;
        yCoordinate=0;
    }
    public ObjectWithPosition(double x,double y){
        xCoordinate=x;
        yCoordinate=y;
    }
    /** The x-coordinate of the object in the level */
    private double xCoordinate;
    /** The y-coordinate of the object in the level */
    private double yCoordinate;

    public double getxCoordinate() {
        return xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
