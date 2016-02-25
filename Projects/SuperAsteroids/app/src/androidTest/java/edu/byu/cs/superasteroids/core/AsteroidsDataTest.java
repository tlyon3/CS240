package edu.byu.cs.superasteroids.core;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.HashMap;
import java.util.Map;

import edu.byu.cs.superasteroids.data.AsteroidsDAO;
import edu.byu.cs.superasteroids.data.DbOpenHelper;
import edu.byu.cs.superasteroids.data.LevelDAO;
import edu.byu.cs.superasteroids.data.ShipPartsDAO;
import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.BGObject;
import edu.byu.cs.superasteroids.model.runtime.Level;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/22/16.
 */
public class AsteroidsDataTest extends AndroidTestCase {

    LevelDAO levelDAO;
    ShipPartsDAO shipPartsDAO;
    AsteroidsDAO asteroidsDAO;
    SQLiteDatabase db;
    int attachX = 100;
    int attachY = 120;
    String image = "path/to/image";
    int imageHeight = 200;
    int imageWidth = 150;

    public void testGetCannons() throws Exception {
        Point emitPoint = new Point(10,10);
        Point attachPoint = new Point(attachX,attachY);
        String attackImage = "attack/image/";
        int attackImageWidth = 15;
        int attackImageHeight = 11;
        String attackSound = "attack/sound";
        int damage = 1;
        Cannon cannon = new Cannon(imageHeight,imageWidth,image,1,attachPoint,emitPoint,
                attackImage,attackImageWidth,attackImageHeight,attackSound,damage);
        shipPartsDAO.addShipPart(cannon);
        AsteroidsData.getInstance().setCannons(shipPartsDAO.getCannonSet());

        Cannon actualCannon = AsteroidsData.getInstance().getCannons().iterator().next();

        assertEquals(100, actualCannon.getAttachPoint().getX());
        assertEquals(120,actualCannon.getAttachPoint().getY());
        assertEquals("path/to/image",actualCannon.getImagePath());
        assertEquals(imageHeight,actualCannon.getHeight());
        assertEquals(imageWidth,actualCannon.getWidth());
        assertEquals(emitPoint,actualCannon.getEmitPoint());
        assertEquals(attachPoint,actualCannon.getAttachPoint());
        assertEquals(attackImage, actualCannon.getAttackImage());
        assertEquals(attackImageWidth, actualCannon.getAttackImageWidth());
        assertEquals(attackImageHeight, actualCannon.getAttackImageHeight());
        assertEquals(attackSound, actualCannon.getAttackSound());
        assertEquals(damage,actualCannon.getDamage());
    }

    public void testGetMainBodies() throws Exception {
        int cannonX = 10;
        int cannonY = 15;
        int engineX = 20;
        int engineY = 25;
        int extraX = 11;
        int extraY = 12;
        MainBody mainBody = new MainBody(cannonX,cannonY,engineX,engineY,extraX,extraY,image,
                imageHeight,imageWidth);
        shipPartsDAO.addShipPart(mainBody);
        AsteroidsData.getInstance().setMainBodies(shipPartsDAO.getMainBodySet());

        MainBody actual = AsteroidsData.getInstance().getMainBodies().iterator().next();

        assertEquals("path/to/image", actual.getImagePath());
        assertEquals(imageHeight,actual.getHeight());
        assertEquals(imageWidth,actual.getWidth());
        assertEquals(cannonX,actual.getCannonAttach().getX());
        assertEquals(cannonY,actual.getCannonAttach().getY());
        assertEquals(engineX, actual.getEngineAttach().getX());
        assertEquals(engineY, actual.getEngineAttach().getY());
        assertEquals(extraX, actual.getExtraAttach().getX());
        assertEquals(extraY,actual.getExtraAttach().getY());
    }

    public void testGetEngines() throws Exception {
        int speed = 10;
        int turnRate = 15;
        Engine engine = new Engine(speed,turnRate,attachX,attachY,image,imageHeight,imageWidth);
        shipPartsDAO.addShipPart(engine);
        assertEquals(1, engine.getId());

        Engine actual = AsteroidsData.getInstance().getEngines().iterator().next();

        assertEquals(100,actual.getAttachPoint().getX());
        assertEquals(120, actual.getAttachPoint().getY());
        assertEquals("path/to/image", actual.getImagePath());
        assertEquals(imageHeight, actual.getHeight());
        assertEquals(imageWidth, actual.getWidth());
        assertEquals(speed, actual.getBaseSpeed());
        assertEquals(turnRate, actual.getBaseTurnRate());
    }

    public void testGetExtraParts() throws Exception {
        ExtraPart extraPart = new ExtraPart(attachX,attachY,image,imageHeight,imageWidth);
        shipPartsDAO.addShipPart(extraPart);
        assertEquals(1, extraPart.getId());

        ExtraPart actual = AsteroidsData.getInstance().getExtraParts().iterator().next();

        assertEquals(attachX,actual.getAttachPoint().getX());
        assertEquals(attachY,actual.getAttachPoint().getY());
        assertEquals(image,actual.getImagePath());
        assertEquals(imageHeight, actual.getHeight());
        assertEquals(imageWidth,actual.getWidth());
    }

    public void testGetPowerCores() throws Exception {
        int cannonBoost = 1;
        int engineBoost = 2;
        PowerCore expected = new PowerCore(cannonBoost,engineBoost,image);
        shipPartsDAO.addShipPart(expected);
        assertEquals(1, expected.getId());

        PowerCore actual = AsteroidsData.getInstance().getPowerCores().iterator().next();

        assertEquals(attachX,actual.getAttachPoint().getX());
        assertEquals(attachY,actual.getAttachPoint().getY());
        assertEquals(image,actual.getImagePath());
        assertEquals(imageHeight, actual.getHeight());
        assertEquals(imageWidth,actual.getWidth());
    }

    public void testGetBackgroundObjects() throws Exception {
        BGObjectType expected = new BGObjectType("path/to/image");
        levelDAO.addBGObject(expected);
        //make sure bgObjectType was inserted
        assertEquals(1,expected.getId());

        BGObjectType actual = AsteroidsData.getInstance().getBackgroundObjects().iterator().next();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getImagePath(),actual.getImagePath());
    }

    public void testGetAsteroidTypes() throws Exception {
        AsteroidType expected = new AsteroidType(12,10,"path/to/image","name","type");
        asteroidsDAO.addAsteroid(expected);
        assertEquals(1, expected.getId());

        AsteroidType actual = AsteroidsData.getInstance().getAsteroidTypes().iterator().next();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getImagePath(),actual.getImagePath());
        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getType(),actual.getType());
    }

    public void testGetLevels() throws Exception {
        Map<BGObject,Integer> bgObjectIntegerMap = new HashMap<>();
        BGObjectType objectType = new BGObjectType(10,12,"path/to/object");
        BGObject bgObject = new BGObject(objectType,100,70,(float)1.5);

        bgObjectIntegerMap.put(bgObject,1);
        levelDAO.addBGObject(objectType);
        levelDAO.addLevelObject(bgObject, 1);

        Map<AsteroidType,Integer> asteroidTypeIntegerMap = new HashMap<>();
        AsteroidType asteroidType = new AsteroidType(9,4,"path/to/asteroid","name","type");
        asteroidsDAO.addAsteroid(asteroidType);
        asteroidTypeIntegerMap.put(asteroidType, 1);
        levelDAO.addLevelAsteroid(asteroidType, 1, 1);

        Level expected = new Level(bgObjectIntegerMap,asteroidTypeIntegerMap,"title","hint",100,100,"path/to/music",1);
        levelDAO.addLevel(expected);
        assertEquals(1, expected.getId());

        Level actual = AsteroidsData.getInstance().getLevels().iterator().next();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getHint(),actual.getHint());
        assertEquals(expected.getMusic(),actual.getMusic());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getNumber(),actual.getNumber());
    }

    @Override
    protected void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(),"Test");
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        levelDAO = new LevelDAO(db);
        asteroidsDAO = new AsteroidsDAO(db);
        shipPartsDAO = new ShipPartsDAO(db);
    }

    @Override
    protected void tearDown() throws Exception {
        db.endTransaction();
    }
}