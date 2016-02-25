package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;

/** Super-class for all Asteroid objects*/
public class Asteroid extends MovingObject {
    public Asteroid(){
        this.type=null;
        this.direction=null;
        this.velocity=0;
        this.health=0;
    }
    public Asteroid(AsteroidType at, Direction d, int v){
        this.type=at;
        this.velocity=v;
        this.direction=d;
        this.health=100;
    }
    /** Type of asteroid */
    protected AsteroidType type;
    /** Health of asteroid. Amount of damage an asteroid can take before exploding */
    protected int health;

    public AsteroidType getType() {
        return type;
    }

    public void setType(AsteroidType type) {
        this.type = type;
    }

}
