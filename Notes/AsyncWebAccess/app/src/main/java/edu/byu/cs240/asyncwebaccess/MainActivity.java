package edu.byu.cs240.asyncwebaccess;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView totalSizeTextView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        totalSizeTextView = (TextView)findViewById(R.id.totalSizeTextView);

        downloadButton = (Button)findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadButtonClicked();
            }
        });

        resetViews();
    }

    private void resetViews() {
        progressBar.setProgress(0);
        totalSizeTextView.setText("Total Size:");
    }

    private void downloadButtonClicked() {
        try {
            resetViews();

            DownloadTask task = new DownloadTask();

            task.execute(new URL("http://home.byu.edu/home/"),
                    new URL("https://www.whitehouse.gov/"),
                    new URL("http://www.oracle.com/index.html"));
        }
        catch (MalformedURLException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }


    public class DownloadTask extends AsyncTask<URL, Integer, Long> {

        protected Long doInBackground(URL... urls) {

            HttpClient httpClient = new HttpClient();

            long totalSize = 0;

            for (int i = 0; i < urls.length; i++) {

                String urlContent = httpClient.getUrl(urls[i]);
                if (urlContent != null) {
                    totalSize += urlContent.length();
                }

                int progress = 0;
                if (i == urls.length - 1) {
                    progress = 100;
                }
                else {
                    float cur = i + 1;
                    float total = urls.length;
                    progress = (int)((cur / total) * 100);
            }
                publishProgress(progress);
            }

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            progressBar.setProgress(progress[0]);
        }

        protected void onPostExecute(Long result) {
            totalSizeTextView.setText("Total Size: " + result);
        }
    }
}
