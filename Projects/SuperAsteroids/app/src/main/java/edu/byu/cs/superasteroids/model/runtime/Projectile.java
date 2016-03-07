package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/7/16.
 *
 */


import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.gamedefinition.ProjectileType;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/** Class for objects of type Projectile. Will be created when Ship.fire() is called*/
public class Projectile extends MovingObject {
    /** Used to store the image of the projectile*/
    private ProjectileType type;
    private int imageId;
    public float scale = 0.10f;

    public Projectile(){
        type=null;
        this.direction=0.0;
        this.velocity=0;
    }
    /** Constructor when type (image), direction and velocity are known. Will be called
     * when Ship.fire() is called */
    public Projectile(ProjectileType type, double d, int v) {
        this.type = type;
        this.direction=d;
        this.velocity=v;
        this.bounds = new RectF((float)(xCoordinate-1),(float)(yCoordinate-1),
                (float)(xCoordinate+1),(float)(xCoordinate+1));
    }

    @Override
    public void update(double time) {
        PointF position = new PointF((float)xCoordinate,(float)yCoordinate);
        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(position, bounds,
                velocity, direction, time);
        this.xCoordinate = result.getNewObjPosition().x;
        this.yCoordinate = result.getNewObjPosition().y;
        this.bounds = result.getNewObjBounds();
    }

    @Override
    public void draw() {
        Point viewCoordinates = Viewport.getInstance().convertToViewCoordinates(this.xCoordinate,
                this.yCoordinate);
        double degrees = GraphicsUtils.radiansToDegrees(this.direction);
        DrawingHelper.drawImage(this.imageId,(int)viewCoordinates.getX(),
                (int)viewCoordinates.getY(),(float)degrees,scale,scale,255);
    }

    public void updateBounds(){
        this.bounds.left = (float)this.xCoordinate-1;
        this.bounds.top = (float)this.yCoordinate-1;
        this.bounds.right = (float)this.xCoordinate+1;
        this.bounds.bottom = (float)this.yCoordinate+1;
    }

    //Getters and setters---------------------------------------------------------------------------/
    public ProjectileType getType() {
        return type;
    }

    public void setType(ProjectileType type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
