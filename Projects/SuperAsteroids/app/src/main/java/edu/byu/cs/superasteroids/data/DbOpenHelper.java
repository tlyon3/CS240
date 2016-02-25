package edu.byu.cs.superasteroids.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by tlyon on 2/9/16.
 */
/** Class to implement an SQLite DatabaseOpenHelper*/
// Study CatalogDemo application to understand everything.
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "asteroidsTest.sqlite";
    private static final int DB_VERSION = 1;
    public DbOpenHelper(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    /** Create all the tables for the database */
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_LEVELASTEROIDS_TABLE);
        db.execSQL(CREATE_TABLE_ASTEROIDS);
        db.execSQL(CREATE_TABLE_BACKDROUNDOBJECTS);
        db.execSQL(CREATE_TABLE_ENGINES);
        db.execSQL(CREATE_TABLE_EXTRAPARTS);
        db.execSQL(CREATE_TABLE_LEVELS);
        db.execSQL(CREATE_TABLE_LEVELOBJECTS);
        db.execSQL(CREATE_TABLE_MAINBODIES);
        db.execSQL(CREATE_TABLE_CANNONS);
        db.execSQL(CREATE_TABLE_POWERCORES);

    }

    /** Creates all tables
     * @param db database in which to create tables*/
    public void createAllTables(SQLiteDatabase db){
        db.execSQL(CREATE_LEVELASTEROIDS_TABLE);
        db.execSQL(CREATE_TABLE_ASTEROIDS);
        db.execSQL(CREATE_TABLE_BACKDROUNDOBJECTS);
        db.execSQL(CREATE_TABLE_ENGINES);
        db.execSQL(CREATE_TABLE_EXTRAPARTS);
        db.execSQL(CREATE_TABLE_LEVELS);
        db.execSQL(CREATE_TABLE_LEVELOBJECTS);
        db.execSQL(CREATE_TABLE_MAINBODIES);
        db.execSQL(CREATE_TABLE_CANNONS);
        db.execSQL(CREATE_TABLE_POWERCORES);
    }

    /** Drops all tables
     * @param db database in which to drop all tables*/
    private void deleteAllTables(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS LEVELASTEROIDS");
        db.execSQL("DROP TABLE IF EXISTS ASTEROIDS");
        db.execSQL("DROP TABLE IF EXISTS BACKGROUNDOBJECTS");
        db.execSQL("DROP TABLE IF EXISTS ENGINES");
        db.execSQL("DROP TABLE IF EXISTS LEVELS");
        db.execSQL("DROP TABLE IF EXISTS LEVELOBJECTS");
        db.execSQL("DROP TABLE IF EXISTS MAINBODIES");
        db.execSQL("DROP TABLE IF EXISTS CANNONS");
        db.execSQL("DROP TABLE IF EXISTS POWERCORES");
    }

    /** Does nothing. Implemented so the DbOpenHelper can extend SQLiteOpenHelper*/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        return;
    }

    private static final String CREATE_TABLE_LEVELS =
            "create table levels (\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  number integer not null,\n" +
            "  title varchar(255) not null,\n" +
            "  hint varchar(255) not null,\n" +
            "  height integer not null,\n" +
            "  width integer not null,\n" +
            "  music varchar(255) not null\n" +
            ");\n";
    private static final String CREATE_LEVELASTEROIDS_TABLE =
            "create table levelAsteroids(\n" +
            "  entryid integer not null primary key autoincrement,\n"+
            "  levelId integer not null,\n" +
            "  asteroidId integer not null,\n" +
            "  number integer not null\n" +
            ");";
    private static final String CREATE_TABLE_ASTEROIDS =
            "create table asteroids(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  name varchar(255) not null,\n" +
            "  image varchar(255) not null,\n" +
            "  type varchar(255) not null,\n" +
            "  imageWidth integer not null,\n" +
            "  imageHeight integer not null\n" +
            ");";
    private static final String CREATE_TABLE_BACKDROUNDOBJECTS =
            "create table backgroundObjects(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  image varchar(255) not null\n" +
            ");";
    private static final String CREATE_TABLE_LEVELOBJECTS =
            "create table levelObjects(\n" +
            "  id integer not null primary key,\n" +
            "  levelId integer not null,\n" +
            "  objectId integer not null,\n" +
            "  scale float not null,\n" +
            "  positionX integer not null,\n" +
            "  positionY integer not null\n" +
            ");";
    private static final String CREATE_TABLE_MAINBODIES =
            "create table mainBodies(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  attachCannonX integer not null,\n" +
            "  attachCannonY integer not null,\n" +
            "  attachExtraX integer not null,\n" +
            "  attachExtraY integer not null,\n" +
            "  attachEngineX integer not null,\n" +
            "  attachEngineY integer not null,\n" +
            "  image varchar(255) not null,\n" +
            "  imageHeight integer not null,\n" +
            "  imageWidth integer not null\n" +
            ");";
    private static final String CREATE_TABLE_CANNONS =
            "create table cannons(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  attachX integer not null,\n" +
            "  attachY integer not null,\n" +
            "  emitX integer not null,\n" +
            "  emitY integer not null,\n" +
            "  image varchar(255) not null,\n" +
            "  imageWidth integer not null,\n" +
            "  imageHeight integer not null,\n" +
            "  attackImage varchar(255) not null,\n" +
            "  attackImageWidth integer not null,\n" +
            "  attackImageHeight integer not null,\n" +
            "  attackSound varchar(255) not null,\n" +
            "  damage integer not null\n" +
            ");";
    private static final String CREATE_TABLE_EXTRAPARTS =
            "create table extraParts\n" +
            "(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  image varchar(255) not null,\n" +
            "  attachX integer not null,\n" +
            "  attachY integer not null,\n" +
            "  imageHeight integer,\n" +
            "  imageWidth integer\n" +
            ");";
    private static final String CREATE_TABLE_ENGINES =
            "create table engines(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  image varchar(255) not null,\n" +
            "  speed integer not null,\n" +
            "  turningSpeed integer not null,\n" +
            "  imageHeight integer not null,\n" +
            "  imageWidth integer not null,\n" +
            "  attachX integer not null,\n" +
            "  attachY integer not null\n" +
            ");\n";
    private static final String CREATE_TABLE_POWERCORES =
            "create table powerCores\n" +
            "(\n" +
            "  id integer not null primary key autoincrement,\n" +
            "  image varchar(255) not null,\n" +
            "  cannonBoost integer not null,\n" +
            "  engineBoost integer not null\n" +
            ");";
}
