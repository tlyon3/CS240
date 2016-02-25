package edu.byu.cs.superasteroids.model.gamedefinition;

/**
 * Created by tlyon on 2/6/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

/** Class for all objects of type Asteroid*/
public class AsteroidType extends ImageObject{
    public AsteroidType(){
        name="";
        type="";
    }

    public AsteroidType(String name,String type){
        this.name=name;
        this.type=type;
    }

    public AsteroidType(int h, int w, String ip, String name, String type) {
        super(h, w, ip);
        this.name = name;
        this.type = type;
    }

    public AsteroidType(int h, int w, String ip, String name, String type, long id) {
        super(h, w, ip);
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public AsteroidType(JSONObject asteroidObject) throws JSONException{
        this.imagePath = asteroidObject.getString("image");
        this.width = asteroidObject.getInt("imageWidth");
        this.height = asteroidObject.getInt("imageHeight");
        this.type = asteroidObject.getString("type");
        this.name = asteroidObject.getString("name");
    }

    public AsteroidType(JSONObject asteroidObject, int id) throws JSONException{
        this.imagePath = asteroidObject.getString("image");
        this.width = asteroidObject.getInt("imageWidth");
        this.height = asteroidObject.getInt("imageHeight");
        this.type = asteroidObject.getString("type");
        this.name = asteroidObject.getString("name");
        this.setId(id);
    }

    private String name;
    private String type;
    private long id;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
