package webAccess;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import model.ModelData;
import model.Person;

/**
 * Created by tlyon on 3/16/16.
 */
public class ServerFacade {
    private static ServerFacade serverFacade;
    private String host;
    private String port;
    public ServerFacade(){}
    public static ServerFacade getInstance(){
        if(serverFacade == null){
            serverFacade = new ServerFacade();
            return serverFacade;
        }
        else return serverFacade;
    }
    public JSONObject login(String username, String password){
        try {
            URL url = new URL("http://"+ host + ":" + port + "/user/login");
            JSONObject postData = new JSONObject();
            try {
                postData.put("username", username);
                postData.put("password", password);
            } catch (JSONException ex) {

            }
            JSONObject returnedData = doPost(url, postData);
            return returnedData;
        }
        catch (MalformedURLException ex){
            Log.e("ServerFacade.login()",ex.getMessage(),ex);
        }
        return null;
    }

    public Person getPersonWithId(String id){
        try {
            URL url = new URL("http://" + host + ":" + port + "/person/"+id);
            JSONObject result = doGet(url);
            // TODO: 3/16/16 Process returned information
            String descendant = result.getString("descendant");
            String personId = result.getString("personID");
            String firstName = result.getString("firstName");
            String lastName = result.getString("lastName");
            String gender = result.getString("gender");
            String fatherId = result.getString("father");
            String motherId = result.getString("mother");
            Person newPerson = new Person(descendant,personId,firstName,lastName,gender,fatherId,motherId);
            return newPerson;
        }
        catch (MalformedURLException e){
            Log.e("ServerFacade",e.getMessage(),e);
            return null;
        }
        catch(JSONException ex){
            Log.e("ServerFacade", ex.getMessage(),ex);
            return null;
        }
    }

    private JSONObject doGet(URL url){
        try {

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

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
                }
                catch (JSONException ex){
                    Log.e("ServerFacade",ex.getMessage(),ex);
                    return null;
                }
            }
            else {
                Log.e("ServerFacade","HTTP Error");
                return null;
                // SERVER RETURNED AN HTTP ERROR
            }
        }
        catch (IOException e) {
            // IO ERROR
        }
        return null;
    }

    private  JSONObject doPost(URL url, JSONObject postData){
        //get code from lecture slides
        String data = postData.toString();
        try {
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set HTTP request headers, if necessary
            // connection.addRequestProperty(”Accept”, ”text/html”);

//            connection.connect();
            // Write post data to request body
            OutputStream requestBody = connection.getOutputStream();
//            requestBody.write(postData.getBytes());
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
                }
                catch (JSONException ex){
                    Log.e("ServerFacade","Error creating jsonobject from returned string",ex);
                    return null;
                }

            }
            else {
                Log.e("ServerFacade","HTTP Error");
                return null;
                // SERVER RETURNED AN HTTP ERROR
            }
        }
        catch (IOException e) {
            Log.e("ServerFacade","IOException",e);
            // IO ERROR
        }
        return null;
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
