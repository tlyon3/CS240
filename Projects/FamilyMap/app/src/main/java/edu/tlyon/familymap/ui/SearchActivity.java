package edu.tlyon.familymap.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.ui.recyclerview.SearchExpandableListAdapter;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchBar;
    private SearchExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private Activity activity = this;
    private Toolbar toolbar;

    private MenuItem filterButton;
    private MenuItem settingsButton;
    private MenuItem searchButton;
    private MenuItem goToTopButton;

    /**
     * Allows the user to search through all the ModelData (persons and events)
     */
    public SearchActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = (Toolbar) findViewById(R.id.search_app_bar);
        toolbar.setTitle("Search");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.searchBar = (SearchView) findViewById(R.id.search_bar);
        this.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.searchExpandableListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (expandableListAdapter.getGroup(groupPosition).equals("People")) {
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
        this.onBackPressed();
        return true;
    }

    /**
     * Finds all events and people that match the search prompt
     *
     * @param prompt Search prompt provided by the user"
     */
    private void search(String prompt) {
        expandableListDetail = new HashMap<>();
        expandableListDetail.put("People", getPersonSearchResults(prompt));
        expandableListDetail.put("Events", getEventSearchResults(prompt));
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new SearchExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    /**
     * Returns a list of eventId's that matched the search prompt
     *
     * @param prompt Search prompt provided by the user
     */
    private List<String> getEventSearchResults(String prompt) {
        List<String> result = new ArrayList<>();
        Map<String, Event> events = ModelData.getInstance().getEventIdMap();
        Iterator it = events.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Event event = (Event) pair.getValue();
            if (containsIgnoreCase(event.getCountry(), prompt))
                result.add((String) pair.getKey());
            else if (containsIgnoreCase(event.getCity(), prompt))
                result.add((String) pair.getKey());
            else if (containsIgnoreCase(event.getEventType(), prompt))
                result.add((String) pair.getKey());
        }
        return result;
    }

    /**
     * Returns a list of personId's that matched the search prompt
     *
     * @param prompt Search prompt provided by the user
     */
    private List<String> getPersonSearchResults(String prompt) {
        List<String> result = new ArrayList<>();
        Map<String, Person> people = ModelData.getInstance().getPersonIdMap();
        Iterator it = people.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Person person = (Person) pair.getValue();
            if (containsIgnoreCase(person.getFirstName(), prompt))
                result.add((String) pair.getKey());
            else if (containsIgnoreCase(person.getLastName(), prompt))
                result.add((String) pair.getKey());
        }
        return result;
    }

    /**
     * Allows the String.contains() function to ignore case
     */
    private boolean containsIgnoreCase(String term, String search) {
        String t = term.toLowerCase();
        String s = search.toLowerCase();
        return t.contains(s);
    }
}
