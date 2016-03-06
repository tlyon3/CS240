package edu.byu.cs.superasteroids.model.runtime;

/**
 * Created by tlyon on 2/10/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import edu.byu.cs.superasteroids.model.gamedefinition.AsteroidType;
import edu.byu.cs.superasteroids.model.gamedefinition.BGObjectType;

/** Object to store all data for a level*/
public class Level {
    public Level(Map<BGObject, Integer> levelBackgroundObjectsMap,
                 Map<AsteroidType, Integer> levelAsteroidsMap,
                 String title, String hint, int height, int width, String music,int number) {
        this.levelBackgroundObjectsMap = levelBackgroundObjectsMap;
        this.levelAsteroidsMap = levelAsteroidsMap;
        this.title = title;
        this.hint = hint;
        this.height = height;
        this.width = width;
        this.music = music;
        this.number=number;
    }

    public Level(Map<BGObject, Integer> levelBackgroundObjectsMap,
                 Map<AsteroidType, Integer> levelAsteroidsMap, String title, String hint,
                 int height, int width, String music, int number, long id) {
        this.levelBackgroundObjectsMap = levelBackgroundObjectsMap;
        this.levelAsteroidsMap = levelAsteroidsMap;
        this.title = title;
        this.hint = hint;
        this.height = height;
        this.width = width;
        this.music = music;
        this.number = number;
        this.id = id;
    }

    public Level(JSONObject levelObject) throws JSONException{
        this.number = levelObject.getInt("number");
        this.title = levelObject.getString("title");
        this.hint = levelObject.getString("hint");
        this.width = levelObject.getInt("width");
        this.height = levelObject.getInt("height");
        this.music = levelObject.getString("music");
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Level() {
        this.title = null;
        this.hint = null;
        this.height = 0;
        this.width = 0;

        this.music = null;
        levelAsteroidsMap=null;
        levelBackgroundObjectsMap=null;
    }

    /** Map from a background image to the number of each image on the level*/
    private Map<BGObject,Integer> levelBackgroundObjectsMap;
    /** Map from an AsteroidType to the number of each asteroids of that particular type in the level*/
    private Map<AsteroidType,Integer> levelAsteroidsMap;
    /** Title of level*/
    private String title;
    /** Hint of level*/
    private String hint;
    /** Height of level*/
    private int height;
    /** Width of level*/
    private int width;
    /** Path to music file*/
    private String music;
    private int number;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<BGObject, Integer> getLevelBackgroundObjectsMap() {
        return levelBackgroundObjectsMap;
    }

    public Map<AsteroidType, Integer> getLevelAsteroidsMap() {
        return levelAsteroidsMap;
    }

    public String getTitle() {
        return title;
    }

    public String getHint() {
        return hint;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getMusic() {
        return music;
    }

    public void setLevelBackgroundObjectsMap(Map<BGObject, Integer> levelBackgroundObjectsMap) {
        this.levelBackgroundObjectsMap = levelBackgroundObjectsMap;
    }

    public void setLevelAsteroidsMap(Map<AsteroidType, Integer> levelAsteroidsMap) {
        this.levelAsteroidsMap = levelAsteroidsMap;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMusic(String music) {
        this.music = music;
    }
}
