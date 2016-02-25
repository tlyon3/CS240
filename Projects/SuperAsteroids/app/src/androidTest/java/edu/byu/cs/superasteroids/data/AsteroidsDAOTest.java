package edu.byu.cs.superasteroids.data;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.util.Set;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;


/**
 * Created by tlyon on 2/22/16.
 */
public class AsteroidsDAOTest extends AndroidTestCase {

    AsteroidsDAO asteroidsDAO;
    SQLiteDatabase db;

    public void testAddAsteroid() throws Exception {
        AsteroidType expected = new AsteroidType(12,10,"path/to/image","name","type");
        asteroidsDAO.addAsteroid(expected);
        assertEquals(1, expected.getId());

        Set<AsteroidType> asteroidTypes = asteroidsDAO.getAsteroids();
        AsteroidType actual = asteroidTypes.iterator().next();

        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getImagePath(),actual.getImagePath());
        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getType(),actual.getType());
    }

    public void testDeleteAsteroid() throws Exception {

    }

    @Override
    protected void setUp() throws Exception {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(),"test");
        DbOpenHelper dbOpenHelper = new DbOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        asteroidsDAO = new AsteroidsDAO(db);
    }

    @Override
    protected void tearDown() throws Exception {
        db.endTransaction();
    }
}