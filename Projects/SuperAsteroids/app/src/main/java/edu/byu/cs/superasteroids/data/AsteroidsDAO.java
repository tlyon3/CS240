package edu.byu.cs.superasteroids.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.runtime.Asteroid;

/**
 * Created by tlyon on 2/9/16.
 */
/** Data Access Object for objects of the Asteroid type*/
// Study CatalogDemo application to understand everything
public class AsteroidsDAO {

    private SQLiteDatabase db;

    public AsteroidsDAO(SQLiteDatabase sqldb){
        this.db = sqldb;
    }

    /** Gets the set of all asteroids in the database
     * @return returns a set of all asteroids in the database */
    public Set<AsteroidType> getAsteroids(){
        Set<AsteroidType> result = new HashSet<AsteroidType>();
        Cursor cursor = db.rawQuery(SELECT_ALL_ASTEROID,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image = cursor.getString(2);
                String type = cursor.getString(3);
                int imageWidth = cursor.getInt(4);
                int imageHeight = cursor.getInt(5);

                AsteroidType newAteroidType = new AsteroidType(imageHeight, imageWidth, image,
                        name, type);
                newAteroidType.setId(id);
                result.add(newAteroidType);

                cursor.moveToNext();
            }

        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Adds a new asteroid to the database
     * @return returns true if asteroid was added, false if not
     * @param asteroid asteroid to be added to the database */
    public boolean addAsteroid(AsteroidType asteroid){
        ContentValues values = new ContentValues();
        values.put("name",asteroid.getName());
        values.put("image",asteroid.getImagePath());
        values.put("imageWidth",asteroid.getWidth());
        values.put("imageHeight",asteroid.getHeight());
        values.put("type", asteroid.getType());
        long id = db.insert("asteroids",null,values);
        boolean result = false;
        if(id>=0){
            asteroid.setId(id);
            result = true;
        }
        return result;
    }

    /** Removes an asteroid from the database
     * @param asteroid The asteroid to be removed
     * @return Returns true if asteroid was removed, false if was not removed */
    public boolean deleteAsteroid(AsteroidType asteroid){
        int rows = db.delete("asteroids","id = " + asteroid.getId(),null);
        if(rows==1){
            asteroid.setId(-1);
            return true;
        }
        else return false;
    }

    /** Deletes all asteroids from database */
    public void emptyAsteroids(){
        db.execSQL(DELETE_ALL_ENTRIES_IN_ASTEROIDS);
    }

    /** Drops the asteroids table*/
    public void dropTable(){
        db.execSQL(DELETE_ASTEROIDS_TABLE);
    }

    private static final String[] EMPTY_ARRAY_OF_STRINGS = {};
    private static final String SELECT_ALL_ASTEROID = "select * from ASTEROIDS";
    private static final String DELETE_ASTEROIDS_TABLE = "DROP TABLE IF EXISTS ASTEROIDS";
    private static final String DELETE_ALL_ENTRIES_IN_ASTEROIDS = "delete from asteroids";
}
