package edu.byu.cs.woodfiel.catalogdemo.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.woodfiel.catalogdemo.model.CD;

/**
 * Created by Scott Woodfield on 2/2/2016.
 */
public class Database {
    private DbOpenHelper dbOpenHelper;
    private SQLiteDatabase database;
    private Context baseContext;

    public CD_DAO cdDAO;

    //Constructor
    public Database(Context baseContext) {
        this.baseContext = baseContext;
        dbOpenHelper = new DbOpenHelper(baseContext);
        database = dbOpenHelper.getWritableDatabase();
        cdDAO = new CD_DAO(database);
    }
    //Queries
    public CD_DAO getCD_DAO() {
        return cdDAO;
    }

    public void open(String fileName) {

    }

    //Commands
    //See if you can follow the traversal of our JSON tree.
    //The root of our tree is a JSONObject with a single member named "CATALOG".
    //The CATALOG object is a JSONArray
    //Each element in the CATALOG array is JSONObject with a single member named "CD"
    //The CD Object is a JSONObject with keys: id, title, artist, country, company, price, and year
    //along with their corresponding atomic values represented as strings.
    public void importData(String jsonFileName)
        throws org.json.JSONException, IOException
    {
        AssetManager manager = baseContext.getAssets();
        InputStream input = manager.open(jsonFileName);
        Reader reader = new InputStreamReader(input);

        JSONObject rootObject = new JSONObject(makeString(reader));
        JSONArray cdArr = null;
        try {
            cdArr = rootObject.getJSONArray("CATALOG");
            for (int i = 0; i < cdArr.length(); ++i) {
                JSONObject elemObj = cdArr.getJSONObject(i);
                JSONObject cdObj = elemObj.getJSONObject("CD");
                CD cd = new CD(cdObj);
                cdDAO.addCD(cd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

    private static final String DB_FILE_NAME = "cd_catalog.json";
}
