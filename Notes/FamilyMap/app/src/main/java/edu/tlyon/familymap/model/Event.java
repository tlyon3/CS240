package edu.tlyon.familymap.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tlyon on 3/21/16.
 * Class that holds the data for an event
 */
public class Event {
    private String eventId;
    private String eventType;
    private String personId;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String year;
    private String descendant;

    public Event(JSONObject object) throws JSONException{
        this.eventId = object.getString("eventID");
        this.personId = object.getString("personID");
        this.eventType = object.getString("description").toLowerCase();
        this.latitude = object.getString("latitude");
        this.longitude = object.getString("longitude");
        this.country = object.getString("country");
        this.city = object.getString("city");
        this.year = object.getString("year");
        this.descendant = object.getString("descendant");
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
}
