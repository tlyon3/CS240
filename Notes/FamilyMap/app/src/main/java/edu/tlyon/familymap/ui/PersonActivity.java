package edu.tlyon.familymap.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.ui.recyclerview.ExpandableListAdapter;
import edu.tlyon.familymap.ui.recyclerview.ExpandableListDataPump;

/**
 * Displays the Persons relatives and his/her life events
 */
public class PersonActivity extends AppCompatActivity {
    private Person person;
    private Toolbar toolbar;
    private MenuItem filterButton;
    private MenuItem settingsButton;
    private MenuItem searchButton;
    private MenuItem goToTopButton;
    private List<String> events;
    private TextView personName;
    private ImageView personIcon;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private Activity activity = this;

    public PersonActivity(Person person) {
        this.person = person;
    }

    public PersonActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String personId = getIntent().getStringExtra("personId");
        this.person = ModelData.getInstance().getPersonIdMap().get(personId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        toolbar = (Toolbar) findViewById(R.id.person_app_bar);
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        toolbar.setTitle(firstName + " " + lastName);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set up name of person and icon
        this.personName = (TextView) findViewById(R.id.name_text_view);
        this.personName.setText(toolbar.getTitle());
        this.personIcon = (ImageView) findViewById(R.id.person_icon);
        if (person.getGender().equals("m")) {
            Drawable icon = new IconDrawable(this, Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
            personIcon.setImageDrawable(icon);
        } else {
            Drawable icon = new IconDrawable(this, Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
            personIcon.setImageDrawable(icon);
        }

        //set up expandable recycler view
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(this.person);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(this, expandableListTitle, expandableListDetail, this.person);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (expandableListAdapter.getGroup(groupPosition).equals("Family")) {
                    final String personId = (String) expandableListAdapter.getChild(groupPosition, childPosition);
                    Intent intent = new Intent(activity, PersonActivity.class);
                    intent.putExtra("personId", personId);
                    activity.startActivity(intent);
                } else {
                    final String eventId = (String) expandableListAdapter.getChild(groupPosition, childPosition);
                    Intent intent = new Intent(activity, MapActivity.class);
                    intent.putExtra("eventId", eventId);
                    activity.startActivity(intent);
                }
                return true;
            }
        });

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
                Utils.startTopActivity(this, true);
                return true;
            default:
                return false;
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
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

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

}
