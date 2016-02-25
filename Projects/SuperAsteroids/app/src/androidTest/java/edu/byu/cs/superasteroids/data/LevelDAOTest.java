package edu.byu.cs.superasteroids.data;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.BGObject;
import edu.byu.cs.superasteroids.model.runtime.Level;

/**
 * Created by tlyon on 2/18/16.
 */
public class LevelDAOTest extends AndroidTestCase {

    LevelDAO levelDAO;
    AsteroidsDAO asteroidsDAO;
    SQLiteDatabase db;

    public void testAddLevel() throws Exception {
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
        levelDAO.addLevelAsteroid(asteroidType,1,1);

        Level expected = new Level(bgObjectIntegerMap,asteroidTypeIntegerMap,"title","hint",100,100,"path/to/music",1);
        levelDAO.addLevel(expected);
        assertEquals(1,expected.getId());

        Level actual = levelDAO.getLevels().iterator().next();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getHint(),actual.getHint());
        assertEquals(expected.getMusic(),actual.getMusic());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getNumber(),actual.getNumber());
    }

    public void testAddBGObject() throws Exception {
        BGObjectType expected = new BGObjectType("path/to/image");
        levelDAO.addBGObject(expected);
        //make sure bgObjectType was inserted
        assertEquals(1,expected.getId());

        Set<BGObjectType> allObjects = levelDAO.getAllBgObjects();
        //make sure BGObject was added
        assertEquals(1, allObjects.size());
        BGObjectType actual = allObjects.iterator().next();
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getImagePath(),actual.getImagePath());
    }

    public void testAddLevelObject() throws Exception {
        BGObjectType expectedType = new BGObjectType("path/to/image");
        levelDAO.addBGObject(expectedType);
        assertEquals(1,expectedType.getId());

        float scale = (float)1.5;
        BGObject expected = new BGObject(expectedType,100,200,scale);
        levelDAO.addLevelObject(expected, 1);
        Map<BGObject,Integer> returnedObjects = levelDAO.getBGObjectsForLevel(1);

        BGObject actualObject = (BGObject)returnedObjects.keySet().toArray()[0];

        assertEquals(expected.getId(),actualObject.getId());
        assertEquals(expected.getPositionX(),actualObject.getPositionX());
        assertEquals(expected.getScale(),actualObject.getScale());
        assertEquals(expected.getType().getImagePath(),actualObject.getType().getImagePath());
        assertEquals(expected.getType().getHeight(),actualObject.getType().getHeight());
        assertEquals(expected.getType().getWidth(),actualObject.getType().getWidth());
        assertEquals(expected.getType().getId(),actualObject.getType().getId());
    }

    public void testDeleteLevel() throws Exception {

    }

    public void testEmptyLevels() throws Exception {

    }

    @Override
    protected void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(),"test");
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        levelDAO = new LevelDAO(db);
        asteroidsDAO = new AsteroidsDAO(db);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        db.endTransaction();

    }
}