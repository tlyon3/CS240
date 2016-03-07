package edu.byu.cs.superasteroids.game;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.AsteroidsData;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.runtime.Asteroid;
import edu.byu.cs.superasteroids.model.runtime.BGObject;
import edu.byu.cs.superasteroids.model.runtime.MiniMap;
import edu.byu.cs.superasteroids.model.runtime.Projectile;
import edu.byu.cs.superasteroids.model.runtime.Ship;
import edu.byu.cs.superasteroids.model.runtime.Viewport;

/**
 * Created by tlyon on 2/25/16.
 * Keeps track of all objects, detects collisions, handles level transitions
 */
public class GameController implements IGameDelegate {
    private Set<Asteroid> asteroids;
    private Ship ship;
    private Set<BGObject> bgObjects;
    private Map<BGObject, Integer> backgroundObjects;
    private Map<AsteroidType,Integer> levelAsteroidsMap;
    private List<Projectile> projectiles;
    private int currentLevel;
    private List<Integer> asteroidImages;
    private List<Integer> backgroundImages;
    private MiniMap miniMap;
    private int viewPortHeight;
    private int viewPortWidth;
    private int starFieldId;
    private int attackSound;
    ContentManager contentManager;
    /** Cannon's cooldown*/
    private int fireDelay = 0;
    /** Used to stop the ship from firing during cannon cooldown*/
    private boolean shouldFire = true;
    /** Used to stop updates while loading next level data*/
    private boolean performUpdate;
    /** Used to determine when to draw the blue circle around the ship*/
    private boolean drawShield;

    public GameController(){
        this.performUpdate = false;
        this.currentLevel = AsteroidsData.getInstance().getCurrentLevel();
        this.ship = AsteroidsData.getInstance().getShip();
        int levelWidth = AsteroidsData.getInstance().getLevelWithId(currentLevel).getWidth();
        int levelHeight = AsteroidsData.getInstance().getLevelWithId(currentLevel).getHeight();
        ship.setxCoordinate(levelWidth / 2);
        ship.setyCoordinate(levelHeight / 2);
        ship.initializeBounds();
        this.backgroundObjects = AsteroidsData.getInstance().getLevelWithId(currentLevel).getLevelBackgroundObjectsMap();
        this.levelAsteroidsMap = AsteroidsData.getInstance().getLevelWithId(currentLevel).getLevelAsteroidsMap();
        this.viewPortHeight = Viewport.getInstance().getHeight();
        this.viewPortWidth = Viewport.getInstance().getWidth();
        this.asteroids = extractAsteroidsFromMap(levelAsteroidsMap);
        this.bgObjects = extractFromBGObjectMap(backgroundObjects);
        this.projectiles = new ArrayList<>();
        this.asteroidImages = new ArrayList<>();
        this.backgroundImages = new ArrayList<>();
        this.miniMap = new MiniMap(this.asteroids);
        this.performUpdate = true;
    }
    /** Loads data from AsteroidsData for the current level*/
    public void loadData(){
        this.currentLevel = AsteroidsData.getInstance().getCurrentLevel();
        this.ship = AsteroidsData.getInstance().getShip();
        int levelWidth = AsteroidsData.getInstance().getLevelWithId(currentLevel).getWidth();
        int levelHeight = AsteroidsData.getInstance().getLevelWithId(currentLevel).getHeight();
        ship.setxCoordinate(levelWidth / 2);
        ship.setyCoordinate(levelHeight / 2);
        ship.initializeBounds();
        Viewport.getInstance().initialize();
        this.backgroundObjects = AsteroidsData.getInstance().getLevelWithId(currentLevel).getLevelBackgroundObjectsMap();
        this.levelAsteroidsMap = AsteroidsData.getInstance().getLevelWithId(currentLevel).getLevelAsteroidsMap();
        this.viewPortHeight = Viewport.getInstance().getHeight();
        this.viewPortWidth = Viewport.getInstance().getWidth();
        this.asteroids = extractAsteroidsFromMap(levelAsteroidsMap);
        this.bgObjects = extractFromBGObjectMap(backgroundObjects);
        this.projectiles = new ArrayList<>();
        this.miniMap = new MiniMap(this.asteroids);
        this.performUpdate = true;
        this.drawShield = false;
        Viewport.getInstance().setCurrentLevel(AsteroidsData.getInstance().getLevelWithId(currentLevel));
        for(BGObject bgObject:this.bgObjects){
            int id = this.contentManager.loadImage(bgObject.getType().getImagePath());
            bgObject.setImageId(id);
        }
        for(Asteroid asteroid:asteroids){
            int id = this.contentManager.loadImage(asteroid.getType().getImagePath());
            asteroid.setImageId(id);
        }
    }
    /** Increments currentLevel and calls loadData*/
    public void nextLevel(){
        this.performUpdate = false;
        boolean loadData = true;
        if(currentLevel == AsteroidsData.getInstance().getLevels().size()) {
            loadData = false;
            endGame(true);
            this.performUpdate = false;
        }
        AsteroidsData.getInstance().incrementLevel();
        DrawingHelper.drawFilledRectangle(Viewport.getInstance().getBounds(), 0, 255);
        DrawingHelper.drawText(new Point(512, 150), "Level " + currentLevel + " complete!", 255, 50);
        DrawingHelper.drawCenteredText("Starting level " + (currentLevel + 1), 50, 255);
        if(loadData) {
            loadData();
        }
    }
    /** Displays "You Won!" if levels were completed and "You lost!" if ship dies*/
    public void endGame(Boolean didWin){
        if(didWin) {
            DrawingHelper.drawCenteredText("You won!", 50, 255);
        }
        else {
            DrawingHelper.drawCenteredText("You lost!", 50, 255);
        }
    }
    /** Breaks the asteroid when life reaches 0
     * @param asteroid Asteroid whose life reached 0
     * @return A new asteroid of the same type as it's parent*/
    public Asteroid breakAsteroid(Asteroid asteroid){
        Random rand = new Random();
        AsteroidType asteroidType = asteroid.getType();
        double direction = GraphicsUtils.TWO_PI*rand.nextDouble();
        Asteroid newAsteroid = new Asteroid(asteroidType,direction,asteroid.getVelocity());
        newAsteroid.alreadyBroken = true;
        newAsteroid.setScale(0.5f);
        newAsteroid.setxCoordinate(asteroid.getxCoordinate() + (20 + (30) * rand.nextDouble()));
        newAsteroid.setyCoordinate(asteroid.getyCoordinate() + (20 + (30) * rand.nextDouble()));
        newAsteroid.setImageId(asteroid.getImageId());
        newAsteroid.updateBounds();
        return newAsteroid;
    }
    /** Draws the starfield*/
    private void drawSpace(){
        int levelWidth = AsteroidsData.getInstance().getLevelWithId(currentLevel).getWidth();
        int levelHeight = AsteroidsData.getInstance().getLevelWithId(currentLevel).getHeight();
        double left = (Viewport.getInstance().getBounds().left/levelWidth)*2048;
        double top = (Viewport.getInstance().getBounds().top/levelHeight)*2048;
        double right = (Viewport.getInstance().getBounds().right/levelWidth)*2048;
        double bottom = (Viewport.getInstance().getBounds().bottom/levelHeight)*2048;

        int viewTop = 0;
        int viewLeft = 0;
        int viewRight = DrawingHelper.getGameViewWidth();
        int viewBottom = DrawingHelper.getGameViewHeight();

        Rect starRect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        Rect viewRect = new Rect(viewLeft,viewTop,viewRight,viewBottom);

        DrawingHelper.drawImage(starFieldId, starRect, viewRect);
    }
    /** Populates the asteroids set.
     * @param map Key is the type of asteroid to be created. Value is the number of each
     * @return Returns the set of asteroids for the level*/
    private Set<Asteroid> extractAsteroidsFromMap(Map<AsteroidType,Integer> map){
        Set<Asteroid> result = new HashSet<>();
        Iterator it = map.entrySet().iterator();
        Random rand = new Random();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            for(int i=0;i<(int)pair.getValue();i++){
                Asteroid newAsteroid = new Asteroid((AsteroidType)pair.getKey(),0.0,2);
                newAsteroid.setxCoordinate(rand.nextInt() % 1300 + 1400);
                newAsteroid.setyCoordinate(rand.nextInt() % 1300 + 1400);
                newAsteroid.setDirection(GraphicsUtils.TWO_PI * rand.nextDouble());
                newAsteroid.setVelocity(rand.nextInt() % 5 + 30);
                newAsteroid.updateBounds();
                result.add(newAsteroid);
            }
        }
        return result;
    }
    /** Populates the Background objects set
     * @param map Key is the background object. Integer is the number of that object
     * @return Returns the set of background objects for the level*/
    private Set<BGObject> extractFromBGObjectMap(Map<BGObject,Integer> map){
        Set<BGObject> result = new HashSet<>();
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            for(int i=0;i<(int)pair.getValue();i++){
                BGObject bgObject = (BGObject)pair.getKey();
                bgObject.updateBounds();
                result.add(bgObject);
            }
        }
        return result;
    }
    /** Updates asteroids, background objects, ship, and viewport
     * @param elapsedTime Time between updates. Used to calculate distance traveled*/
    @Override
    public void update(double elapsedTime) {
        if(!performUpdate)
            return;
        if(ship.getHealth() == 0)
            endGame(false);

        //update asteroids
        for(Asteroid asteroid:asteroids)
            asteroid.update(elapsedTime);

        //update projectiles
        for(Projectile projectile:projectiles)
            projectile.update(elapsedTime);

        //update ship
        ship.update(elapsedTime);
        if(ship.shield == 0){
            drawShield = false;
        }
        else drawShield = true;

        //update viewport
        Viewport.getInstance().update(elapsedTime);

        //check for user input.
        if(InputManager.movePoint != null && this.fireDelay==0 && shouldFire){
            shouldFire = false;
            Projectile projectile = ship.fire();
            this.contentManager.playSound(attackSound,1.0f,1.0f);
            projectile.setImageId(ship.getCannon().getProjectileImageId());
            projectile.updateBounds();
            this.projectiles.add(projectile);
            if(fireDelay==0)
                this.fireDelay = 10;
        }
        //reset the cannon fire when the user takes finger off of the tablet
        else if(InputManager.movePoint == null){
            shouldFire = true;
        }
        if(fireDelay!=0)
            this.fireDelay-=1;
        Viewport.getInstance().update(elapsedTime);

        //check for all asteroids destroyed
        if(asteroids.size() == 0){
            nextLevel();
        }
        //check for collisions
        else{
            List<Asteroid> asteroidsToDelete = new ArrayList<>();
            List<Asteroid> asteroidsToAdd = new ArrayList<>();
            for (Asteroid asteroid : asteroids) {

                //check if ship ran into asteroid
                if (ship.getBounds().intersect(asteroid.getBounds()) && ship.shield == 0) {
                    ship.takeDamage(1);
                    ship.shield = 100;
                    if(asteroid.shield == 0){
                        asteroid.takeDamage(1);
                    }
                    else if(asteroid.shield != 0){
                        asteroid.shield -= 1;
                    }
                }
                //check if any projectiles hit an asteroid
                List<Projectile> projectilesToDelete = new ArrayList<>();
                for (Projectile projectile : projectiles) {
                    if (projectile.getBounds().intersect(asteroid.getBounds())) {
                        asteroid.takeDamage(ship.getCannon().getDamage());
                        projectilesToDelete.add(projectile);
                    }
                }
                for(Projectile projectile:projectilesToDelete){
                    this.projectiles.remove(projectile);
                }
                //check if asteroid should be destroyed
                if(asteroid.getHealth() == 0){
                    asteroidsToDelete.add(asteroid);
                    if(asteroid.getType().getType().equals("regular") && !asteroid.alreadyBroken) {
                        for(int i=0;i<2;i++) {
                            asteroidsToAdd.add(breakAsteroid(asteroid));
                        }
                    }
                    else if(asteroid.getType().getType().equals("octeroid") && !asteroid.alreadyBroken) {
                        for(int i=0;i<8;i++) {
                            asteroidsToAdd.add(breakAsteroid(asteroid));
                        }
                    }
                    else if(asteroid.getType().getType().equals("growing") && !asteroid.alreadyBroken) {
                        for(int i=0;i<2;i++) {
                            asteroidsToAdd.add(breakAsteroid(asteroid));
                        }
                    }
                }
                if(asteroid.getType().getType().equals("growing") && asteroid.getScale() < 0.75f){
                    asteroid.setScale(asteroid.getScale()+0.005f);
                }
            }
            for(Asteroid asteroid:asteroidsToDelete){
                asteroids.remove(asteroid);
            }
            for(Asteroid asteroid:asteroidsToAdd){
                asteroids.add(asteroid);
            }

        }
        //update minimap
        miniMap.update(elapsedTime);
    }
    /** Loads all images for the game*/
    @Override
    public void loadContent(ContentManager content) {
        this.contentManager = content;
        for(Asteroid asteroid:asteroids){
            int imageId = content.loadImage(asteroid.getType().getImagePath());
            asteroid.setImageId(imageId);
        }
        for(BGObject bgObject:bgObjects){
            int imageId = content.loadImage(bgObject.getType().getImagePath());
            bgObject.setImageId(imageId);
        }
        Viewport.getInstance().setHeight(DrawingHelper.getGameViewHeight());
        Viewport.getInstance().setWidth(DrawingHelper.getGameViewWidth());

        ship.setEngineImage(content.loadImage(ship.getEngine().getImagePath()));
        ship.setCannonImage(content.loadImage(ship.getCannon().getImagePath()));
        ship.setMainBodyImage(content.loadImage(ship.getMainBody().getImagePath()));
        ship.setExtraPartImage(content.loadImage(ship.getExtraPart().getImagePath()));
        ship.getCannon().setProjectileImageId(content.loadImage(ship.getCannon().getAttackImage()));
        this.starFieldId = content.loadImage("images/space.bmp");
        try {
            this.attackSound = content.loadSound(ship.getCannon().getAttackSound());
        }
        catch (IOException ex){
            Log.d("Sound","Unable to load sound");
        }
    }
    /** Unused right now*/
    @Override
    public void unloadContent(ContentManager content) {
        //unused right now
    }
    /** Draws space, background objects, ship, and asteroids*/
    @Override
    public void draw() {
        //draw starfield
        drawSpace();

        //draw background objects
        for(BGObject bgObject:bgObjects){
            bgObject.draw();
        }

        //draw ship
        this.ship.draw();
        if(drawShield){
            edu.byu.cs.superasteroids.model.runtime.shipparts.Point drawPoint = Viewport.getInstance().convertToViewCoordinates(ship.getxCoordinate(),ship.getyCoordinate());
            DrawingHelper.drawFilledCircle(new PointF((float)drawPoint.getX(),(float)drawPoint.getY()),ship.getTotalHeight()/2,200,100);
        }
        //draw all asteroids
        for(Asteroid asteroid:asteroids) {
            //if is in viewport, draw
            if(Viewport.getInstance().isIntersectingWithViewport(asteroid)){
                asteroid.draw();
            }
            if(Viewport.getInstance().isInViewport(asteroid)){
                asteroid.draw();
            }
        }
        //draw all projectiles
        List<Projectile> projectilesToDelete = new ArrayList<>();
        for(Projectile projectile:projectiles){
            if(Viewport.getInstance().isInViewport(projectile))
                projectile.draw();
            else
                projectilesToDelete.add(projectile);
        }
        for(Projectile projectile:projectilesToDelete){
            this.projectiles.remove(projectile);
        }
        //draw minimap
        miniMap.draw();
    }

    // Getters and setters--------------------------------------------------------------
    public Set<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(Set<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Map<BGObject, Integer> getBackgroundObjects() {
        return backgroundObjects;
    }

    public void setBackgroundObjects(Map<BGObject, Integer> backgroundObjects) {
        this.backgroundObjects = backgroundObjects;
    }

    public Map<AsteroidType, Integer> getLevelAsteroidsMap() {
        return levelAsteroidsMap;
    }

    public void setLevelAsteroidsMap(Map<AsteroidType, Integer> levelAsteroidsMap) {
        this.levelAsteroidsMap = levelAsteroidsMap;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(List<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Integer> getAsteroidImages() {
        return asteroidImages;
    }

    public void setAsteroidImages(List<Integer> asteroidImages) {
        this.asteroidImages = asteroidImages;
    }

    public List<Integer> getBackgroundImages() {
        return backgroundImages;
    }

    public void setBackgroundImages(List<Integer> backgroundImages) {
        this.backgroundImages = backgroundImages;
    }

    public int getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(int viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    public int getViewPortWidth() {
        return viewPortWidth;
    }

    public void setViewPortWidth(int viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }
}
