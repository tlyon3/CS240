package edu.byu.cs.superasteroids.model.runtime.shipparts;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.model.runtime.Ship;

/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @version 1.0*/

/** Class for ShipPart of type ExtraPart */
public class ExtraPart extends ShipPart {
    public ExtraPart(){
        this.attachPoint = null;
        this.imagePath = null;
        this.width=-1;
        this.height=-1;
    }
    public ExtraPart(JSONObject extraPart)throws JSONException{
        String point = extraPart.getString("attachPoint");
        Point attachPoint = makePoint(point);
        this.imagePath = extraPart.getString("image");
        this.width = extraPart.getInt("imageWidth");
        this.height = extraPart.getInt("imageHeight");
        this.attachPoint = attachPoint;
    }
    public ExtraPart(int attachX,int attachY, String image,int imageH,int imageW){
        this.attachPoint = new Point(attachX,attachY);
        this.imagePath=image;
        this.width=imageW;
        this.height=imageH;
    }
}
