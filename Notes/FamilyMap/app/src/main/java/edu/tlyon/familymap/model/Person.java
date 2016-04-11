package edu.tlyon.familymap.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tlyon on 3/17/16.
 */
public class Person {
    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherId;
    private String motherId;
    private String spouseId;
    private Set<String> children;

    public Person(String descendant, String personId, String firstName, String lastName,
                  String gender, String fatherId, String motherId) {
        this.descendant = descendant;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.children = new HashSet<>();
    }

    public Person() {
        this.descendant = "";
        this.personId = "";
        this.firstName = "";
        this.lastName = "";
        this.gender = "";
        this.fatherId = "";
        this.motherId = "";
        this.spouseId = "";
        this.children = new HashSet<>();
    }

    public String getRelation(String otherId){
        if(otherId.equals(this.spouseId))
            return "Spouse";
        else if(otherId.equals(this.fatherId))
            return "Father";
        else if(otherId.equals(this.motherId))
            return "Mother";
        else if(this.children.contains(otherId))
            return "Child";
        else
            return "NORELATION";
    }

    public void addChild(String id){
        this.children.add(id);
    }

    public List<Event> getLifeEvents(){
        List<String> lifeEventIds = ModelData.getInstance().getPersonEventsMap().get(this.personId);
        List<Event> lifeEvents = new ArrayList<>();
        for(String eventId:lifeEventIds){
            Event event = ModelData.getInstance().getEventIdMap().get(eventId);
            lifeEvents.add(event);
        }
        return sortLifeEvents(lifeEvents);
    }

    private List<Event> sortLifeEvents(List<Event> lifeEvents){
        Collections.sort(lifeEvents, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                return Integer.parseInt(lhs.getYear()) - Integer.parseInt(rhs.getYear());
            }
        });
        return lifeEvents;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }

    public Set<String> getChildren() {
        return children;
    }

    public void setChildren(Set<String> children) {
        this.children = children;
    }
}
