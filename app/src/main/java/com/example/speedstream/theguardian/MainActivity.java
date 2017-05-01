package com.example.speedstream.theguardian;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String SECTIONS_URL = "http://content.guardianapis.com/sections?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    SomeConnections mainActivityConnection = new SomeConnections();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Kick off an {@link AsyncTask} to perform the network request
        /*NewsAsyncTask task = new NewsAsyncTask();
        task.execute();*/
        new NewsAsyncTask().execute();
    }

    private void updateUi(ArrayList<News> sections) {
        // Display the sections title in the UI
        NewsSectionsAdapter allSections = new NewsSectionsAdapter(this, sections);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list_sections);
        listView.setAdapter(allSections);
    }

    private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(URL... urls) {
            // Create URL object
            URL url = mainActivityConnection.createUrl(SECTIONS_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                Log.d(LOG_TAG, "making http request");
                jsonResponse = mainActivityConnection.makeHttpRequest(url);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Get failed for unknown reason");
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ExtractFromJson extractSections = new ExtractFromJson();

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return extractSections.extractFeature("main", jsonResponse);
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link NewsAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<News> mainSection) {
            if (mainSection == null) {
                return;
            }
            updateUi(mainSection);
        }
    }
}
