package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/7/16.
 */

import edu.byu.cs.superasteroids.model.gamedefinition.ProjectileType;

/** Class for objects of type Projectile. Will be created when Ship.fire() is called*/
public class Projectile extends MovingObject {
    public Projectile(){
        type=null;
        this.direction=null;
        this.velocity=0;
    }
    /** Constructor when type (image), direction and velocity are known. Will be called
     * when Ship.fire() is called */
    public Projectile(ProjectileType type, Direction d, int v) {
        this.type = type;
        this.direction=d;
        this.velocity=0;
    }

    /** Used to store the image of the projectile*/
    private ProjectileType type;

}
