package edu.byu.cs.woodfiel.catalogdemo.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.woodfiel.catalogdemo.model.CD;

/**
 * The CD data access object for the database
 *
 * Created by Scott Woodfield on 2/2/2016.
 */
public class CD_DAO {

    private SQLiteDatabase db;

    //We use the database in all of our queries.  We could have passed it in whenever someone
    //called a method.  But, we chose to initialize the local variable when create a CD_DAO
    //and then reference the local database.

    public CD_DAO(SQLiteDatabase db) {
        this.db = db;
    }

    //We designed this method to make it easier to load the database into a catalog.
    public Set<CD> getCDs() {
        Set<CD> result = new HashSet<>();

        //The rawQuery command assumes that normally the sql statement will have '?' in it.
        //It thus expect an array of values to fill the '?'.  However, if the sql statement
        //does not have any '?' in it.  You must still pass an empty array of strings.
        Cursor cursor = db.rawQuery(SELECT_ALL_CD_INFORMATION, EMPTY_ARRAY_OF_STRINGS);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String artist = cursor.getString(2);
                String country = cursor.getString(3);
                String company = cursor.getString(4);
                float price = cursor.getFloat(5);
                int year = cursor.getInt(6);

                CD cd = new CD(id, title, artist, country, company, price, year);
                result.add(cd);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    //Not used in our demo but generally useful.
    public boolean addCD(CD cd) {
        ContentValues values = new ContentValues();
        values.put("title", cd.getTitle());
        values.put("artist", cd.getArtist());
        values.put("country", cd.getCountry());
        values.put("company", cd.getCompany());
        values.put("price", cd.getPrice());
        values.put("year", cd.getYear());

        long id = db.insert("CD", null, values);

        boolean result = false;
        if (id >= 0) {
            cd.setId(id);
            result = true;
        }

        return result;
    }

    //Not used in our demo but generally useful.
    public boolean updateCD(CD cd) {
        ContentValues values = new ContentValues();
        values.put("title", cd.getTitle());
        values.put("artist", cd.getArtist());
        values.put("country", cd.getCountry());
        values.put("company", cd.getCompany());
        values.put("price", cd.getPrice());
        values.put("year", cd.getYear());

        int rows = db.update("CD", values, "id = " + cd.getId(), null);

        return (rows == 1);
    }

    //Not used in our demo but generally useful.
    public boolean deleteCD(CD cd) {
        int rows = db.delete("CD", "id = " + cd.getId(), null);
        if (rows == 1) {
            cd.setId(-1);
            return true;
        }
        else {
            return false;
        }
    }

    public void emptyCDs() {
        db.execSQL(DELETE_ALL_TUPLES_FROM_CD_TABLE);

        /* The following is alternative way of emptying a table.  It is really here to show you the
           statement "DROP TABLE IF EXISTS table_name" , the reuse of the create table string,
           and the use of transactions.
        db.beginTransaction();
            db.execSQL(DELETE_CD_TABLE_IF_EXISTS);
            db.execSQL(DbOpenHelper.CREATE_CD_TABLE);
        db.setTransactionSuccessful();
        db.endTransaction();
        */
    }


    private static final String[] EMPTY_ARRAY_OF_STRINGS = {};
    private static final String SELECT_ALL_CD_INFORMATION = "select * from CD";
    private static final String DELETE_CD_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS CD";
    private static final String DELETE_ALL_TUPLES_FROM_CD_TABLE = "DELETE FROM CD";
}

