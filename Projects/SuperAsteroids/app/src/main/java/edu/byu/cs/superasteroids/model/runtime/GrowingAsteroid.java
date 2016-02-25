package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;

/** Class for all Asteroids of type "growing"*/
public class GrowingAsteroid extends Asteroid{
    public GrowingAsteroid(){
        this.type = new AsteroidType("growing","growing");
        this.health=100;
        this.velocity=0;
        this.direction=null;
    }
}
