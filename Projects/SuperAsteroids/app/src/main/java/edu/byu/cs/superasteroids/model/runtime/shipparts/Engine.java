package edu.byu.cs.superasteroids.model.runtime.shipparts;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @version 1.0*/

/** Class for ShipPart object of type Engine*/
public class Engine extends ShipPart {
    /* Constructors ----------------------------------------------------------------------------*/
    public Engine(){

    }
    public Engine(JSONObject engine) throws JSONException{
        this.imagePath = engine.getString("image");
        this.baseSpeed = engine.getInt("baseSpeed");
        this.baseTurnRate = engine.getInt("baseTurnRate");
        String aPoint = engine.getString("attachPoint");
        Point attachPoint = makePoint(aPoint);
        this.attachPoint = attachPoint;
        this.width = engine.getInt("imageWidth");
        this.height = engine.getInt("imageHeight");
    }
    public Engine(int bs,int bt,int attachX, int attachY, String image,int imageHeight,int imageWidth){
        this.baseSpeed=bs;
        this.baseTurnRate=bt;
        this.setAttachPoint(new Point(attachX,attachY));
        this.imagePath = image;
        this.height=imageHeight;
        this.width=imageWidth;
    }
    /*-------------------------------------------------------------------------------------------*/

    /* Members-----------------------------------------------------------------------------------*/
    /** Speed of Ship provided by Engine */
    private int baseSpeed;
    /** Turning rate of Ship provided by Engine */
    private int baseTurnRate;
    /*-------------------------------------------------------------------------------------------*/

    /* Getters and setters ----------------------------------------------------------------------*/
    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseTurnRate() {
        return baseTurnRate;
    }

    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public void setBaseTurnRate(int baseTurnRate) {
        this.baseTurnRate = baseTurnRate;
    }
    /*-------------------------------------------------------------------------------------------*/

}
