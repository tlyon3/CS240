package edu.tlyon.familymap.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;

/**
 * Activity where filters can be turned on/off. Can use by event type (baptism, birth, etc),
 * family side (father's side, mother's side) or by gender (male/female)
 * */
public class FilterActivity extends AppCompatActivity {

    private ListView listView;
    private MenuItem filterButton;
    private MenuItem settingsButton;
    private MenuItem searchButton;
    private MenuItem goToTopButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        this.listView = (ListView) findViewById(R.id.filter_items);
        FilterAdapter adapter = new FilterAdapter(this, R.layout.filter_list_item, ModelData.getInstance().getEventTypes());
        this.listView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.filter_app_bar);
        toolbar.setTitle("Filter");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        //hide menu items
        filterButton = menu.findItem(R.id.person_filter);
        filterButton.setVisible(false);

        settingsButton = menu.findItem(R.id.person_settings);
        settingsButton.setVisible(false);

        searchButton = menu.findItem(R.id.person_search_button);
        searchButton.setVisible(false);

        //set up goToTop button
        goToTopButton = menu.findItem(R.id.person_action_go_to_top);
        goToTopButton.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Utils.startTopActivity(this,true);
        return true;
    }
}
