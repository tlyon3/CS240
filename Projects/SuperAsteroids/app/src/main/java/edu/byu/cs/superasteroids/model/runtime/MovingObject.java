package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */
/** Super-class for all objects that move */
public class MovingObject extends ObjectWithPosition {
    /** Speed of a moving object in the level */
    protected int velocity;
    protected int health;
    /** Direction of moving object in radians */
    protected double direction;
    protected double directionSin;
    protected double directionCos;

    public MovingObject(){
        velocity=0;
    }
    public MovingObject(int v){
        velocity=v;
    }
    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {

        return velocity;
    }

    /** Takes 'd' amount of damage to object
     * If the object is an asteroid, and the total health has been reduced to 0, asteroids should
     * split
     * If the object is the ship, and the total health has been reduced to 0, execute gameOver()
     * @param d Amount of damage to be taken
     * */
    public void takeDamage(int d){
        this.health-=d;
    }


}
