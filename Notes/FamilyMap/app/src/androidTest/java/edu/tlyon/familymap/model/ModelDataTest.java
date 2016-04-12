package edu.tlyon.familymap.model;

import junit.framework.TestCase;

import org.json.JSONObject;

/**
 * Created by tlyon on 4/11/16.
 */
public class ModelDataTest extends TestCase {

    public void testAddPerson() throws Exception {

        assertEquals(0, ModelData.getInstance().getPersonIdMap().size());
        Person expected = new Person("john", "1111", "bob", "jones", "m", "2222", "3333");
        ModelData.getInstance().addPerson(expected);
        assertEquals(1, ModelData.getInstance().getPersonIdMap().size());

        Person actual = ModelData.getInstance().getPersonIdMap().get("1111");

        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getDescendant(), actual.getDescendant());
        assertEquals(expected.getFatherId(), actual.getFatherId());
        assertEquals(expected.getMotherId(), actual.getMotherId());
        assertEquals(expected.getGender(), actual.getGender());
    }

    public void testAddEvent() throws Exception {
        JSONObject eventObject = new JSONObject();
        eventObject.put("eventID", "2222");
        eventObject.put("description", "test");
        eventObject.put("personID", "1111");
        eventObject.put("latitude", "10");
        eventObject.put("longitude", "20");
        eventObject.put("country", "france");
        eventObject.put("city", "paris");
        eventObject.put("year", "2000");
        eventObject.put("descendant", "sam");
        Event expected = new Event(eventObject);

        assertEquals(0, ModelData.getInstance().getEventIdMap().size());
        ModelData.getInstance().addEvent(expected);

        assertEquals(1, ModelData.getInstance().getEventIdMap().size());
        Event actual = ModelData.getInstance().getEventIdMap().get(expected.getEventId());

        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getCountry(), actual.getCountry());
        assertEquals(expected.getDescendant(), actual.getDescendant());
        assertEquals(expected.getEventId(), actual.getEventId());
        assertEquals(expected.getEventType(), actual.getEventType());
        assertEquals(expected.getLatitude(), actual.getLatitude());
        assertEquals(expected.getLongitude(), actual.getLongitude());
        assertEquals(expected.getYear(), actual.getYear());

    }

    public void testPopulatePersonEventsMap() throws Exception {
        Person person = new Person("john", "1111", "bob", "jones", "m", "2222", "3333");
        ModelData.getInstance().addPerson(person);

        JSONObject eventObject = new JSONObject();
        eventObject.put("eventID", "2222");
        eventObject.put("description", "test");
        eventObject.put("personID", "1111");
        eventObject.put("latitude", "10");
        eventObject.put("longitude", "20");
        eventObject.put("country", "france");
        eventObject.put("city", "paris");
        eventObject.put("year", "2000");
        eventObject.put("descendant", "sam");
        Event event = new Event(eventObject);
        ModelData.getInstance().addEvent(event);

        assertEquals(0, ModelData.getInstance().getPersonEventsMap().size());
        ModelData.getInstance().populatePersonEventsMap();

        assertEquals(1, ModelData.getInstance().getPersonEventsMap().size());
    }

    @Override
    protected void tearDown() throws Exception {
        ModelData.getInstance().getPersonEventsMap().clear();
        ModelData.getInstance().getEventIdMap().clear();
        ModelData.getInstance().getPersonIdMap().clear();
        ModelData.getInstance().getPaternalAncestors().clear();
        ModelData.getInstance().getMaternalAncestors().clear();
        ModelData.getInstance().getEventTypes().clear();
    }

    public void testPopulateMaternalAncestors() throws Exception {
        User user = new User("authoKey");
        Person person = new Person("bob", "personId", "john", "jones", "m", "fatherId", "motherId");
        user.setOwnPerson(person);
        Person mother = new Person("john", "motherId", "jill", "jones", "f", "grandfatherMId", "grandmotherMId");
        Person grandfatherM = new Person("john", "grandfatherMId", "jim", "jones", "m", "", "");
        Person grandmotherM = new Person("john", "grandmotherMId", "jane", "jones", "f", "", "");

        ModelData.getInstance().setCurrentUser(user);
        ModelData.getInstance().addPerson(person);
        ModelData.getInstance().addPerson(mother);
        ModelData.getInstance().addPerson(grandfatherM);
        ModelData.getInstance().addPerson(grandmotherM);

        ModelData.getInstance().populateMaternalAncestors();

        assertNotSame(0, ModelData.getInstance().getMaternalAncestors());

        Person actualMother = ModelData.getInstance().getPersonIdMap().get(person.getMotherId());
        assertEquals(mother.getLastName(), actualMother.getLastName());
        assertEquals(mother.getFirstName(), actualMother.getFirstName());
    }

    public void testPopulatePaternalAncestors() throws Exception {
        User user = new User("authoKey");
        Person person = new Person("bob", "personId", "john", "jones", "m", "fatherId", "motherId");
        user.setOwnPerson(person);
        Person father = new Person("john", "fatherId", "bob", "jones", "m", "", "");

        ModelData.getInstance().setCurrentUser(user);
        ModelData.getInstance().addPerson(person);
        ModelData.getInstance().addPerson(father);
        ModelData.getInstance().populatePaternalAncestors();
        assertNotSame(0, ModelData.getInstance().getPaternalAncestors());

        Person actualFather = ModelData.getInstance().getPersonIdMap().get(person.getFatherId());
        assertEquals(father.getFirstName(), actualFather.getFirstName());
        assertEquals(father.getLastName(), actualFather.getLastName());
    }
}