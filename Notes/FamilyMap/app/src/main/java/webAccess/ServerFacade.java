package webAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tlyon on 3/16/16.
 */
public class ServerFacade {




    public void login(String username, String password){
        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);
        }
        catch (JSONException ex){

        }
        doPost("login/user",postData);

    }

    public void getPeople(){
        doGet("/person");
    }

    public void getPerson(){

    }

    private void doGet(URL url){
        try {

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            // Set HTTP request headers, if necessary
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

                // TODO: Process response body data
                // ...
            }
            else {
                // SERVER RETURNED AN HTTP ERROR
            }
        }
        catch (IOException e) {
            // IO ERROR
        }
    }

    private void doPost(URL url, JSONObject postData){
        //get code from lecture slides
    }
}
