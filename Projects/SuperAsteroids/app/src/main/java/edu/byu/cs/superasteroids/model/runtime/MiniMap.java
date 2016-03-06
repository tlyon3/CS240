package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/7/16.
 */

import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Set;

import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameController;

/** Class for the minimap on the in-game screen*/
public class MiniMap extends ObjectWithPosition{
    GameController gameController;
    RectF bounds;
    Set<Asteroid> asteroids;

    public MiniMap(GameController gameController){
        this.gameController = gameController;
    }

    public MiniMap(Set<Asteroid> asteroids){
        this.height = 100;
        this.width = 200;

        //position in top left corner
        this.xCoordinate = 0;
        this.yCoordinate = 0;
        this.bounds = new RectF((float)xCoordinate,(float)yCoordinate,(float)(xCoordinate+width),
                (float)(yCoordinate+height));
        this.asteroids = asteroids;
    }

    private PointF convertToMiniMapCoordinates(double worldX,double worldY){
        float x = (float)(this.width*(worldX/3000));
        float y = (float)(this.height*(worldY/3000));
        return new PointF(x,y);
    }

    /** Override function for Visible.draw()
     * Draws the minimap on the screen.
     * Should be called after MiniMap.update() to account for
     * changing positions*/
    @Override
    public void draw(){
        Rect border = new Rect((int)bounds.left,(int)bounds.top,(int)bounds.right,(int)bounds.bottom);
        DrawingHelper.drawFilledRectangle(bounds,100,100);
        DrawingHelper.drawRectangle(border, 255, 255);

        //draw green box for ship
        Paint shipColor = new Paint();
        shipColor.setARGB(255,0,255,0);
        Ship ship = AsteroidsData.getInstance().getShip();
        PointF drawPoint = convertToMiniMapCoordinates(ship.xCoordinate, ship.yCoordinate);
        DrawingHelper.drawPointWithPaint(drawPoint,shipColor);

        //draw red box for each asteroid
        Paint asteroidColor = new Paint();
        asteroidColor.setARGB(255,255,0,0);
        for(Asteroid asteroid:asteroids){
            PointF draw = convertToMiniMapCoordinates(asteroid.xCoordinate,asteroid.yCoordinate);
            DrawingHelper.drawPointWithPaint(draw,asteroidColor);
        }

    }


}
