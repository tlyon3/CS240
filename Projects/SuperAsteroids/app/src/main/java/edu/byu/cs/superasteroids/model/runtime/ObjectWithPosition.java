package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */


import android.graphics.Rect;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/** All objects that have a position on the screen */
public class ObjectWithPosition extends Visible {
    /** The x-coordinate of the object in the level */
    protected double xCoordinate;
    /** The y-coordinate of the object in the level */
    protected double yCoordinate;

    protected RectF bounds;

    public ObjectWithPosition(){
        xCoordinate=0;
        yCoordinate=0;
    }
    public ObjectWithPosition(double x,double y){
        xCoordinate=x;
        yCoordinate=y;
    }

    @Override
    public void draw() {
        DrawingHelper.drawImage(this.imageId,(float)xCoordinate,(float)yCoordinate,0.0f,
                AsteroidsData.getInstance().getShipScale(),AsteroidsData.getInstance().getShipScale(),255);
    }
    public void drawBounds(){
        Point viewTopLeft = Viewport.getInstance().convertToViewCoordinates(this.bounds.left,this.bounds.top);
        Point viewBottomRight = Viewport.getInstance().convertToViewCoordinates(this.bounds.right,this.bounds.bottom);
        Rect viewBounds = new Rect((int)viewTopLeft.getX(),(int)viewTopLeft.getY(),(int)viewBottomRight.getX(),(int)viewBottomRight.getY());
        DrawingHelper.drawRectangle(viewBounds, 200, 255);
    }

    //Getters and setters---------------------------------------------------------------------------/
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

    public RectF getBounds() {
        return bounds;
    }
}
