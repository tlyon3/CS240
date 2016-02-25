package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/7/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/** Class for background images */
public class BGObject {
    public BGObject(){
        type=null;
    }

    public BGObject(BGObjectType type, int positionX, int positionY, float scale) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.scale = scale;
    }

    public BGObject(BGObjectType type, int positionX, int positionY, double scale, long id) {
        this.type = type;
        this.positionX = positionX;
        this.positionY = positionY;
        this.scale = scale;
        this.id = id;
    }

    public BGObject(JSONObject object,BGObjectType objectType) throws JSONException{
        this.scale = object.getDouble("scale");
        String position = object.getString("position");
        Point positionPoint = makePoint(position);
        this.positionX = positionPoint.getX();
        this.positionY = positionPoint.getY();
        int objectId = object.getInt("objectId");
        this.id = objectId;
        this.type = objectType;
    }

    private BGObjectType type;
    private int positionX;
    private int positionY;
    private double scale;
    private long id;

    @Override
    public boolean equals(Object o) {

        if(!o.getClass().equals(this.getClass())){
            return false;
        }
        BGObject test = (BGObject)o;
        if(test.getId()!=this.id)
            return false;
        else if(test.getPositionX()!=this.positionX)
            return false;
        else if(test.getPositionY()!=this.positionY)
            return false;
        else if(test.getScale()!=this.scale)
            return false;
        else if(test.getType()!=this.type)
            return false;
        else return true;
    }

    public BGObjectType getType() {
        return type;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public double getScale() {
        return scale;
    }

    public void setType(BGObjectType type) {
        this.type = type;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
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
}
