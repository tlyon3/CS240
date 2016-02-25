package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

/** Class to store the direction of a moving object*/
public class Direction {
    Direction(){
        sin=0;
        cos=0;
    }
    Direction(float s,float c){
        sin=s;
        cos=c;
    }

    /** Sine component of the direction */
    private float sin;
    /** Cosine component of the direction */
    private float cos;

    public float getSin() {
        return sin;
    }

    public float getCos() {
        return cos;
    }

    public void setSin(float sin) {
        this.sin = sin;
    }

    public void setCos(float cos) {
        this.cos = cos;
    }
}
