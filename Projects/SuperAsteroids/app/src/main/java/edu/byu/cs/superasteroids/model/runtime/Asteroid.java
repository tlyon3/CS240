package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.AbstractSequentialList;

import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/** Super-class for all Asteroid objects*/
public class Asteroid extends MovingObject {
    public Asteroid(){
        this.type=null;
        this.direction=0;
        this.velocity=0;
        this.health=2;
        this.bounds = new RectF(0,0,0,0);
        this.scale = 0.75f;
        this.alreadyBroken = false;
    }
    public Asteroid(AsteroidType at, double d, int v){
        this.type=at;
        this.velocity=v*2;
        this.direction=d;
        this.directionSin = Math.sin(d);
        this.directionCos = Math.cos(d);
        this.health=2;
        this.scale = 0.75f;
        this.width = at.getWidth();
        this.height = at.getHeight();
        float top = (float)yCoordinate - (height/2)*scale;
        float bottom = (float)yCoordinate + (height/2)*scale;
        float left = (float)xCoordinate - (width/2)*scale;
        float right = (float)xCoordinate + (width/2)*scale;
        this.bounds = new RectF(left,top,right,bottom);
        updateBounds();
        this.alreadyBroken = false;
        this.shield = 100;
    }
    /** Type of asteroid */
    protected AsteroidType type;
    /** Scale of the asteroid*/
    private float scale;
    /** Shield of asteroid. Used to prevent the ship from damaging it every tick*/
    public int shield;
    /** Boolean value if the asteroid should break again or not*/
    public boolean alreadyBroken;
    /** Updates the bounds of the asteroid based of off its position*/
    public void updateBounds(){
        float top = ((float)yCoordinate - (height/2)*scale);
        float bottom = ((float)yCoordinate + (height/2)*scale);
        float left = ((float)xCoordinate - (width/2)*scale);
        float right = ((float)xCoordinate + (width/2)*scale);
        this.bounds = new RectF(left,top,right,bottom);
    }
    /** Draws a rectangle around the asteroid representing its bounds*/
    public void drawBounds(){
        Point viewTopLeft = Viewport.getInstance().convertToViewCoordinates(this.bounds.left,this.bounds.top);
        Point viewBottomRight = Viewport.getInstance().convertToViewCoordinates(this.bounds.right,this.bounds.bottom);
        Rect viewBounds = new Rect((int)viewTopLeft.getX(),(int)viewTopLeft.getY(),(int)viewBottomRight.getX(),(int)viewBottomRight.getY());
        DrawingHelper.drawRectangle(viewBounds,200,255);
    }
    @Override
    public void draw() {
        Point drawPoint = Viewport.getInstance().convertToViewCoordinates(xCoordinate,yCoordinate);
        DrawingHelper.drawImage(this.imageId,(int)drawPoint.getX(),(int)drawPoint.getY(),0,scale,scale,255);
    }

    @Override
    public void update(double time) {
        PointF currentPosition = new PointF((float)xCoordinate,(float)yCoordinate);
        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(currentPosition,this.bounds,
                this.velocity,this.directionCos,this.directionSin,time);
        this.xCoordinate = result.getNewObjPosition().x;
        this.yCoordinate = result.getNewObjPosition().y;
        currentPosition = result.getNewObjPosition();
        this.bounds = result.getNewObjBounds();

        //bounce of wall if needed
        AsteroidsData asteroidsData = AsteroidsData.getInstance();
        int worldHeight = asteroidsData.getLevelWithId(asteroidsData.getCurrentLevel()).getHeight();
        int worldWidth = asteroidsData.getLevelWithId(asteroidsData.getCurrentLevel()).getWidth();
        GraphicsUtils.RicochetObjectResult ricochetResult = GraphicsUtils.ricochetObject(currentPosition,
                this.bounds, this.direction, worldWidth, worldHeight);

        this.xCoordinate = ricochetResult.getNewObjPosition().x;
        this.yCoordinate = ricochetResult.getNewObjPosition().y;
        this.bounds = ricochetResult.getNewObjBounds();
        this.direction = ricochetResult.getNewAngleRadians();
        this.directionSin = ricochetResult.getNewAngleSine();
        this.directionCos = ricochetResult.getNewAngleCosine();
    }

    public AsteroidType getType() {
        return type;
    }

    public void setType(AsteroidType type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }


}
