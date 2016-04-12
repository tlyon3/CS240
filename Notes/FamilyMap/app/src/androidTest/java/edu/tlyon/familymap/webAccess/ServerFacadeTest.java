package edu.tlyon.familymap.webAccess;

import junit.framework.TestCase;

import org.json.JSONObject;

import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.User;

/**
 * Created by tlyon on 4/11/16.
 */
public class ServerFacadeTest extends TestCase {

    /**
     * Test the login() method in the server facade.
     * The Strings host and port are hardcoded. Will need to be changed if server is restarted
     * on another network.
     * goodResult will have a "message" field if the getEvents() was unsuccessful.
     * result should have a "message" field because incorrect credentials were provided.
     */
    public void testLogin() throws Exception {
        //check if there was an error with correct info
        String host = "10.10.19.230";
        String port = "8080";
        String username = "john";
        String password = "john";
        ServerFacade.getInstance().setHost(host);
        ServerFacade.getInstance().setPort(port);
        JSONObject goodResult = ServerFacade.getInstance().login(username, password);
        assertEquals(false, goodResult.has("message"));

        //check with bad login credentials
        username = "bob";
        password = "bob";
        JSONObject result = ServerFacade.getInstance().login(username, password);
        assertEquals(true, result.has("message"));
    }

    /**
     * Test the getPeople() method in the server facade.
     * The Strings host and port are hardcoded. Will need to be changed if server is restarted
     * on another network.
     * peopleResult will have a "message" field if the getEvents() was unsuccessful.
     */
    public void testGetPeople() throws Exception {
        String host = "10.10.19.230";
        String port = "8080";
        String username = "john";
        String password = "john";
        ServerFacade.getInstance().setHost(host);
        ServerFacade.getInstance().setPort(port);
        JSONObject result = ServerFacade.getInstance().login(username, password);

        String authorizationCode = result.getString("Authorization");
        String personId = result.getString("personId");
        User user = new User(authorizationCode);
        ModelData.getInstance().setCurrentUser(user);

        JSONObject peopleResult = ServerFacade.getInstance().getPeople();
        assertEquals(false, peopleResult.has("message"));
    }

    /**
     * Test the getEvents() method in the server facade.
     * The Strings host and port are hardcoded. Will need to be changed if server is restarted
     * on another network.
     * eventResult will have a "message" field if the getEvents() was unsuccessful.
     */
    public void testGetEvents() throws Exception {
        String host = "10.10.19.230";
        String port = "8080";
        String username = "john";
        String password = "john";
        ServerFacade.getInstance().setHost(host);
        ServerFacade.getInstance().setPort(port);
        JSONObject result = ServerFacade.getInstance().login(username, password);

        String authorizationCode = result.getString("Authorization");
        String personId = result.getString("personId");
        User user = new User(authorizationCode);
        ModelData.getInstance().setCurrentUser(user);

        JSONObject eventResult = ServerFacade.getInstance().getEvents();
        assertEquals(false, eventResult.has("message"));
    }
}