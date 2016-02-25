package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */
/** Super-class for all objects that move */
public class MovingObject extends ObjectWithPosition {
    public MovingObject(){
        velocity=0;
    }
    public MovingObject(int v){
        velocity=v;
    }

    /** Speed of a moving object in the level */
    protected int velocity;
    protected int health;
    /** Direction of moving object */
    protected Direction direction;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {

        return velocity;
    }

    /** Called when an object (mainly and probably only Asteroid objects)
     * collides with the side of the level (position hits max pixel value of level).
     * This will be called when MovingObject.update() sets the position outside the limits.
     * If the MovingObject is a Projectile fired from the ship, the Projectile will be destroyed
     * @return Returns a direction object holding the sin and cos components of the new direction*/
    public Direction bounceOfWall(){
        //TODO Compute new direction
        return null;
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
