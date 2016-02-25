package edu.byu.cs.superasteroids.data;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.TestCase;

import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;

/**
 * Created by tlyon on 2/18/16.
 */
public class ShipPartsDAOTest extends AndroidTestCase {

    ShipPartsDAO shipPartsDAO;
    SQLiteDatabase db;

    public void testAddShipPart() throws Exception {
        //general attributes
        int attachX = 100;
        int attachY = 120;
        String image = "path/to/image";
        int imageHeight = 200;
        int imageWidth = 150;

        //test add engine
        int speed = 10;
        int turnRate = 15;
        Engine engine = new Engine(speed,turnRate,attachX,attachY,image,imageHeight,imageWidth);
        shipPartsDAO.addShipPart(engine);
        assertEquals(1, engine.getId());

        Engine returned = shipPartsDAO.getEngineSet().iterator().next();

        assertEquals(100,returned.getAttachPoint().getX());
        assertEquals(120,returned.getAttachPoint().getY());
        assertEquals("path/to/image",returned.getImagePath());
        assertEquals(imageHeight,returned.getHeight());
        assertEquals(imageWidth,returned.getWidth());
        assertEquals(speed,returned.getBaseSpeed());
        assertEquals(turnRate,returned.getBaseTurnRate());

        //test add mainbody
        int cannonX = 10;
        int cannonY = 15;
        int engineX = 20;
        int engineY = 25;
        int extraX = 11;
        int extraY = 12;
        MainBody mainBody = new MainBody(cannonX,cannonY,engineX,engineY,extraX,extraY,image,
                imageHeight,imageWidth);
        shipPartsDAO.addShipPart(mainBody);
        assertEquals(1, mainBody.getId());
        MainBody actual = shipPartsDAO.getMainBodySet().iterator().next();
        assertEquals("path/to/image",actual.getImagePath());
        assertEquals(imageHeight,actual.getHeight());
        assertEquals(imageWidth,actual.getWidth());
        assertEquals(cannonX,actual.getCannonAttach().getX());
        assertEquals(cannonY,actual.getCannonAttach().getY());
        assertEquals(engineX, actual.getEngineAttach().getX());
        assertEquals(engineY, actual.getEngineAttach().getY());
        assertEquals(extraX, actual.getExtraAttach().getX());
        assertEquals(extraY,actual.getExtraAttach().getY());

        //test add cannon
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
        assertEquals(1, cannon.getId());
        Cannon actualCannon = shipPartsDAO.getCannonSet().iterator().next();
        assertEquals(100,actualCannon.getAttachPoint().getX());
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

        //test add powercores
        int cannonBoost = 1;
        int engineBoost = 2;
        PowerCore powerCore = new PowerCore(cannonBoost,engineBoost,image);
        shipPartsDAO.addShipPart(powerCore);
        assertEquals(1, powerCore.getId());
        PowerCore returnedPowerCore = shipPartsDAO.getPowerCoreSet().iterator().next();
        assertEquals(cannonBoost,returnedPowerCore.getCannonBoost());
        assertEquals(engineBoost,returnedPowerCore.getEngineBoost());
        assertEquals(image,returnedPowerCore.getImagePath());

        //test add extraparts
        ExtraPart extraPart = new ExtraPart(attachX,attachY,image,imageHeight,imageWidth);
        shipPartsDAO.addShipPart(extraPart);
        assertEquals(1, extraPart.getId());
        ExtraPart returnedExtraPart = shipPartsDAO.getExtraPartsSet().iterator().next();
        assertEquals(attachX,returnedExtraPart.getAttachPoint().getX());
        assertEquals(attachY,returnedExtraPart.getAttachPoint().getY());
        assertEquals(image,returnedExtraPart.getImagePath());
        assertEquals(imageHeight,returnedExtraPart.getHeight());
        assertEquals(imageWidth,returnedExtraPart.getWidth());
    }

    public void testDeleteShipPart() throws Exception {

    }

    public void testEmptyShipParts() throws Exception {

    }

    @Override
    protected void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(),"test");
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        shipPartsDAO = new ShipPartsDAO(db);
    }

    @Override
    protected void tearDown() throws Exception {
        db.endTransaction();
    }
}