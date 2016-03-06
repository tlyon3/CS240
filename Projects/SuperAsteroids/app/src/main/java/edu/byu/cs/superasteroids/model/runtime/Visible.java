package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */
/** Super-class for all visible objects */
public class Visible {
    int imageId;
    protected int height;
    protected int width;

    public Visible(){}

    /** Update the position and status of each visible object */
    public void update(double time){}

    /** Draw the object with its updated values*/
    public void draw(){}

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
