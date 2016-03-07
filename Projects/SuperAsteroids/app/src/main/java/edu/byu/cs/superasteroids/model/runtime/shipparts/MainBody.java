package edu.byu.cs.superasteroids.model.runtime.shipparts;

/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @version 1.0*/


import org.json.JSONException;
import org.json.JSONObject;

/** Class for ShipPart object of type MainBody*/
public class MainBody extends ShipPart{
    public MainBody() {

    }
    public MainBody(JSONObject mainBody) throws JSONException{
        String cAttach = mainBody.getString("cannonAttach");
        Point cannonAttach = makePoint(cAttach);
        this.cannonAttach = cannonAttach;
        String eAttach = mainBody.getString("engineAttach");
        Point engAttach = makePoint(eAttach);
        this.engineAttach = engAttach;
        String exAttach = mainBody.getString("extraAttach");
        Point extAttach = makePoint(exAttach);
        this.extraAttach = extAttach;
        this.imagePath = mainBody.getString("image");
        this.width = mainBody.getInt("imageWidth");
        this.height = mainBody.getInt("imageHeight");
    }
    public MainBody(int cannonX,int cannonY,int engineX,int engineY,int extraX,int extraY,
                    String image,int h,int w){
        this.cannonAttach=new Point(cannonX,cannonY);
        this.engineAttach=new Point(engineX,engineY);
        this.extraAttach=new Point(extraX,extraY);
        this.imagePath=image;
        this.width=w;
        this.height=h;
    }
    /** Point to attach cannon */
    Point cannonAttach;
    /**Point to attach engine */
    Point engineAttach;
    /** Point to attach extra part */
    Point extraAttach;

    public Point getCannonAttach() {
        return cannonAttach;
    }

    public Point getEngineAttach() {
        return engineAttach;
    }

    public Point getExtraAttach() {
        return extraAttach;
    }

    public void setCannonAttach(Point cannonAttach) {
        this.cannonAttach = cannonAttach;
    }

    public void setEngineAttach(Point engineAttach) {
        this.engineAttach = engineAttach;
    }

    public void setExtraAttach(Point extraAttach) {
        this.extraAttach = extraAttach;
    }
    /*-------------------------------------------------------------------------------------------*/

}
