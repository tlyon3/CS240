package edu.tlyon.familymap.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.Set;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Settings;

public class MainActivity extends AppCompatActivity {

    private SignInFragment signInFragment;
    private MapFrag mapFrag;
    private MenuItem filterButton;
    private MenuItem searchButton;
    private MenuItem settingsButton;
    private boolean shouldShowMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.shouldShowMenu = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();

        if (ModelData.getInstance().getCurrentUser() == null) {
//            this.signInFragment = (SignInFragment) fm.findFragmentById(R.id.fragment_container);
            if (signInFragment == null) {
                this.signInFragment = SignInFragment.newInstance("Sign In");
                fm.beginTransaction()
                        .add(R.id.fragment_container, this.signInFragment)
                        .commit();
            }
        } else {
            this.shouldShowMenu = true;
            this.mapFrag = MapFrag.newInstance("mapfrag");
            fm.beginTransaction()
                    .replace(R.id.fragment_container, this.mapFrag)
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_app_bar);
        toolbar.setTitle("FamilyMap");
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        filterButton = menu.findItem(R.id.filter);
        Drawable filterIcon = new IconDrawable(this, Iconify.IconValue.fa_filter).colorRes(R.color.colorPrimaryDark).sizeDp(40);
        filterButton.setIcon(filterIcon);
        filterButton.setVisible(shouldShowMenu);

        settingsButton = menu.findItem(R.id.settings);
        Drawable settingsIcon = new IconDrawable(this, Iconify.IconValue.fa_gear).color(R.color.colorPrimaryDark).sizeDp(40);
        settingsButton.setIcon(settingsIcon);
        settingsButton.setVisible(shouldShowMenu);

        searchButton = menu.findItem(R.id.search);
        Drawable searchIcon = new IconDrawable(this, Iconify.IconValue.fa_search).color(R.color.colorPrimaryDark).sizeDp(40);
        searchButton.setIcon(searchIcon);
        searchButton.setVisible(shouldShowMenu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.filter:
                Intent filterIntent = new Intent(this, FilterActivity.class);
                this.startActivity(filterIntent);
                return true;
            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                this.startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void swapToMapFragment() {
        Settings.getInstance().setUpFilterSettings();
        //hide keyboard
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        if (this.mapFrag == null) {
            this.mapFrag = MapFrag.newInstance("mapfrag");
        }

        filterButton.setVisible(true);
        settingsButton.setVisible(true);
        searchButton.setVisible(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, this.mapFrag)
                .commit();
    }

}
