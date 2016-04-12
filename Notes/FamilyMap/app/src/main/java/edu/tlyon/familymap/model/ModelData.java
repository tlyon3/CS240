package edu.tlyon.familymap.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by tlyon on 3/17/16.
 * Singleton pattern class to hold data for the application
 */
public class ModelData {
    private User currentUser;
    private Map<String, Person> personIdMap;
    private Map<String, Event> eventIdMap;
    /** Connects a personId to a list of eventId's associated with him/her*/
    private Map<String, List<String>> personEventsMap;

    /** Types of events (birth, death, christening, etc)*/
    private List<String> eventTypes;
    /**map of personId's to set of personId's*/
    private Map<String, Set<String>> familyTree;
    /** Set of personId of users maternal ancestors*/
    private Set<String> maternalAncestors;
    /** Set of personId of users paternal ancestors*/
    private Set<String> paternalAncestors;


    private static ModelData ourInstance = new ModelData();

    public static ModelData getInstance() {
        return ourInstance;
    }

    private ModelData() {
        personIdMap = new HashMap<>();
        eventIdMap = new HashMap<>();
        personEventsMap = new HashMap<>();
        eventTypes = new ArrayList<>();
        familyTree = new HashMap<>();
        maternalAncestors = new HashSet<>();
        paternalAncestors = new HashSet<>();
    }


    public void addPerson(Person person){
        String id = person.getPersonId();
        personIdMap.put(id, person);
    }

    public void addEvent(Event event){
        String id = event.getEventId();
        eventIdMap.put(id, event);
    }

    /** Links people to events*/
    public void populatePersonEventsMap(){
        Iterator it = eventIdMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            String eventId = (String)pair.getKey();
            Event event = (Event)pair.getValue();
            String personId = event.getPersonId();
            if(this.personEventsMap.get(personId) == null){
                personEventsMap.put(personId, new ArrayList<String>());
            }
            personEventsMap.get(personId).add(eventId);
        }
    }

    /** Links all maternal ancestors to current user*/
    public void populateMaternalAncestors(){
        String motherId = currentUser.getOwnPerson().getMotherId();
        maternalAncestors.add(motherId);
        Person mother = ModelData.getInstance().personIdMap.get(motherId);
        mother.addChild(currentUser.getOwnPerson().getPersonId());
        populateMaternalAncestorsRecursive(motherId);
    }

    private void populateMaternalAncestorsRecursive(String id){
        Person person = personIdMap.get(id);
        if(!person.getFatherId().equals("")){
            maternalAncestors.add(person.getFatherId());
            ModelData.getInstance().personIdMap.get(person.getFatherId()).addChild(id);
            populateMaternalAncestorsRecursive(person.getFatherId());
        }
        if(!person.getMotherId().equals("")){
            maternalAncestors.add(person.getMotherId());
            ModelData.getInstance().personIdMap.get(person.getMotherId()).addChild(id);
            populateMaternalAncestorsRecursive(person.getMotherId());
        }
    }

    /** Links all paternal ancestors to current user*/
    public void populatePaternalAncestors(){
        String fatherId = currentUser.getOwnPerson().getFatherId();
        paternalAncestors.add(fatherId);
        ModelData.getInstance().personIdMap.get(fatherId).addChild(currentUser.getOwnPerson().getPersonId());
        populatePaternalAncestorsRecursive(fatherId);

    }

    private void populatePaternalAncestorsRecursive(String id){
        Person person = personIdMap.get(id);
        if(!person.getFatherId().equals("")){
            paternalAncestors.add(person.getFatherId());
            ModelData.getInstance().personIdMap.get(person.getFatherId()).addChild(id);
            populatePaternalAncestorsRecursive(person.getFatherId());
        }
        if (!person.getMotherId().equals("")){
            paternalAncestors.add(person.getMotherId());
            ModelData.getInstance().personIdMap.get(person.getMotherId()).addChild(id);
            populatePaternalAncestorsRecursive(person.getMotherId());
        }
    }

    /** Creates a set of all the current event types in the Model*/
    public void populateEventTypes(){
        Iterator it = eventIdMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            String eventType = ((Event)pair.getValue()).getEventType();
            if(!eventTypes.contains(eventType)) {
                this.eventTypes.add(eventType);
            }
        }
    }


    //getters and setters
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Map<String, Person> getPersonIdMap() {
        return personIdMap;
    }

    public void setPersonIdMap(Map<String, Person> personIdMap) {
        this.personIdMap = personIdMap;
    }

    public Map<String, Event> getEventIdMap() {
        return eventIdMap;
    }

    public void setEventIdMap(Map<String, Event> eventIdMap) {
        this.eventIdMap = eventIdMap;
    }

    public Map<String, List<String>> getPersonEventsMap() {
        return personEventsMap;
    }

    public void setPersonEventsMap(Map<String, List<String>> personEventsMap) {
        this.personEventsMap = personEventsMap;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Map<String, Set<String>> getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree(Map<String, Set<String>> familyTree) {
        this.familyTree = familyTree;
    }

    public Set<String> getMaternalAncestors() {
        return maternalAncestors;
    }

    public void setMaternalAncestors(Set<String> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public Set<String> getPaternalAncestors() {
        return paternalAncestors;
    }

    public void setPaternalAncestors(Set<String> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    public static ModelData getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(ModelData ourInstance) {
        ModelData.ourInstance = ourInstance;
    }
}
