package com.example.speedstream.theguardian;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ActivitySectionNews extends AppCompatActivity {
    public static final String LOG_TAG = ActivitySectionNews.class.getSimpleName();
    public static final String API_KEY = "?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    SomeConnections newsActivityConnection = new SomeConnections();
    private String internalApiURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        internalApiURI = getIntent().getStringExtra("sectionURL")+API_KEY;      //Recover URL parsed from newsSectionAdapter

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_news);
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(ArrayList<News> sections) {
        // Display the sections title in the UI
        NewsfeedAdapter sectionAdapter = new NewsfeedAdapter(this, sections);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.list_news);
        listView.setAdapter(sectionAdapter);
    }

    private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<News>> {
        @Override
        protected ArrayList<News> doInBackground(URL... urls) {
            // Create URL object
            URL url = newsActivityConnection.createUrl(internalApiURI);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = newsActivityConnection.makeHttpRequest(url);
            } catch (IOException e) {
                Log.d(LOG_TAG, "Get failed for unknown reason");
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ExtractFromJson extractSections = new ExtractFromJson();

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return extractSections.extractFeature("section", jsonResponse);
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link NewsAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<News> section) {
            if (section == null) {
                return;
            }
            updateUi(section);
        }
    }
}
