package edu.tlyon.familymap.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Settings;
import edu.tlyon.familymap.webAccess.tasks.GetUserPersonTask;

/**
 * Houses settings such as "Display Family Lines", "Resync data", and "Logout"
 */
public class SettingsActivity extends AppCompatActivity {

    private Switch lifeStoryLines;
    private Switch familyTreeLines;
    private Switch spouseLines;
    private Spinner lifeStoryColors;
    private Spinner familyTreeColors;
    private Spinner spouseColors;
    private Spinner mapType;
    private RelativeLayout resyncDataLayout;
    private RelativeLayout logout;
    private MenuItem filterButton;
    private MenuItem settingsButton;
    private MenuItem searchButton;
    private MenuItem goToTopButton;
    private Activity activity = this;
    private String[] colorsArray;
    private String[] mapTypeArray;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.colorsArray = new String[]{
                "Green", "Blue", "Red"
        };

        this.mapTypeArray = new String[]{
                "Normal", "Satellite", "Hybrid"
        };

        setUpResyncButton();
        setUpLogoutButton();
        setUpLinesSwitches();
        setUpFamilyTreeSpinner();
        setUpLifeStorySpinner();
        setUpSpouseSpinner();
        setUpMapSpinner();

        //set up menu
        toolbar = (Toolbar) findViewById(R.id.person_app_bar);
        toolbar.setTitle("Settings");

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
        Utils.startTopActivity(this, true);
        return true;
    }

    /**
     * Sets up the spinner for selecting the colors of lifeStory lines
     */
    private void setUpLifeStorySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorsArray);
        this.lifeStoryColors = (Spinner) findViewById(R.id.life_story_colors);
        this.lifeStoryColors.setAdapter(adapter);
        this.lifeStoryColors.setSelection(Settings.getInstance().getLifeStoryLinesColor());
        this.lifeStoryColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.getInstance().setLifeStoryLinesColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    /**
     * Sets up spinner for colors of spouse lines
     */
    private void setUpSpouseSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorsArray);

        this.spouseColors = (Spinner) findViewById(R.id.spouse_lines_colors);
        this.spouseColors.setAdapter(adapter);
        this.spouseColors.setSelection(Settings.getInstance().getSpouseLinesColor());
        this.spouseColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.getInstance().setSpouseLinesColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    /**
     * Sets up spinner for colors for family tree lines
     */
    private void setUpFamilyTreeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorsArray);
        this.familyTreeColors = (Spinner) findViewById(R.id.family_tree_colors);
        this.familyTreeColors.setAdapter(adapter);
        this.familyTreeColors.setSelection(Settings.getInstance().getFamilyTreeLinesColor());
        this.familyTreeColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.getInstance().setFamilyTreeLinesColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

    }

    /**
     * Sets up switch for toggling lines
     */
    private void setUpLinesSwitches() {

        this.lifeStoryLines = (Switch) findViewById(R.id.life_story_switch);
        this.lifeStoryLines.setChecked(Settings.getInstance().getDisplayLifeStoryLines());
        lifeStoryLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setDisplayLifeStoryLines(isChecked);
            }
        });

        this.familyTreeLines = (Switch) findViewById(R.id.family_tree_switch);
        this.familyTreeLines.setChecked(Settings.getInstance().getDisplayFamilyTreeLines());
        familyTreeLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setDisplayFamilyTreeLines(isChecked);
            }
        });

        this.spouseLines = (Switch) findViewById(R.id.spouse_lines_switch);
        this.spouseLines.setChecked(Settings.getInstance().getDisplaySpouseLines());
        spouseLines.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.getInstance().setDisplaySpouseLines(isChecked);
            }
        });

    }

    /**
     * Sets up the resync button
     */
    private void setUpResyncButton() {
        this.resyncDataLayout = (RelativeLayout) findViewById(R.id.resync_data_view);
        this.resyncDataLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resyncData();
            }

        });

    }

    /**
     * Sets up the logout button
     */
    private void setUpLogoutButton() {
        this.logout = (RelativeLayout) findViewById(R.id.logout_view);
        this.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.logout(activity);
            }
        });
    }

    /**
     * Clears data from the Model and downloads it again from the server
     */
    private void resyncData() {
        ModelData.getInstance().getPersonIdMap().clear();
        ModelData.getInstance().getEventIdMap().clear();
        ModelData.getInstance().getPersonEventsMap().clear();
        ModelData.getInstance().getEventTypes().clear();
        ModelData.getInstance().getFamilyTree().clear();
        ModelData.getInstance().getMaternalAncestors().clear();
        ModelData.getInstance().getPaternalAncestors().clear();
        String userId = ModelData.getInstance().getCurrentUser().getOwnPerson().getPersonId();
        new GetUserPersonTask(this).execute("authorizationkey", userId);
    }

    /**
     * Sets up the spinner for selecting the map type
     */
    private void setUpMapSpinner() {
        ArrayAdapter<String> mapTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypeArray);
        this.mapType = (Spinner) findViewById(R.id.map_type_spinner);
        this.mapType.setAdapter(mapTypeAdapter);
        this.mapType.setSelection(Settings.getInstance().getMapType());
        this.mapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.getInstance().setMapType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

}
