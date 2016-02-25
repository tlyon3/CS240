package edu.byu.cs.woodfiel.catalogdemo.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Scott on 2/2/2016.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cd.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CD_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    public static final String CREATE_CD_TABLE =
            "CREATE TABLE CD " +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   title TEXT NOT NULL," +
                    "   artist TEXT NOT NULL," +
                    "   country TEXT NOT NULL," +
                    "   company TEXT NOT NULL," +
                    "   price REAL NOT NULL," +
                    "   year INTEGER NOT NULL" +
                    ")";
}
