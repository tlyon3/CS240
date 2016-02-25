package edu.byu.cs.superasteroids.model.runtime.shipparts;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.model.runtime.Ship;

/**
 * Created by tlyon on 2/6/16.
 */
/** Class for ShipPart object of type PowerCore */
public class PowerCore extends ShipPart {
    public PowerCore() {
        this.cannonBoost = -1;
        this.cannonBoost = -1;
        this.imagePath = null;
    }

    public PowerCore(int cb,int eb,String image){
        this.cannonBoost=cb;
        this.engineBoost=eb;
        this.imagePath=image;
    }
    public PowerCore(JSONObject powerCore) throws JSONException{
        this.cannonBoost = powerCore.getInt("cannonBoost");
        this.engineBoost = powerCore.getInt("engineBoost");
        this.imagePath = powerCore.getString("image");
    }
    private int cannonBoost;
    private int engineBoost;

    public int getCannonBoost() {
        return cannonBoost;
    }

    public int getEngineBoost() {
        return engineBoost;
    }

    public void setCannonBoost(int cannonBoost) {
        this.cannonBoost = cannonBoost;
    }

    public void setEngineBoost(int engineBoost) {
        this.engineBoost = engineBoost;
    }
}
