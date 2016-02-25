package edu.byu.cs.superasteroids.model.runtime;

import java.util.Set;
/**
 * Created by tlyon on 2/9/16.
 */
/** Class to handle all objects on a level.
 * Will be instantiated when a level is started
 * This class will keep track of the positions of all objects. */
public class LevelHandler {
    public LevelHandler(){
        movingObjects = null;
        currentLevel=null;
    }

    /** Set of all MovingObject objects in level*/
    private Set<MovingObject> movingObjects;
    private Level currentLevel;

    /** Called when a new level is started. Will populate movingObjects set*/
    public void initialize(){
        // TODO: 2/11/16 Populate movingObjects set
    }
    /** Executes when two MovingObjects collide with each other
     * @param a object 1
     * @param b object 2*/
    public void onCollision(MovingObject a, MovingObject b){
        // TODO: 2/11/16
    }
    /** Updates all the positions of all moving objects on the level*/
    public void update(){
        // TODO: 2/11/16 Call update() methods for each moving object in level
    }

    /** After calling update(), this will check the positions of every MovingObject on the level.
     * If there is a collision, onCollision(MovingObject a, MovingObject b) will be called
     * @return Returns true if there was a collision. False if not */
    public boolean resolveCollisions(){
        // TODO: 2/11/16 Check to see if any object has collide with another
        return false;
    }

    public Set<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    public void setMovingObjects(Set<MovingObject> movingObjects) {
        this.movingObjects = movingObjects;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
}
