package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/7/16.
 */

import android.graphics.RectF;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/** Class for background images */
public class BGObject extends ObjectWithPosition{
    private BGObjectType type;
    private double scale;
    private long id;
    private int imageId;

    public BGObject(){
        type=null;
    }

    public BGObject(BGObjectType type, int positionX, int positionY, float scale) {
        this.type = type;
        this.xCoordinate = positionX;
        this.yCoordinate = positionY;
        this.scale = scale;
        int top = (int)this.yCoordinate - (int)(type.getHeight()/2*scale);
        int bottom = (int)this.yCoordinate + (int)(type.getHeight()/2*scale);
        int left = (int)this.xCoordinate - (int)(type.getWidth()/2*scale);
        int right = (int)this.xCoordinate + (int) (type.getWidth()/2*scale);
        this.bounds = new RectF(left,top,right,bottom);
    }

    public BGObject(BGObjectType type, int positionX, int positionY, double scale, long id) {
        this.type = type;
        this.xCoordinate = positionX;
        this.yCoordinate = positionY;
        this.scale = scale;
        this.id = id;
        int top = (int)this.yCoordinate - (int)(type.getHeight()/2*scale);
        int bottom = (int)this.yCoordinate + (int)(type.getHeight()/2*scale);
        int left = (int)this.xCoordinate - (int)(type.getWidth()/2*scale);
        int right = (int)this.xCoordinate + (int) (type.getWidth()/2*scale);
        this.bounds = new RectF(left,top,right,bottom);
    }

    public BGObject(JSONObject object,BGObjectType objectType) throws JSONException{
        this.scale = object.getDouble("scale");
        String position = object.getString("position");
        Point positionPoint = makePoint(position);
        this.xCoordinate = positionPoint.getX();
        this.yCoordinate = positionPoint.getY();
        int objectId = object.getInt("objectId");
        this.id = objectId;
        this.type = objectType;
        int top = (int)this.yCoordinate - (int)(type.getHeight()/2*scale);
        int bottom = (int)this.yCoordinate + (int)(type.getHeight()/2*scale);
        int left = (int)this.xCoordinate - (int)(type.getWidth()/2*scale);
        int right = (int)this.xCoordinate + (int) (type.getWidth()/2*scale);
        this.bounds = new RectF(left,top,right,bottom);
    }



    /** Makes a Point object from a string of the format "x,y"
     * @param pointString string of the format "x,y"
     * @return returns a Point object*/
    protected Point makePoint(String pointString){
        String[] parts = pointString.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Point result = new Point(x,y);
        return result;
    }
    /** Draws the background object at its x and y coordinates*/
    public void draw(){
        Point viewportPosition = Viewport.getInstance().convertToViewCoordinates(xCoordinate,yCoordinate);
        DrawingHelper.drawImage(imageId,(int)viewportPosition.getX(), (int)viewportPosition.getY(),0,
                (float)scale,(float)scale,255);
    }
    /** Updates the bounds of the background object based on its position*/
    public void updateBounds(){
        int top = (int)this.yCoordinate - (int)(type.getHeight()/2*scale);
        int bottom = (int)this.yCoordinate + (int)(type.getHeight()/2*scale);
        int left = (int)this.xCoordinate - (int)(type.getWidth()/2*scale);
        int right = (int)this.xCoordinate + (int) (type.getWidth()/2*scale);
        this.bounds = new RectF(left,top,right,bottom);
    }

    //Getters and setters---------------------------------------------------------------------------/
    public BGObjectType getType() {
        return type;
    }

    public double getPositionX() {
        return xCoordinate;
    }

    public double getPositionY() {
        return yCoordinate;
    }

    public double getScale() {
        return scale;
    }

    public void setType(BGObjectType type) {
        this.type = type;
    }

    public void setPositionX(int positionX) {
        this.xCoordinate = positionX;
    }

    public void setPositionY(int positionY) {
        this.yCoordinate = positionY;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}

