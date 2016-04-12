package edu.tlyon.familymap.webAccess;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;

/**
 * Created by tlyon on 3/16/16.
 * Handles all communication (GETs and POSTs) with the server
 */
public class ServerFacade {
    private static ServerFacade serverFacade;
    private String host;
    private String port;

    public ServerFacade() {
    }

    public static ServerFacade getInstance() {
        if (serverFacade == null) {
            serverFacade = new ServerFacade();

            return serverFacade;
        } else return serverFacade;
    }

    /**
     * Logs the user in
     *
     * @param username Username provided by the user
     * @param password password provided by the user
     */
    public JSONObject login(String username, String password) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/user/login");
            JSONObject postData = new JSONObject();
            postData.put("username", username);
            postData.put("password", password);
            JSONObject returnedData = doPost(url, postData);
            return returnedData;
        } catch (MalformedURLException ex) {
            Log.e("ServerFacade.login()", ex.getMessage(), ex);
            JSONObject result = new JSONObject();
            try {
                result.put("message", "Server host or port not correct");
                return result;
            } catch (JSONException e) {
                return null;
            }

        } catch (JSONException ex) {

        }
        return null;
    }

    /**
     * Finds the person with the matching id from the server
     *
     * @param id Id of the person to find
     * @return Returns a person object with the matching id
     */
    public Person getPersonWithId(String id) {
        try {
            URL url = new URL("http://" + host + ":" + port + "/person/" + id);
            JSONObject result = doGet(url);
            String descendant = result.getString("descendant");
            String personId = result.getString("personID");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String gender = result.getString("gender");
            String fatherId = result.getString("father");
            String motherId = result.getString("mother");
            Person newPerson = new Person(descendant, personId, firstName, lastName, gender, fatherId, motherId);
            return newPerson;
        } catch (MalformedURLException e) {
            Log.e("ServerFacade", e.getMessage(), e);
            return null;
        } catch (JSONException ex) {
            Log.e("ServerFacade", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Gets the people associated with the current user
     */
    public JSONObject getPeople() {
        try {
            URL url = new URL("http://" + host + ":" + port + "/person/");
            JSONObject result = doGet(url);
            return result;
        } catch (MalformedURLException ex) {
            Log.e("ServerFacade", "Malformed url in getPeople", ex);
            JSONObject result = new JSONObject();
            try {
                result.put("message", "There was an error downloading information.");
                return result;
            } catch (JSONException jsonexception) {
                return null;
            }
        }
    }

    /**
     * Downloads all the events for the user
     */
    public JSONObject getEvents() {
        try {
            URL url = new URL("http://" + host + ":" + port + "/events/");
            return doGet(url);
        } catch (MalformedURLException ex) {
            Log.e("ServerFacade", "Malformed url in getEvents");
            JSONObject result = new JSONObject();
            try {
                result.put("message", "There was an error downloading information.");
                return result;
            } catch (JSONException jsonexception) {
                return null;
            }
        }
    }

    private JSONObject doGet(URL url) {
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            // Set HTTP request headers, if necessary
            connection.setRequestProperty("Authorization", ModelData.getInstance().getCurrentUser().getAuthorizationKey());
            // connection.addRequestProperty(”Accept”, ”text/html”);

            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                try {
                    JSONObject result = new JSONObject(responseBodyData);
                    return result;
                } catch (JSONException ex) {
                    Log.e("ServerFacade", ex.getMessage(), ex);
                    return null;
                }
            } else {
                Log.e("ServerFacade", "HTTP Error");
                return null;
                // SERVER RETURNED AN HTTP ERROR
            }
        } catch (IOException e) {
            // IO ERROR
        }
        return null;
    }

    private JSONObject doPost(URL url, JSONObject postData) {
        //get code from lecture slides
        String data = postData.toString();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set HTTP request headers, if necessary
            // connection.addRequestProperty(”Accept”, ”text/html”);

//            connection.connect();
            // Write post data to request body
            OutputStream requestBody = connection.getOutputStream();
            requestBody.write(data.getBytes());
            requestBody.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                try {
                    JSONObject result = new JSONObject(responseBodyData);
                    return result;
                } catch (JSONException ex) {
                    Log.e("ServerFacade", "Error creating jsonobject from returned string", ex);
                    return null;
                }

            } else {
                Log.e("ServerFacade", "HTTP Error");
                return null;
                // SERVER RETURNED AN HTTP ERROR
            }
        } catch (IOException e) {
            Log.e("ServerFacade", "IOException", e);
            // IO ERROR
            JSONObject result = new JSONObject();
            try {
                result.put("message", "Server connection timed out.");
                return result;
            } catch (JSONException ex) {
                return null;
            }
        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
