package edu.tlyon.familymap.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by tlyon on 3/21/16.
 * Keeps track of filter settings and settings in the SettingsActivity
 */
public class Settings {

    //map types
    public final int NORMAL = 0;
    public final int SATELLITE = 1;
    public final int HYBRID = 2;

    //line colors
    public final int GREEN = 0;
    public final int RED = 2;
    public final int BLUE = 1;

    private Boolean displayLifeStoryLines = true;
    private Boolean displayFamilyTreeLines = true;
    private Boolean displaySpouseLines = true;

    private int lifeStoryLinesColor = GREEN;
    private int familyTreeLinesColor = BLUE;
    private int spouseLinesColor = RED;

    private int mapType = NORMAL;

    private Map<String, Boolean> filterDisplaySettings = new HashMap<>();
    private Map<String, Float> markerHueMap = new HashMap<>();

    private static Settings ourInstance = new Settings();

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {

    }

    /**
     * Sets up the filterDisplaySettings. Populates the map from eventTypes from the Model
     */
    public void setUpFilterSettings() {
        this.filterDisplaySettings = new HashMap<>();
        for (String eventType : ModelData.getInstance().getEventTypes()) {
            filterDisplaySettings.put(eventType, true);
        }
        filterDisplaySettings.put("mother's side", true);
        filterDisplaySettings.put("father's side", true);
        filterDisplaySettings.put("male", true);
        filterDisplaySettings.put("female", true);
    }

    /**
     * Adds and removes events in the list as necessary. Called when resync is called
     */
    public void updateFilterSettings() {
        //add new event types
        for (String eventType : ModelData.getInstance().getEventTypes()) {
            if (!filterDisplaySettings.containsKey(eventType)) {
                filterDisplaySettings.put(eventType, true);
            }
        }
        Set<String> typesToRemove = new HashSet<>();
        Set<String> typesToKeep = new HashSet<>();
        typesToKeep.add("mother's side");
        typesToKeep.add("father's side");
        typesToKeep.add("male");
        typesToKeep.add("female");

        //remove old event types
        Iterator it = filterDisplaySettings.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String eventType = (String) pair.getKey();
            if (!ModelData.getInstance().getEventTypes().contains(eventType) && !typesToKeep.contains(eventType)) {
                typesToRemove.add(eventType);
            }
        }
        for (String type : typesToRemove) {
            filterDisplaySettings.remove(type);
        }

    }

    public Boolean getDisplayLifeStoryLines() {
        return displayLifeStoryLines;
    }

    public void setDisplayLifeStoryLines(Boolean displayLifeStoryLines) {
        this.displayLifeStoryLines = displayLifeStoryLines;
    }

    public Boolean getDisplayFamilyTreeLines() {
        return displayFamilyTreeLines;
    }

    public void setDisplayFamilyTreeLines(Boolean displayFamilyTreeLines) {
        this.displayFamilyTreeLines = displayFamilyTreeLines;
    }

    public Boolean getDisplaySpouseLines() {
        return displaySpouseLines;
    }

    public void setDisplaySpouseLines(Boolean displaySpouseLines) {
        this.displaySpouseLines = displaySpouseLines;
    }

    public int getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public int getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public int getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public Map<String, Boolean> getFilterDisplaySettings() {
        return filterDisplaySettings;
    }

    public void setFilterDisplaySettings(Map<String, Boolean> filterDisplaySettings) {
        this.filterDisplaySettings = filterDisplaySettings;
    }

    public Map<String, Float> getMarkerHueMap() {
        return markerHueMap;
    }

    public void setMarkerHueMap(Map<String, Float> markerHueMap) {
        this.markerHueMap = markerHueMap;
    }
}
