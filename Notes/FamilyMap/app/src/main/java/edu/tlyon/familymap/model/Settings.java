package edu.tlyon.familymap.model;

import java.util.Map;

/**
 * Created by tlyon on 3/21/16.
 */
public class Settings {

    private Map<String, Boolean> filterSettings;
    private Boolean displayMotherSide;
    private Boolean displayFatherSide;
    private static Settings ourInstance = new Settings();

    public static Settings getInstance() {
        return ourInstance;
    }

    private Settings() {
    }

    public Map<String, Boolean> getFilterSettings() {
        return filterSettings;
    }

    public void setFilterSettings(Map<String, Boolean> filterSettings) {
        this.filterSettings = filterSettings;
    }
}
