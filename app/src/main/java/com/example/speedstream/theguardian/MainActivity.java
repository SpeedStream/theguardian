package com.example.speedstream.theguardian;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String JSON_URL = "http://content.guardianapis.com/search?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    extractNews newsExtractor = new extractNews();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "entre a oncreate");
        // Kick off an {@link AsyncTask} to perform the network request
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(ArrayList<News> theNews) {
        /*// Display the earthquake title in the UI
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        // Display the earthquake date in the UI
        TextView dateTextView = (TextView) findViewById(R.id.date);
        dateTextView.setText(getDateString(earthquake.time));

        // Display whether or not there was a tsunami alert in the UI
        TextView tsunamiTextView = (TextView) findViewById(R.id.tsunami_alert);
        tsunamiTextView.setText(getTsunamiAlertString(earthquake.tsunamiAlert));*/

        NewsfeedAdapter flavorAdapter = new NewsfeedAdapter(this, theNews);

        // Get a reference to the ListView, and attach the adapter to the listView.
        ListView listView = (ListView) findViewById(R.id.listNews);
        listView.setAdapter(flavorAdapter);
    }

    private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<News>> {


        @Override
        protected ArrayList<News> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(JSON_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                Log.d(LOG_TAG, "making http request");
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.d(LOG_TAG, "Get failed for unknown reason");
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            ArrayList<News> extractingNews = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return extractingNews;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link NewsAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<News> earthquake) {
            if (earthquake == null) {
                return;
            }

            updateUi(earthquake);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            URL url;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            Log.d(LOG_TAG,"entre en makehttprequest");
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                if(urlConnection.getResponseCode() == 200){
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                    if(TextUtils.isEmpty(jsonResponse))
                        Log.d(LOG_TAG, "json response is empty");
                    else
                        Log.d(LOG_TAG, "JSON RESPONSE NOT EMPTY");
                }
                else{
                    Log.d(LOG_TAG, "Response code= " + urlConnection.getResponseCode());
                }


            } catch (IOException e) {
                // TODO: Handle the exception
                Log.d(LOG_TAG, "catcheando");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link News} object by parsing out information
         * about the first earthquake from the input earthquakeJSON string.
         */
        private ArrayList<News> extractFeatureFromJson(String earthquakeJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);

                JSONObject responseObj = baseJsonResponse.getJSONObject("response");
                JSONArray resultsArray = responseObj.getJSONArray("results");

                ArrayList<News> newsList = new ArrayList<>();

                for(int i=0; i < resultsArray.length(); i++){
                    JSONObject oneNews = resultsArray.getJSONObject(i);

                    String sectionId = oneNews.getString("sectionId");
                    String sectionName = oneNews.getString("sectionName");
                    String publicationDate = oneNews.getString("webPublicationDate");
                    String webTitle = oneNews.getString("webTitle");
                    String webURL = oneNews.getString("webUrl");
                    newsList.add(new News(sectionId, sectionName, publicationDate, webTitle, webURL));
                }

                return newsList;

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
