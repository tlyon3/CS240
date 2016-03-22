package edu.tlyon.familymap.model;

import java.util.Map;

/**
 * Created by tlyon on 3/17/16.
 */
public class ModelData {
    private User currentUser;
    private Map<String, Person> personIdMap;
    private Map<String, Event> eventIdMap;

    private static ModelData ourInstance = new ModelData();

    public static ModelData getInstance() {
        return ourInstance;
    }

    private ModelData() {
    }

    public void loadData(){

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
