package edu.byu.cs.woodfiel.catalogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;

import edu.byu.cs.woodfiel.catalogdemo.database.Database;
import edu.byu.cs.woodfiel.catalogdemo.model.Catalog;

public class MainActivity extends AppCompatActivity {
    private Database database;
    private Catalog catalog;

    //GUI Components
    private Button mEmptyButton;
    private Button mLoadButton;
    private Button mPopulateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //GUI Component setup.
    //If you read chapter 1 of the book you should be able to understand this section,
    //res.layout.activity_main.xml, and the strings in res.values.strings.xml
        mEmptyButton = (Button) findViewById(R.id.empty_button);
        mLoadButton = (Button) findViewById(R.id.load_button);
        mPopulateButton = (Button) findViewById(R.id.populate_button);

        mEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getCD_DAO().emptyCDs();
            }
        });

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    database.importData(JSON_FILE_NAME);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mPopulateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalog catalog = new Catalog(database);
            }
        });

        database = new Database(getBaseContext());
    }

    private static final String JSON_FILE_NAME = "cd_catalog.json";
}
