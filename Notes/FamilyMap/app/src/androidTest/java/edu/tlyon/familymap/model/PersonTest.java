package edu.tlyon.familymap.model;

import junit.framework.TestCase;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by tlyon on 4/11/16.
 */
public class PersonTest extends TestCase {
    @Override
    protected void setUp() throws Exception {


        JSONObject eventObject1 = new JSONObject();
        eventObject1.put("eventID", "event1");
        eventObject1.put("description", "event1");
        eventObject1.put("personID", "personId");
        eventObject1.put("latitude", "10");
        eventObject1.put("longitude", "20");
        eventObject1.put("country", "france");
        eventObject1.put("city", "paris");
        eventObject1.put("year", "1");
        eventObject1.put("descendant", "sam");
        Event event1 = new Event(eventObject1);

        JSONObject eventObject2 = new JSONObject();
        eventObject2.put("eventID", "event2");
        eventObject2.put("description", "event2");
        eventObject2.put("personID", "personId");
        eventObject2.put("latitude", "10");
        eventObject2.put("longitude", "20");
        eventObject2.put("country", "france");
        eventObject2.put("city", "paris");
        eventObject2.put("year", "2");
        eventObject2.put("descendant", "sam");
        Event event2 = new Event(eventObject2);

        JSONObject eventObject3 = new JSONObject();
        eventObject3.put("eventID", "event3");
        eventObject3.put("description", "event3");
        eventObject3.put("personID", "personId");
        eventObject3.put("latitude", "10");
        eventObject3.put("longitude", "20");
        eventObject3.put("country", "france");
        eventObject3.put("city", "paris");
        eventObject3.put("year", "3");
        eventObject3.put("descendant", "sam");
        Event event3 = new Event(eventObject3);

        ModelData.getInstance().addEvent(event1);
        ModelData.getInstance().addEvent(event2);
        ModelData.getInstance().addEvent(event3);

        ModelData.getInstance().populatePersonEventsMap();
    }

    public void testGetLifeEvents() throws Exception {
        Person person = new Person("john","personId","bob","jones","m","fatherId","motherId");
        ModelData.getInstance().addPerson(person);
        List<Event> lifeEvents = person.getLifeEvents();

        //make sure lifeEvents is populated
        assertNotSame(0,lifeEvents.size());

        //check sorted
        for(int i=0;i<lifeEvents.size()-1;i++){
            int year1 = Integer.parseInt(lifeEvents.get(i).getYear());
            int year2 = Integer.parseInt(lifeEvents.get(i+1).getYear());
            assertEquals(true, year1 < year2);
        }
    }
}