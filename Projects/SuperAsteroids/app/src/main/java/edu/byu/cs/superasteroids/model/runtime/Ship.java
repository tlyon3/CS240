package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/** Class for Ship object*/
public class Ship extends MovingObject {
    public Ship(Engine engine, Cannon cannon, PowerCore powerCore, MainBody mainBody, ExtraPart extraPart) {
        this.engine = engine;
        this.cannon = cannon;
        this.powerCore = powerCore;
        this.mainBody = mainBody;
        this.extraPart = extraPart;
        this.health = 100;
    }
    public Ship(){
        this.engine = null;
        this.cannon = null;
        this.powerCore = null;
        this.mainBody = null;
        this. extraPart = null;
        this.health = 100;
    }
    /** Engine of ship*/
    private Engine engine;
    /** Cannon of ship*/
    private Cannon cannon;
    /** Power Core of ship */
    private PowerCore powerCore;
    /** Main Body of ship */
    private MainBody mainBody;
    /** Extra Part of ship */
    private ExtraPart extraPart;
    /** Health of ship */
    private int health;

    /** Fires the cannon */
    public void fire(){

    }

    /** Changes the direction of the ship by 'degree' degrees
     * @param degree Amount to turn the ship
     * */
    public void turn(int degree){

    }

    public boolean isComplete(){
        if (engine == null)
            return false;
        else if(cannon == null)
            return false;
        else if(powerCore == null)
            return false;
        else if(mainBody == null)
            return false;
        else if(extraPart == null)
            return false;
        else return true;
    }
    public Engine getEngine() {
        return engine;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public MainBody getMainBody() {
        return mainBody;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }
}
