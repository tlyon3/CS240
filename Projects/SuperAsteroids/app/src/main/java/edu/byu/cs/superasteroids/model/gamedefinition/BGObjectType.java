package edu.byu.cs.superasteroids.model.gamedefinition;

/**
 * Created by tlyon on 2/6/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

/** Type class for all objects of type "Background Image"*/
public class BGObjectType extends ImageObject {
    public BGObjectType(int height, int width, String imagepath) {
        super(height, width, imagepath);
    }

    public BGObjectType(JSONObject bgObject) throws JSONException{
        this.imagePath = bgObject.getString("image");
        this.height = -1;
        this.width = -1;
    }
    public BGObjectType(String imagePath,int id) {
        this.height=-1;
        this.width=-1;
        this.imagePath=imagePath;
        this.id = id;
    }
    public BGObjectType(String image){
        this.imagePath = image;
        this.height = -1;
        this.width = -1;
        this.id = -1;
    }
    long id;

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(this.getClass())){
            return false;
        }
        BGObjectType objectType = (BGObjectType)o;
        if(objectType.getImagePath()!=this.imagePath)
            return false;
        else return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Image = "+this.imagePath);
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
