package edu.byu.cs.superasteroids.model.runtime;

import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/**
 * Created by tlyon on 2/25/16.
 */
public class Viewport extends ObjectWithPosition{

    private static Viewport viewport;
    int height;
    int width;
    double centerX;
    double centerY;

    Level currentLevel;

    public Viewport(){

    }

    public void initialize(){
        this.height = DrawingHelper.getGameViewHeight();
        this.width = DrawingHelper.getGameViewWidth();
        this.xCoordinate = (AsteroidsData.getInstance().getShip().getxCoordinate() - this.width/2);
        this.yCoordinate = (AsteroidsData.getInstance().getShip().getyCoordinate() - this.height/2);

        initializeBounds();
    }
    @Override
    public void update(double elapsedTime){
        this.height = DrawingHelper.getGameViewHeight();
        this.width = DrawingHelper.getGameViewWidth();
        this.xCoordinate = (AsteroidsData.getInstance().getShip().getxCoordinate() - this.width/2);
        this.yCoordinate = (AsteroidsData.getInstance().getShip().getyCoordinate() - this.height/2);
        this.centerX = AsteroidsData.getInstance().getShip().getxCoordinate();
        this.centerY = AsteroidsData.getInstance().getShip().getyCoordinate();

        //constrain coordinates
        //left
        if(this.xCoordinate < 0) {
            this.xCoordinate = 0;
            this.centerX = width/2;
        }
        //top
        if(this.yCoordinate < 0) {
            this.yCoordinate = 0;
            this.centerY = height/2;
        }
        //right
        Point centerPoint = convertToWorldCoordinates(width/2,height/2);
        int levelWidth = currentLevel.getWidth();
        int levelHeight = currentLevel.getHeight();
        if((this.xCoordinate + width) > currentLevel.getWidth()) {
            this.xCoordinate = currentLevel.getWidth() - this.width;
            this.centerX = levelWidth-width/2;
        }
        //bottom
        if((this.yCoordinate + this.height) > currentLevel.getHeight()) {
            this.yCoordinate = currentLevel.getHeight() - this.height;
            this.centerY = levelHeight - height/2;
        }
        //update bounds
        float top = (float)this.centerY - this.height/2;
        float left = (float)this.centerX - this.width/2;
        float bottom = (float)this.centerY + this.height/2;
        float right = (float)this.centerX + this.width/2;
        RectF newBounds = new RectF(left,top,right,bottom);
        this.bounds = newBounds;
    }

    public Point convertToViewCoordinates(double worldX, double worldY){
        double x = worldX - this.xCoordinate;
        double y = worldY - this.yCoordinate;
        Point result = new Point(x,y);
        return result;
    }

    public Point convertToWorldCoordinates(double viewX, double viewY){
        double x = viewX + this.xCoordinate;
        double y = viewY + this.yCoordinate;
        Point result = new Point(x,y);
        return result;
    }

    public static Viewport getInstance(){
        if(viewport == null)
            viewport = new Viewport();
        return viewport;
    }

    public boolean isIntersectingWithViewport(ObjectWithPosition obj){
        if(this.bounds.intersect(obj.getBounds()))
            return true;
        else return false;
    }

    public boolean isInViewport(ObjectWithPosition obj){
        Point viewPoint = convertToViewCoordinates(obj.xCoordinate,obj.yCoordinate);
        if(viewPoint.getX() > 0 && viewPoint.getX() < 1024){
            if(viewPoint.getY() > 0 && viewPoint.getY() < 528){
                return true;
            }
            else return false;
        }
        else return false;
    }


    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void initializeBounds(){
        if(this.bounds == null){
            float top = (float)this.centerY - this.height/2;
            float left = (float)this.centerX - this.width/2;
            float bottom = (float)this.centerY + this.height/2;
            float right = (float)this.centerX + this.width/2;
            this.bounds = new RectF(left,top,right,bottom);
        }
        else {
            float top = (float)this.centerY - this.height/2;
            float left = (float)this.centerX - this.width/2;
            float bottom = (float)this.centerY + this.height/2;
            float right = (float)this.centerX + this.width/2;
            RectF newBounds = new RectF(left,top,right,bottom);
            this.bounds = newBounds;
        }
    }
}
