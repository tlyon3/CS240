package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/6/16.
 */

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.List;
import java.util.Scanner;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model.gamedefinition.ProjectileType;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/** Class for Ship object*/
public class Ship extends MovingObject {
    /** Engine of ship*/
    private Engine engine;
    /** Cannon of ship*/
    private Cannon cannon;
    /** Power Core of ship */
    private PowerCore powerCore;
    /** Main Body of ship */
    private MainBody mainBody;
    /** Extra Part of ship */
    private ExtraPart extraPart;
    /** Image id for engine*/
    private int engineImage;
    /** Image id for cannon*/
    private int cannonImage;
    /** Image id for mainBody*/
    private int mainBodyImage;
    /** Image id for extraPart*/
    private int extraPartImage;
    /** Total height of ship in pixels */
    private int totalHeight;
    /** Total width of ship in pixels */
    private int totalWidth;
    /** Center point of ship. Calculated from totalHeight and totalWidth*/
    private Point centerOfShip;
    /** Coordinates of the cannon's emitPoint in the world*/
    private Point emitPointInWorld;
    /** Shield of ship. Activated when collides with an asteroids*/
    public int shield;
    private RectF normalBounds;

    //Constructors-------------------------------------------------------------------------------/
    public Ship(Engine engine, Cannon cannon, PowerCore powerCore, MainBody mainBody, ExtraPart extraPart) {
        float scale = AsteroidsData.getInstance().getShipScale();
        this.engine = engine;
        this.cannon = cannon;
        this.powerCore = powerCore;
        this.mainBody = mainBody;
        this.extraPart = extraPart;
        this.health = 3;
        this.velocity = 300;
        this.totalHeight =(int)(mainBody.getHeight()*scale + engine.getHeight()*scale);
        this.totalWidth = (int)(mainBody.getWidth()*scale + extraPart.getWidth()*scale + cannon.getWidth()*scale);
        this.bounds = new RectF(0,0,this.totalWidth,this.totalHeight);
        int centerX = totalWidth/2;
        int centerY = totalHeight/2;
        this.centerOfShip = new Point(centerX,centerY);
        this.shield = 0;
        this.emitPointInWorld = calculateEmitWorldPoint();


    }

    public Ship(){
        this.engine = null;
        this.cannon = null;
        this.powerCore = null;
        this.mainBody = null;
        this.extraPart = null;
        this.health = 3;
        this.velocity = 300;
        this.shield = 0;
    }
    //Member functions-----------------------------------------------------------------------/
    /** Fires the cannon */
    public Projectile fire(){
        double velocity = this.velocity*4;
        ProjectileType projectileType = new ProjectileType(cannon.getAttackImage(),
                cannon.getAttackImageHeight(),cannon.getAttackImageWidth());
        Projectile projectile = new Projectile(projectileType,this.direction,(int)velocity);
        Point emitPointInWorld = calculateEmitWorldPoint();
        projectile.setyCoordinate(emitPointInWorld.getY());
        projectile.setxCoordinate(emitPointInWorld.getX());
        return projectile;
    }
    /** Checks to see if ship has a mainbody, engine, cannon, extraPart and powercore
     * @return Returns true if has all parts. False if not*/
    public boolean isComplete(){
        if (engine == null)
            return false;
        else if(cannon == null)
            return false;
        else if(powerCore == null)
            return false;
        else if(mainBody == null)
            return false;
        else if(extraPart == null)
            return false;
        else return true;
    }
    /** Loads the images for the ship parts*/
    public void loadContent(ContentManager content){
        this.engineImage = content.loadImage(this.engine.getImagePath());
        this.cannonImage = content.loadImage(this.cannon.getImagePath());
        this.mainBodyImage = content.loadImage(this.mainBody.getImagePath());
        content.loadImage(this.powerCore.getImagePath());
        this.extraPartImage = content.loadImage(this.extraPart.getImagePath());
    }
    /** Calculates the difference in the angle between the ships current direction and the move point
     * @param movePoint Point given when the user touches the screen
     * @return returns the difference between the current direction and the angle of the movePoint*/
    public double calculateChangeInAngle(PointF movePoint){
        if(movePoint == null)
            return 0.0f;
        else{
            Point viewPosition = Viewport.getInstance().convertToViewCoordinates(this.xCoordinate,
                    this.yCoordinate);
            double y = movePoint.y - viewPosition.getY();
            double x = movePoint.x - viewPosition.getX();
            double theta = Math.atan2(y,x);
            theta += GraphicsUtils.HALF_PI;
            theta = theta - this.direction;
            return theta;
        }
    }
    /** Draws the engine. Called from Ship.draw()*/
    private void drawEngine(){
        //get ship information
        int bodyCenterX = mainBody.getWidth()/2;
        int bodyCenterY = mainBody.getHeight()/2;
        float scale = AsteroidsData.getInstance().getShipScale();
        Point shipViewportCoordinates = Viewport.getInstance().convertToViewCoordinates(xCoordinate, yCoordinate);
        double angleInDegrees = GraphicsUtils.radiansToDegrees(this.direction);

        //get engine information
        int engineCenterX = engine.getWidth()/2;
        int engineCenterY = engine.getHeight()/2;
        int attachFromPartToBodyX = (int)engine.getAttachPoint().getX();
        int attachFromPartToBodyY = (int)engine.getAttachPoint().getY();
        int attachFromBodyToPartX = (int)mainBody.getEngineAttach().getX();
        int attachFromBodyToPartY = (int)mainBody.getEngineAttach().getY();

        //calcualte offset
        float offsetX = (attachFromBodyToPartX - bodyCenterX) + (engineCenterX - attachFromPartToBodyX);
        float offsetY = (attachFromBodyToPartY - bodyCenterY) + (engineCenterY - attachFromPartToBodyY);

        //scale the offset
        offsetX = offsetX * scale;
        offsetY = offsetY * scale;

        //rotate the offset
        PointF engineRotated = GraphicsUtils.rotate(new PointF(offsetX,offsetY),
                this.direction);
        offsetX = engineRotated.x;
        offsetY = engineRotated.y;

        //calculate draw point
        int drawPointX = (int)offsetX + (int)shipViewportCoordinates.getX();
        int drawPointY = (int)offsetY + (int)shipViewportCoordinates.getY();

        //draw the image
        DrawingHelper.drawImage(engineImage,drawPointX,drawPointY,
                (float)angleInDegrees,scale,scale,255);
    }
    /** Draws the cannon. Called from Ship.draw()*/
    private void drawCannon(){
        int bodyCenterX = mainBody.getWidth()/2;
        int bodyCenterY = mainBody.getHeight()/2;
        float scale = AsteroidsData.getInstance().getShipScale();
        Point shipViewportCoordinates = Viewport.getInstance().convertToViewCoordinates(xCoordinate, yCoordinate);
        double angleInDegrees = GraphicsUtils.radiansToDegrees(this.direction);

        int cannonCenterX = cannon.getWidth()/2;
        int cannonCenterY = cannon.getHeight()/2;
        int attachFromCannonToBodyX = (int)cannon.getAttachPoint().getX();
        int attachFromCannonToBodyY = (int)cannon.getAttachPoint().getY();
        int attachFromBodyToCannonX = (int)mainBody.getCannonAttach().getX();
        int attachFromBodyToCannonY = (int)mainBody.getCannonAttach().getY();

        float cannonOffsetX = (attachFromBodyToCannonX - bodyCenterX) + (cannonCenterX - attachFromCannonToBodyX);
        float cannonOffsetY = (attachFromBodyToCannonY - bodyCenterY) + (cannonCenterY - attachFromCannonToBodyY);

        cannonOffsetX = cannonOffsetX * scale;
        cannonOffsetY = cannonOffsetY * scale;

        PointF cannonRotated = GraphicsUtils.rotate(new PointF(cannonOffsetX,cannonOffsetY),
                this.direction);
        cannonOffsetX = cannonRotated.x;
        cannonOffsetY = cannonRotated.y;

        int cannonDrawPointX = (int)cannonOffsetX + (int)shipViewportCoordinates.getX();
        int cannonDrawPointY = (int)cannonOffsetY + (int)shipViewportCoordinates.getY();

        DrawingHelper.drawImage(cannonImage,cannonDrawPointX,cannonDrawPointY,
                (float)angleInDegrees,scale,scale,255);
    }
    /** Draws the extraPart. Called from Ship.draw()*/
    public void drawExtra(){
        int bodyCenterX = mainBody.getWidth()/2;
        int bodyCenterY = mainBody.getHeight()/2;
        float scale = AsteroidsData.getInstance().getShipScale();
        Point shipViewportCoordinates = Viewport.getInstance().convertToViewCoordinates(xCoordinate, yCoordinate);
        double angleInDegrees = GraphicsUtils.radiansToDegrees(this.direction);

        int extraCenterX = extraPart.getWidth()/2;
        int extraCenterY = extraPart.getHeight()/2;
        int attachFromExtraToBodyX = (int)extraPart.getAttachPoint().getX();
        int attachFromExtraToBodyY = (int)extraPart.getAttachPoint().getY();
        int attachFromBodyToExtraX = (int)mainBody.getExtraAttach().getX();
        int attachFromBodyToExtraY = (int)mainBody.getExtraAttach().getY();

        float extraOffsetX = (attachFromBodyToExtraX - bodyCenterX) + (extraCenterX - attachFromExtraToBodyX);
        float extraOffsetY = (attachFromBodyToExtraY - bodyCenterY) + (extraCenterY - attachFromExtraToBodyY);

        extraOffsetX = extraOffsetX * scale;
        extraOffsetY = extraOffsetY * scale;

        PointF extraRotated = GraphicsUtils.rotate(new PointF(extraOffsetX, extraOffsetY),
                this.direction);
        extraOffsetX = extraRotated.x;
        extraOffsetY = extraRotated.y;

        int extraDrawPointX = (int)extraOffsetX + (int)shipViewportCoordinates.getX();
        int extraDrawPointY = (int)extraOffsetY + (int)shipViewportCoordinates.getY();

        DrawingHelper.drawImage(extraPartImage,extraDrawPointX,extraDrawPointY,
                (float)angleInDegrees,scale,scale,255);
    }
    /** Calculates the emitPoint of the cannon in the world
     * @return returns a point with the world coordinates of the emitPoint*/
    private Point calculateEmitWorldPoint(){
        Point shipViewportCoordinates = Viewport.getInstance().convertToViewCoordinates(xCoordinate,yCoordinate);
        int bodyCenterX = mainBody.getWidth()/2;
        int bodyCenterY = mainBody.getHeight()/2;
        float scale = AsteroidsData.getInstance().getShipScale();
        int cannonCenterX = cannon.getWidth()/2;
        int cannonCenterY = cannon.getHeight()/2;
        int attachFromCannonToBodyX = (int)cannon.getAttachPoint().getX();
        int attachFromCannonToBodyY = (int)cannon.getAttachPoint().getY();
        int attachFromBodyToCannonX = (int)mainBody.getCannonAttach().getX();
        int attachFromBodyToCannonY = (int)mainBody.getCannonAttach().getY();

        float cannonOffsetX = (attachFromBodyToCannonX - bodyCenterX) + (cannonCenterX - attachFromCannonToBodyX);
        float cannonOffsetY = (attachFromBodyToCannonY - bodyCenterY) + (cannonCenterY - attachFromCannonToBodyY);

        cannonOffsetX = cannonOffsetX * scale;
        cannonOffsetY = cannonOffsetY * scale;

        PointF cannonRotated = GraphicsUtils.rotate(new PointF(cannonOffsetX,cannonOffsetY),
                this.direction);
        cannonOffsetX = cannonRotated.x;
        cannonOffsetY = cannonRotated.y;

        int cannonDrawPointX = (int)cannonOffsetX + (int)shipViewportCoordinates.getX();
        int cannonDrawPointY = (int)cannonOffsetY + (int)shipViewportCoordinates.getY();

        Point emitPoint = cannon.getEmitPoint();
        //find distance between center and emit point. add it to draw point
        float emitOffsetX = (float)emitPoint.getX() - cannonCenterX;
        float emitOffsetY = cannonCenterY - (float)emitPoint.getY();
        PointF offsetRotated = GraphicsUtils.rotate(new PointF(emitOffsetX, emitOffsetY), this.direction);
        emitOffsetX = offsetRotated.x;
        emitOffsetY = offsetRotated.y;
        float emitX = cannonDrawPointX + emitOffsetX*scale;
        float emitY = cannonDrawPointY + emitOffsetY*scale;

        Point worldPoint = Viewport.getInstance().convertToWorldCoordinates(emitX,emitY);
        return worldPoint;
    }
    /** Initilizes the bounds of the ship*/
    public void initializeBounds(){
        int centerOfShipY = this.totalHeight/2;
        int centerOfShipX = this.totalWidth/2;
        double centerCoordinateY = yCoordinate + centerOfShipY;
        double centerCoordinateX = 0;
        if(this.cannon.getWidth() < this.extraPart.getWidth()){
            centerCoordinateX = centerOfShipX - xCoordinate;
        }
        else{
            centerCoordinateX = centerOfShipX + xCoordinate;
        }

        float scale = AsteroidsData.getInstance().getShipScale();
        int engineHeight = this.getEngine().getHeight();
        int extraWidth = this.getExtraPart().getWidth();
        int cannonWidth = this.getCannon().getWidth();
        int mainBodyHeight = this.getMainBody().getHeight();

        if(this.direction < GraphicsUtils.PI) {
            float top = (float) (centerCoordinateY - (mainBodyHeight / 2) * scale);
            float left = (float) (centerCoordinateX - (extraWidth) * scale);
            float right = (float) (centerCoordinateX + cannonWidth * scale);
            float bottom = (float) (centerCoordinateY) + scale * ((mainBodyHeight / 2) + engineHeight);
            RectF newBounds = new RectF(left,top,right,bottom);
            this.bounds = newBounds;
            this.normalBounds = newBounds;
        }

    }
    /** draws a rectangle around the ship representing its bounds*/
    public void drawBounds(){
        Point viewTopLeft = Viewport.getInstance().convertToViewCoordinates(this.bounds.left,this.bounds.top);
        Point viewBottomRight = Viewport.getInstance().convertToViewCoordinates(this.bounds.right,this.bounds.bottom);
        Rect viewBounds = new Rect((int)viewTopLeft.getX(),(int)viewTopLeft.getY(),(int)viewBottomRight.getX(),(int)viewBottomRight.getY());
        DrawingHelper.drawRectangle(viewBounds,100,255);
    }

    // Override methods ------------------------------------------------------------/
    @Override
    public void update(double time) {
        PointF movepoint = InputManager.movePoint;
        if(shield != 0){
            shield -=1;
        }
        if (movepoint == null) {
            return;
        }
        else{
            float scale = AsteroidsData.getInstance().getShipScale();
            double changeInAngle = calculateChangeInAngle(movepoint);
            this.direction += changeInAngle;
            //facing down
//            if(this.direction > GraphicsUtils.THREE_FOURTH_PI && this.direction < GraphicsUtils.FIVE_FOURTH_PI){
//                this.bounds.top = this.normalBounds.top - this.totalHeight/2;
//                this.bounds.bottom = this.normalBounds.bottom - this.totalHeight/2;
//            }
//            //facing right
//            else if (this.direction > GraphicsUtils.FOURTH_PI && this.direction < GraphicsUtils.THREE_FOURTH_PI){
//                float offsetTop = this.normalBounds.top + totalHeight/2;
//                this.bounds.top = this.normalBounds.top - offsetTop;
//                float offsetBottom = this.normalBounds.bottom - totalHeight/2;
//               // this.bounds.bottom -= totalHeight/2;
//                this.bounds.bottom = this.normalBounds.bottom + offsetBottom;
//                float offsetRight = this.normalBounds.right - totalWidth/2;
//                //this.bounds.right -= totalWidth/2;
//                this.bounds.right = this.normalBounds.right + offsetRight;
//                float offsetLeft = this.normalBounds.left + totalWidth/2;
//                //this.bounds.left += totalWidth/2;
//                this.bounds.left = this.normalBounds.left - offsetLeft;
//            }
//            //facing up
//            else if(this.direction < GraphicsUtils.FOURTH_PI || this.direction > GraphicsUtils.SEVEN_FOURTH_PI){
//                this.bounds = normalBounds;
//            }
//            //facing left
//            else if(this.direction > GraphicsUtils.FIVE_FOURTH_PI && this.direction < GraphicsUtils.SEVEN_FOURTH_PI){
//                float offsetTop = this.normalBounds.top + totalHeight/2;
//                this.bounds.top = this.normalBounds.top + offsetTop;
//                float offsetBottom = this.normalBounds.bottom - totalHeight/2;
//                // this.bounds.bottom -= totalHeight/2;
//                this.bounds.bottom = this.normalBounds.bottom - offsetBottom;
//                float offsetRight = this.normalBounds.right - totalWidth/2;
//                //this.bounds.right -= totalWidth/2;
//                this.bounds.right = this.normalBounds.right - offsetRight;
//                float offsetLeft = this.normalBounds.left + totalWidth/2;
//                //this.bounds.left += totalWidth/2;
//                this.bounds.left = this.normalBounds.left + offsetLeft;
//            }
            PointF position = new PointF((float)this.xCoordinate,(float)this.yCoordinate);
            GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(position,this.bounds,
                    this.velocity,this.direction,time);
            this.bounds = result.getNewObjBounds();
            this.xCoordinate = result.getNewObjPosition().x;
            this.yCoordinate = result.getNewObjPosition().y;

            int engineHeight = this.getEngine().getHeight();
            int extraWidth = this.getExtraPart().getWidth();
            int cannonWidth = this.getCannon().getWidth();
            int mainBodyHeight = this.getMainBody().getHeight();

            float top = (float)(yCoordinate - (mainBodyHeight/2)*scale);
            float left = (float)(xCoordinate - (extraWidth)*scale);
            float right = (float)(xCoordinate + cannonWidth*scale);
            float bottom = (float)(yCoordinate) + scale*((mainBodyHeight/2) + engineHeight);

            RectF newBounds = new RectF(left,top,right,bottom);
            this.bounds = newBounds;
        }
        AsteroidsData.getInstance().setShip(this);
    }
    /** Draws the main body then calls the respective part draw functions*/
    @Override
    public void draw() {
//        drawBounds();
        float scale = AsteroidsData.getInstance().getShipScale();
        double angleInDegrees = GraphicsUtils.radiansToDegrees(this.direction);
        Point shipViewportCoordinates = Viewport.getInstance().convertToViewCoordinates(xCoordinate,yCoordinate);
        DrawingHelper.drawImage(mainBodyImage,(int)shipViewportCoordinates.getX(),
                (int)shipViewportCoordinates.getY(), (float)angleInDegrees,scale,scale,255);
        drawEngine();
        drawCannon();
        drawExtra();
    }

    @Override
    public void takeDamage(int d) {
        super.takeDamage(d);

    }

    //Getters and setters-------------------------------------------------------------------------/
    public Engine getEngine() {
        return engine;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public PowerCore getPowerCore() {
        return powerCore;
    }

    public MainBody getMainBody() {
        return mainBody;
    }

    public ExtraPart getExtraPart() {
        return extraPart;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setCannon(Cannon cannon) {
        this.cannon = cannon;
    }

    public void setPowerCore(PowerCore powerCore) {
        this.powerCore = powerCore;
    }

    public void setMainBody(MainBody mainBody) {
        this.mainBody = mainBody;
    }

    public void setExtraPart(ExtraPart extraPart) {
        this.extraPart = extraPart;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getEngineImage() {
        return engineImage;
    }

    public void setEngineImage(int engineImage) {
        this.engineImage = engineImage;
    }

    public int getCannonImage() {
        return cannonImage;
    }

    public void setCannonImage(int cannonImage) {
        this.cannonImage = cannonImage;
    }

    public int getMainBodyImage() {
        return mainBodyImage;
    }

    public void setMainBodyImage(int mainBodyImage) {
        this.mainBodyImage = mainBodyImage;
    }

    public int getExtraPartImage() {
        return extraPartImage;
    }

    public void setExtraPartImage(int extraPartImage) {
        this.extraPartImage = extraPartImage;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public void setTotalHeight(int totalHeight) {
        this.totalHeight = totalHeight;
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    public void setTotalWidth(int totalWidth) {
        this.totalWidth = totalWidth;
    }

    public RectF getBounds() {
        return bounds;
    }

    public void setBounds(RectF bounds) {
        this.bounds = bounds;
    }

    public Point getCenterOfShip() {
        return centerOfShip;
    }

    public void setCenterOfShip(Point centerOfShip) {
        this.centerOfShip = centerOfShip;
    }

    public Point getEmitPointInWorld() {
        return emitPointInWorld;
    }

    public void setEmitPointInWorld(Point emitPointInWorld) {
        this.emitPointInWorld = emitPointInWorld;
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }
}
