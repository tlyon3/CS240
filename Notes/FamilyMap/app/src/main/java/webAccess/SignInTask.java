package webAccess;

import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by tlyon on 3/15/16.
 */
public class SignInTask extends AsyncTask<URL, Integer, Long>{
    @Override
    protected Long doInBackground(URL... params) {
        HttpClient httpClient = new HttpClient();
        long totalSize = 0;
        for(int i=0;i<params.length;i++){
            String urlContent = httpClient.getURL(params[i]);
            if(urlContent != null){
                totalSize += urlContent.length();
            }

            int progress = 0;
            if(i == params.length -1){
                progress = 100;
            }
            else {
                float cur = i+1;
                float total = params.length;
                progress = (int)((cur / total) * 100);
            }
            publishProgress(progress);
        }
        return totalSize;
    }
}
