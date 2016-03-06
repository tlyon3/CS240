package edu.byu.cs.superasteroids.model.runtime.shipparts;


/**
 * Created by tlyon on 2/6/16.
 */
/** @author Trevor Lyon*/
/** @version 1.0*/

import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.CharArrayReader;


/** Class for ShipPart objects of type cannon */
public class Cannon extends ShipPart {
    public Cannon(){

    }
    public Cannon(JSONObject cannon) throws JSONException{
        String aPoint = cannon.getString("attachPoint");
        Point attachPoint = makePoint(aPoint);
        this.attachPoint = attachPoint;
        String ePoint = cannon.getString("emitPoint");
        Point emitPoint = makePoint(ePoint);
        this.emitPoint = emitPoint;
        this.imagePath = cannon.getString("image");
        this.width = cannon.getInt("imageWidth");
        this.height = cannon.getInt("imageHeight");
        this.attackImage = cannon.getString("attackImage");
        this.attackImageHeight = cannon.getInt("attackImageHeight");
        this.attackImageWidth = cannon.getInt("attackImageWidth");
        this.attackSound = cannon.getString("attackSound");
        this.damage = cannon.getInt("damage");
    }
    public Cannon(int imageHeight, int imageWidth, String imagePath, long id, Point attachPoint, Point emitPoint,
                  String attackImage, int attackImageWidth, int attackImageHeight, String attackSound, int damage) {
        super(imageHeight, imageWidth, imagePath, id, attachPoint);
        this.emitPoint = emitPoint;
        this.attackImage = attackImage;
        this.attackImageWidth = attackImageWidth;
        this.attackImageHeight = attackImageHeight;
        this.attackSound = attackSound;
        this.damage = damage;
    }

    public Cannon(int emitX,int emitY){
        emitPoint = new Point(emitX,emitY);
    }
    /** Point where projectile is emitted*/
    private Point emitPoint;
    /** Path to image file for projectile*/
    private String attackImage;
    /** Width of projectile image */
    private int attackImageWidth;
    /** Height of projectile image */
    private int attackImageHeight;
    /** Path to cannon sound on fire */
    private String attackSound;
    /** Damage of each projectile */
    private int damage;

    private int projectileImageId;

    public Point getEmitPoint() {
        return emitPoint;
    }

    public String getAttackImage() {
        return attackImage;
    }

    public int getAttackImageWidth() {
        return attackImageWidth;
    }

    public int getAttackImageHeight() {
        return attackImageHeight;
    }

    public String getAttackSound() {
        return attackSound;
    }

    public int getDamage() {
        return damage;
    }

    public void setEmitPoint(Point emitPoint) {
        this.emitPoint = emitPoint;
    }

    public void setAttackImage(String attackImage) {
        this.attackImage = attackImage;
    }

    public void setAttackImageWidth(int attackImageWidth) {
        this.attackImageWidth = attackImageWidth;
    }

    public void setAttackImageHeight(int attackImageHeight) {
        this.attackImageHeight = attackImageHeight;
    }

    public void setAttackSound(String attackSound) {
        this.attackSound = attackSound;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean equals(Object o) {
        if(!super.equals(o))
            return false;
        Cannon cannon = (Cannon)o;
        if(cannon.getEmitPoint()!=this.emitPoint)
            return false;
        else if(cannon.getAttackImage()!=this.attackImage)
            return false;
        else if(cannon.getAttackImageHeight()!=this.attackImageHeight)
            return false;
        else if(cannon.getAttackImageWidth()!=this.attackImageWidth)
            return false;
        else if(cannon.getAttackSound()!=this.attackSound)
            return false;
        else if(cannon.getDamage()!=this.damage)
            return false;
        else return true;
    }

    public int getProjectileImageId() {
        return projectileImageId;
    }

    public void setProjectileImageId(int projectileImageId) {
        this.projectileImageId = projectileImageId;
    }

}
