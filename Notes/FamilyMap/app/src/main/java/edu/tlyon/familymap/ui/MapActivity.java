package edu.tlyon.familymap.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.amazon.geo.mapsv2.pvt.Util;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;

public class MapActivity extends AppCompatActivity {

    private MenuItem filterButton;
    private MenuItem settingsButton;
    private MenuItem searchButton;
    private MenuItem goToTopButton;
    private MapFrag mapFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String eventId = getIntent().getStringExtra("eventId");
        Event event = ModelData.getInstance().getEventIdMap().get(eventId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        FragmentManager fm = this.getSupportFragmentManager();
        if(this.mapFrag == null){
            this.mapFrag = MapFrag.newInstance("Event Details");
        }
        mapFrag.setSelectedEvent(event);
        fm.beginTransaction().add(R.id.map_fragment_container,this.mapFrag).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.map_app_bar);
        toolbar.setTitle("Event Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);

        filterButton = menu.findItem(R.id.person_filter);
        filterButton.setVisible(false);

        settingsButton = menu.findItem(R.id.person_settings);
        settingsButton.setVisible(false);

        searchButton = menu.findItem(R.id.person_search_button);
        searchButton.setVisible(false);

        goToTopButton = menu.findItem(R.id.person_action_go_to_top);
        Drawable topIcon = new IconDrawable(this, Iconify.IconValue.fa_angle_double_up).color(R.color.colorPrimaryDark).sizeDp(40);
        goToTopButton.setIcon(topIcon);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.person_action_go_to_top:
                Utils.startTopActivity(this,true);
                return true;
            default:
                return false;
        }
    }

    public MenuItem getFilterButton() {
        return filterButton;
    }

    public void setFilterButton(MenuItem filterButton) {
        this.filterButton = filterButton;
    }

    public MenuItem getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(MenuItem settingsButton) {
        this.settingsButton = settingsButton;
    }

    public MenuItem getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(MenuItem searchButton) {
        this.searchButton = searchButton;
    }

    public MenuItem getGoToTopButton() {
        return goToTopButton;
    }

    public void setGoToTopButton(MenuItem goToTopButton) {
        this.goToTopButton = goToTopButton;
    }

    public MapFrag getMapFrag() {
        return mapFrag;
    }

    public void setMapFrag(MapFrag mapFrag) {
        this.mapFrag = mapFrag;
    }
}
