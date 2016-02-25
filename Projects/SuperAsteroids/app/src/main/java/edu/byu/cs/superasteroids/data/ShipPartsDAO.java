package edu.byu.cs.superasteroids.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.superasteroids.model.runtime.shipparts.Cannon;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Engine;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ExtraPart;
import edu.byu.cs.superasteroids.model.runtime.shipparts.MainBody;
import edu.byu.cs.superasteroids.model.runtime.shipparts.Point;
import edu.byu.cs.superasteroids.model.runtime.shipparts.PowerCore;
import edu.byu.cs.superasteroids.model.runtime.shipparts.ShipPart;

/**
 * Created by tlyon on 2/10/16.
 */
/** DAO for ShipPart objects*/
public class ShipPartsDAO {
    private SQLiteDatabase db;
    public ShipPartsDAO(SQLiteDatabase db){
        this.db = db;
    }

    /** Gets all Cannons in database
     * @return returns a set of Cannons in database*/
    public Set<Cannon> getCannonSet(){
        Set<Cannon> result = new HashSet<Cannon>();
        Cursor cursor = db.rawQuery(SELECT_ALL_CANNONS,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int attachX = cursor.getInt(1);
                int attachY = cursor.getInt(2);
                int emitX = cursor.getInt(3);
                int emitY = cursor.getInt(4);
                String pathToImage = cursor.getString(5);
                int imageWidth = cursor.getInt(6);
                int imageHeight = cursor.getInt(7);
                String attackImage = cursor.getString(8);
                int attackImageWidth = cursor.getInt(9);
                int attackImageHeight = cursor.getInt(10);
                String attackSound = cursor.getString(11);
                int damage = cursor.getInt(12);
                Point emitPoint = new Point(emitX,emitY);
                Point attachPoint = new Point(attachX,attachY);
                Cannon cannon  = new Cannon(imageHeight,imageWidth,pathToImage,id,attachPoint,
                        emitPoint,attackImage,attackImageWidth,attackImageHeight,attackSound,damage);
                result.add(cannon);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Gets all MainBody in database
     * @return returns a set of MainBody in database*/
    public Set<MainBody> getMainBodySet(){
        Set<MainBody> result = new HashSet<MainBody>();
        Cursor cursor = db.rawQuery(SELECT_ALL_MAINBODIES,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int id = cursor.getInt(0);
                int attachCannonX = cursor.getInt(1);
                int attachCannonY = cursor.getInt(2);
                int attachExtraX = cursor.getInt(3);
                int attachExtraY = cursor.getInt(4);
                int attachEngineX = cursor.getInt(5);
                int attachEngineY = cursor.getInt(6);
                String imagePath = cursor.getString(7);
                int imageHeight = cursor.getInt(8);
                int imageWidth = cursor.getInt(9);
                MainBody mainBody = new MainBody(attachCannonX,attachCannonY,attachEngineX,
                        attachEngineY,attachExtraX,attachExtraY,imagePath,imageHeight,imageWidth);
                result.add(mainBody);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Gets all ExtraParts in database
     * @return returns a Set of ExtraPart in database*/
    public Set<ExtraPart> getExtraPartsSet(){
        Set<ExtraPart> result = new HashSet<ExtraPart>();
        Cursor cursor =db.rawQuery(SELECT_ALL_EXTRAPARTS,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String image = cursor.getString(1);
                int attachX = cursor.getInt(2);
                int attachY = cursor.getInt(3);
                int imageHeight = cursor.getInt(4);
                int imageWidth = cursor.getInt(5);
                ExtraPart extraPart = new ExtraPart(attachX, attachY, image, imageHeight, imageWidth);
                result.add(extraPart);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Gets all Engines in database
     * @return returns a set of Engine in database */
    public Set<Engine> getEngineSet(){
        Set<Engine> result = new HashSet<Engine>();
        Cursor cursor = db.rawQuery(SELECT_ALL_ENGINES,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String image = cursor.getString(1);
                int speed = cursor.getInt(2);
                int turningSpeed = cursor.getInt(3);
                int imageHeight = cursor.getInt(4);
                int imageWidth = cursor.getInt(5);
                int attachX = cursor.getInt(6);
                int attachY = cursor.getInt(7);
                Engine engine = new Engine(speed, turningSpeed, attachX, attachY, image, imageHeight,
                        imageWidth);
                result.add(engine);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Gets all PowerCore in database
     * @return returns a set of PowerCore in database*/
    public Set<PowerCore> getPowerCoreSet(){
        Set<PowerCore> result = new HashSet<PowerCore>();
        Cursor cursor = db.rawQuery(SELECT_ALL_POWERCORES,EMPTY_ARRAY_OF_STRINGS);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String image = cursor.getString(1);
                int cannonBoost = cursor.getInt(2);
                int engineBoost = cursor.getInt(3);
                PowerCore powerCore = new PowerCore(cannonBoost, engineBoost, image);
                result.add(powerCore);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    /** Adds a ShipPart to the database
     * @param shipPart part to be added to database
     * @return Returns true if ShipPart was added, false if not*/
    public boolean addShipPart(ShipPart shipPart){
        ContentValues values = new ContentValues();
        Cannon c = new Cannon();
        Engine e = new Engine();
        ExtraPart ep = new ExtraPart();
        PowerCore pc = new PowerCore();
        MainBody mb = new MainBody();
        if(shipPart.getClass().equals(c.getClass())){
            c = (Cannon)shipPart;
            values.put("attachX",c.getAttachPoint().getX());
            values.put("attachY",c.getAttachPoint().getY());
            values.put("emitX",c.getEmitPoint().getX());
            values.put("emitY",c.getEmitPoint().getY());
            values.put("image",c.getImagePath());
            values.put("imageWidth",c.getWidth());
            values.put("imageHeight",c.getHeight());
            values.put("attackImage",c.getAttackImage());
            values.put("attackImageWidth",c.getAttackImageWidth());
            values.put("attackImageHeight",c.getAttackImageHeight());
            values.put("attackSound",c.getAttackSound());
            values.put("damage",c.getDamage());
            long id  = db.insert("cannons", null, values);
            boolean result = false;
            if(id>=0){
                c.setId(id);
                result = true;
            }
            return result;
        }
        //add Engine
        else if(shipPart.getClass().equals(e.getClass())){
            e = (Engine)shipPart;
            values.put("image",e.getImagePath());
            values.put("attachX",e.getAttachPoint().getX());
            values.put("attachY",e.getAttachPoint().getY());
            values.put("speed",e.getBaseSpeed());
            values.put("turningSpeed",e.getBaseTurnRate());
            values.put("imageHeight",e.getHeight());
            values.put("imageWidth",e.getWidth());
            long id = db.insert("engines",null,values);
            boolean result = false;
            if(id>=0){
                e.setId(id);
                result = true;
            }
            return result;
        }
        //add ExtraPart
        else if(shipPart.getClass().equals(ep.getClass())){
            ep = (ExtraPart)shipPart;
            values.put("image",ep.getImagePath());
            values.put("attachX",ep.getAttachPoint().getX());
            values.put("attachY",ep.getAttachPoint().getY());
            values.put("imageHeight",ep.getHeight());
            values.put("imageWidth",ep.getWidth());
            long id = db.insert("extraParts",null,values);
            boolean result = false;
            if(id>=0){
                ep.setId(id);
                result = true;
            }
            return result;
        }
        //add powercore
        else if(shipPart.getClass().equals(pc.getClass())){
            pc = (PowerCore)shipPart;
            values.put("image",pc.getImagePath());
            values.put("cannonBoost",pc.getCannonBoost());
            values.put("engineBoost",pc.getEngineBoost());
            long id = db.insert("powerCores",null,values);
            boolean result = false;
            if(id>=0){
                pc.setId(id);
                result = true;
            }
            return result;
        }
        //add mainbody
        else if(shipPart.getClass().equals(mb.getClass())){
            mb = (MainBody)shipPart;
            values.put("attachCannonX",mb.getCannonAttach().getX());
            values.put("attachCannonY",mb.getCannonAttach().getY());
            values.put("attachExtraX",mb.getExtraAttach().getX());
            values.put("attachExtraY",mb.getExtraAttach().getY());
            values.put("attachEngineX",mb.getEngineAttach().getX());
            values.put("attachEngineY",mb.getEngineAttach().getY());
            values.put("image",mb.getImagePath());
            values.put("imageHeight",mb.getHeight());
            values.put("imageWidth",mb.getWidth());
            long id = db.insert("mainBodies",null,values);
            boolean result =false;
            if(id>=0){
                mb.setId(id);
                result = true;
            }
            return result;
        }
        return false;
    }

    /** Deletes ShipPart from database
     * @param shipPart part to be deleted from database
     * @return returns true if ShipPart was deleted, false if not*/
    public boolean deleteShipPart(ShipPart shipPart) {
        Cannon c = new Cannon();
        Engine e = new Engine();
        ExtraPart ep = new ExtraPart();
        PowerCore pc = new PowerCore();
        MainBody mb = new MainBody();
        if (shipPart.getClass().equals(c.getClass())) {
            int rows = db.delete("CANNONS", "id = " + shipPart.getId(), null);
            if (rows == 1) {
                shipPart.setId(-1);
                return true;
            } else {
                return false;
            }
        } else if (shipPart.getClass().equals(e.getClass())) {
            int rows = db.delete("ENGINES", "id = " + shipPart.getId(), null);
            if (rows == 1) {
                shipPart.setId(-1);
                return true;
            } else {
                return false;
            }
        } else if (shipPart.getClass().equals(ep.getClass())) {
            int rows = db.delete("EXTRAPARTS", "id = " + shipPart.getId(), null);
            if (rows == 1) {
                shipPart.setId(-1);
                return true;
            } else {
                return false;
            }
        } else if (shipPart.getClass().equals(pc.getClass())) {
            int rows = db.delete("POWERCORES", "id = " + shipPart.getId(), null);
            if (rows == 1) {
                shipPart.setId(-1);
                return true;
            } else {
                return false;
            }
        } else if (shipPart.getClass().equals(mb.getClass())) {
            int rows = db.delete("MAINBODIES", "id = " + shipPart.getId(), null);
            if (rows == 1) {
                shipPart.setId(-1);
                return true;
            } else {
                return false;
            }
        }
        else return false;
    }
    
    /** Deletes all ShipParts from database*/
    public void emptyShipParts(){
        db.execSQL(DELETE_ALL_ENTRIES_IN_CANNONS);
        db.execSQL(DELETE_ALL_ENTRIES_IN_ENGINES);
        db.execSQL(DELETE_ALL_ENTRIES_IN_EXTRAPARTS);
        db.execSQL(DELETE_ALL_ENTRIES_IN_MAINBODIES);;
        db.execSQL(DELETE_ALL_ENTRIES_IN_POWERCORES);

    }

    /** Drops the engines, powerCores, mainBodies, extraParts and cannons tables*/
    public void dropAllTables(){
        db.execSQL(DELETE_ENGINES_TABLE);
        db.execSQL(DELETE_POWERCORES_TABLE);
        db.execSQL(DELETE_MAINBODIES_TABLE);
        db.execSQL(DELETE_EXTRAPARTS_TABLE);
        db.execSQL(DELETE_CANNONS_TABLE);
    }

    private static final String[] EMPTY_ARRAY_OF_STRINGS = {};
    private static final String SELECT_ALL_MAINBODIES = "select * from mainBodies";
    private static final String SELECT_ALL_CANNONS = "select * from cannons";
    private static final String SELECT_ALL_EXTRAPARTS = "select * from extraParts";
    private static final String SELECT_ALL_ENGINES = "select * from engines";
    private static final String SELECT_ALL_POWERCORES = "select * from powerCores";
    private static final String DELETE_MAINBODIES_TABLE = "DROP TABLE IF EXISTS MAINBODIES";
    private static final String DELETE_CANNONS_TABLE = "DROP TABLE IF EXISTS CANNONS";
    private static final String DELETE_EXTRAPARTS_TABLE = "DROP TABLE IF EXISTS EXTRAPARTS";
    private static final String DELETE_ENGINES_TABLE = "DROP TABLE IF EXISTS ENGINES";
    private static final String DELETE_POWERCORES_TABLE = "DROP TABLE IF EXISTS POWERCORES";
    private static final String DELETE_ALL_ENTRIES_IN_ENGINES = "delete from engines";
    private static final String DELETE_ALL_ENTRIES_IN_POWERCORES = "delete from powerCores";
    private static final String DELETE_ALL_ENTRIES_IN_MAINBODIES = "delete from mainBodies";
    private static final String DELETE_ALL_ENTRIES_IN_CANNONS = "delete from cannons";
    private static final String DELETE_ALL_ENTRIES_IN_EXTRAPARTS = "delete from extraParts";
}
