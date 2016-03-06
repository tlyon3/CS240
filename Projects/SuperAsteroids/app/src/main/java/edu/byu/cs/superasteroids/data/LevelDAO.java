package edu.byu.cs.superasteroids.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.byu.cs.superasteroids.game.InputManager;
import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;
import edu.byu.cs.superasteroids.model.runtime.Asteroid;
import edu.byu.cs.superasteroids.model.runtime.BGObject;
import edu.byu.cs.superasteroids.model.runtime.Level;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;

/**
 * Created by tlyon on 2/10/16.
 */
/** Level Data Access Object. Also handles the tables for levelAsteroids and levelObjects*/
public class LevelDAO {
    private SQLiteDatabase db;

    public LevelDAO(SQLiteDatabase db){
        this.db=db;
    }

    /** Gets set of all levels in database
     * @return returns a set of all levels in database*/
    public Set<Level> getLevels(){
        Set<Level> result = new HashSet<Level>();
        Cursor cursor = db.rawQuery(SELECT_ALL_LEVELS,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int number = cursor.getInt(1);
                String title = cursor.getString(2);
                String hint = cursor.getString(3);
                int height = cursor.getInt(4);
                int width = cursor.getInt(5);
                String music = cursor.getString(6);
                Map<BGObject,Integer> bgObjectMap = getBGObjectsForLevel(id);
                Map<AsteroidType,Integer> asteroidMap = getLevelAsteroids(id);
                Level level = new Level(bgObjectMap,asteroidMap,title,hint,height,width,music,
                        number,id);
                result.add(level);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Adds a Level to database
      * @param level Level to be added to database
      * @return Returns true if level was added, false if not*/
    public boolean addLevel(Level level){
        ContentValues values = new ContentValues();
        values.put("title",level.getTitle());
        values.put("hint",level.getHint());
        values.put("height",level.getHeight());
        values.put("width",level.getWidth());
        values.put("music",level.getMusic());
        values.put("number",level.getNumber());

        long id = db.insert("levels", null, values);

        boolean result = false;
        if(id>=0){
            level.setId(id);
            result = true;
        }
        return result;
    }

    /** Adds a background object to the database
     * @param bgObject bgobject to be added
     * @return returns true if bgObject was added, false if not*/
    public boolean addBGObject(BGObjectType bgObject){
        ContentValues values = new ContentValues();
        values.put("image",bgObject.getImagePath());
        long id = db.insert("backgroundObjects",null,values);

        boolean result = false;
        if(id>=0){
            bgObject.setId(id);
            result = true;
        }
        return result;
    }

    /** Adds a backround object and which level it appears on to the database
     * @param bgObject object to be added
     * @param level level where object appears
     * @return returns true if add was successfull, false if not*/
    public boolean addLevelObject(BGObject bgObject,int level){
        ContentValues values = new ContentValues();
        values.put("levelId",level);
        values.put("objectId", bgObject.getType().getId());
        values.put("scale",bgObject.getScale());
        values.put("positionX",bgObject.getPositionX());
        values.put("positionY",bgObject.getPositionY());
        long id = db.insert("levelObjects",null,values);
        boolean result = false;
        if(id>=0){
            bgObject.setId(id);
            result = true;
        }
        return result;
    }

    /** Adds an asteroid to the level in which it appears in the database
     * @param level level which the asteroid type appears
     * @param asteroidType type of asteroid
     * @param number number of the type of asteroid that appears in the level
     * @return returns true if add was successful, false if not*/
    public boolean addLevelAsteroid(AsteroidType asteroidType, int level, int number){
        ContentValues values = new ContentValues();
        values.put("levelId",level);
        values.put("asteroidId",asteroidType.getId());
        values.put("number",number);

        long id = db.insert("levelAsteroids",null,values);
        boolean result = false;
        if(id>=0){
            result = true;
        }
        return result;
    }

    /** Gets the bgObject from the objects table with the id 'id'
     * @param id id of object to get
     * @return returns the bgObject with the id 'id'*/
    public BGObjectType getObjectTypeForId(int id){
        String selectStatement = "SELECT * FROM BACKGROUNDOBJECTS WHERE ID = "+Integer.toString(id);
        Cursor cursor = db.rawQuery(selectStatement,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            String image = cursor.getString(1);
            BGObjectType objectType = new BGObjectType(image,id);
            return objectType;
        }
        finally {
            cursor.close();
        }
    }

    /** Gets the asteroidType from the asteroids table with the id 'id'
     * @param id id of asteroid to get
     * @return returns the asteroidType with the id 'id'*/
    public AsteroidType getAsteroidTypeForId(int id){
        String selectStatement = "SELECT * FROM ASTEROIDS WHERE ID = "+Integer.toString(id);
        Cursor cursor = db.rawQuery(selectStatement,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            long asteroidId = cursor.getInt(0);
            String name = cursor.getString(1);
            String image = cursor.getString(2);
            int imageWidth = cursor.getInt(3);
            int imageHeight = cursor.getInt(4);
            String type = cursor.getString(5);

            AsteroidType asteroidType = new AsteroidType(imageHeight,imageWidth,image,name,type,asteroidId);
            return asteroidType;
        }
        finally {
            cursor.close();
        }
    }

    /** Gets map of background objects for a level
     * @param level The level to get the background objects for.*/
    public Map<BGObject,Integer> getBGObjectsForLevel(int level) {
        Map<BGObject, Integer> result = new HashMap<BGObject, Integer>();
        String selectStatement = "SELECT * from levelObjects WHERE levelId = " + Integer.toString(level);
        Cursor cursor = db.rawQuery(selectStatement,EMPTY_ARRAY_OF_STRINGS);
        Log.d(TAG,"Size of selection: " + level);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int objectId = cursor.getInt(2);
                float scale = cursor.getFloat(3);
                int positionX = cursor.getInt(4);
                int positionY = cursor.getInt(5);

                String statement = "SELECT * FROM BACKGROUNDOBJECTS WHERE ID = " + Integer.toString(objectId);
                Cursor bgobjectTypeCursor = db.rawQuery(statement, EMPTY_ARRAY_OF_STRINGS);
                bgobjectTypeCursor.moveToFirst();
                String image = bgobjectTypeCursor.getString(1);

                BGObjectType bgObjectType = new BGObjectType(-1,-1,image);
                bgObjectType.setId(objectId);
                bgobjectTypeCursor.close();

                BGObject bgObject = new BGObject(bgObjectType,positionX,positionY,scale,objectId);

                result.put(bgObject, 1);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    public Set<BGObjectType> getAllBgObjects(){
        Set<BGObjectType> result = new HashSet<>();
        String selectStatment = "SELECT * FROM BACKGROUNDOBJECTS";
        Cursor cursor = db.rawQuery(selectStatment,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                String image = cursor.getString(1);

                BGObjectType bgObjectType = new BGObjectType(image);
                bgObjectType.setId(cursor.getInt(0));
                result.add(bgObjectType);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    /** Gets map of asteroids for a level
     * @param level level to get asteroids for*/
    public Map<AsteroidType,Integer> getLevelAsteroids(int level){
        Map<AsteroidType,Integer> result = new HashMap<AsteroidType,Integer>();
        String selectStatmeent = "SELECT * FROM LEVELASTEROIDS WHERE LEVELID = " + Integer.toString(level);
        Cursor cursor = db.rawQuery(selectStatmeent,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int asteroidId = cursor.getInt(2);
                int number = cursor.getInt(3);

                String statement = "SELECT * FROM ASTEROIDS WHERE ID = "+Integer.toString(asteroidId);
                Cursor asteroidCursor = db.rawQuery(statement, EMPTY_ARRAY_OF_STRINGS);

                asteroidCursor.moveToFirst();

                int id = asteroidCursor.getInt(0);
                String name = asteroidCursor.getString(1);
                String image = asteroidCursor.getString(2);
                String type = asteroidCursor.getString(3);
                int width = asteroidCursor.getInt(4);
                int height = asteroidCursor.getInt(5);

                AsteroidType asteroidType = new AsteroidType(height,width,image,name,type,id);
                result.put(asteroidType,number);

                asteroidCursor.close();

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Deletes a Level from the database
     * @param level Level to be deleted from database
     * @return Returns true if level was deleted, false if not*/
    public boolean deleteLevel(Level level){
        int rows = db.delete("levels","id = "+level.getId(),null);
        if(rows == 1){
            level.setId(-1);
            return true;
        }
        else return false;
    }

    /** Deletes all levels in database*/
    public void emptyLevels(){
        db.execSQL(DELETE_ALL_ENTRIES_IN_LEVELASTEROIDS);
        db.execSQL(DELETE_ALL_ENTRIES_IN_LEVELOBJECTS);
        db.execSQL(DELETE_ALL_ENTRIES_IN_LEVELS);
        db.execSQL(DELETE_ALL_ENTRIES_IN_BACKGROUNDOBJECTS);
    }

    /** Drops the levelAsteroids, levelObjects, levels, and backgroundObjects tables*/
    public void dropAllTables(){
        db.execSQL(DELETE_LEVELASTEROIDS_TABLE);
        db.execSQL(DELETE_LEVELOBJECTS_TABLE);
        db.execSQL(DELETE_LEVEL_TABLE);
        db.execSQL(DELETE_BACKGROUNDOBJECTS_TABLE);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    private static final String[] EMPTY_ARRAY_OF_STRINGS = {};
    private static final String DELETE_LEVEL_TABLE = "DROP TABLE IF EXISTS LEVELS";
    private static final String DELETE_LEVELASTEROIDS_TABLE = "DROP TABLE IF EXISTS LEVELASTEROIDS";
    private static final String DELETE_LEVELOBJECTS_TABLE = "DROP TABLE IF EXISTS LEVELOBJECTS";
    private static final String DELETE_BACKGROUNDOBJECTS_TABLE = "DROP TABLE IF EXISTS BACKGROUNDOBJECTS";
    private static final String DELETE_ALL_ENTRIES_IN_LEVELOBJECTS = "delete from levelObjects";
    private static final String DELETE_ALL_ENTRIES_IN_LEVELASTEROIDS = "delete from levelAsteroids";
    private static final String DELETE_ALL_ENTRIES_IN_LEVELS = "delete from levels";
    private static final String SELECT_ALL_LEVELS = "select * from levels";
    private static final String SELECT_ALL_LEVELOBJECTS = "select * from levelObjects";
    private static final String DELETE_ALL_ENTRIES_IN_BACKGROUNDOBJECTS = "delete from backgroundObjects";
    private final String TAG = this.getClass().getSimpleName();

    protected Point makePoint(String pointString){
        String[] parts = pointString.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Point result = new Point(x,y);
        return result;
    }
}
