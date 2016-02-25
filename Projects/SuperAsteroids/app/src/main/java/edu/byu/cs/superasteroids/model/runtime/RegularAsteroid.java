package edu.byu.cs.superasteroids.model.runtime;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;

/**
 * Created by tlyon on 2/6/16.
 */
/** Class for Asteroid objects of type "regular"*/
public class RegularAsteroid extends Asteroid {
    public RegularAsteroid(){
        type = new AsteroidType("regular","regular");
    }
}
