package webAccess;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tlyon on 3/15/16.
 */
public class HttpClient {
    public String getURL(URL url){
        try{
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer,0,length);
                }

                String responseBodyData = baos.toString();
                return responseBodyData;
            }
        }
        catch (Exception e){
            Log.e("HttpClient", e.getMessage(),e);
        }
        return null;
    }
}
