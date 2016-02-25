package edu.byu.cs.superasteroids.model.gamedefinition;

/**
 * Created by tlyon on 2/7/16.
 */
/** Type class for Projectile objects. This is mainly used to store the image of the projectile*/
public class ProjectileType extends ImageObject {
    public ProjectileType(){
        this.imagePath="";
        this.height=0;
        this.width=0;
    }
    /** Constructor with image path, height and width known.
     * @param iPath path to the image file
     * @param h height of the image in pixels
     * @param w width of the image in pixels */
    public ProjectileType(String iPath, int h,int w) {
        this.imagePath = iPath;
        this.height = h;
        this.width = h;
    }
}
