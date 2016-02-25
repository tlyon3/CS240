package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;

/** Class for all Asteroid objects with type "octeroid" */
public class OcteroidAsteroid extends Asteroid{
    public OcteroidAsteroid(){
        type = new AsteroidType("octeroid","octeroid");
    }
}
