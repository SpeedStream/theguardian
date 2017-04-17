package com.example.speedstream.theguardian;
import android.util.Log;

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

/**
 * Created by speedstream on 17/04/17.
 */

public class QueryUtils {
    private QueryUtils(){}

    public static ArrayList<News> extractNews(String url){
        ArrayList<News> newses = new ArrayList<>();
        try{
            JSONObject jsonObj = new JSONObject(makeHttpRequest(createUrl(url)));
            JSONObject root= jsonObj.getJSONObject("response");
            JSONArray results = root.getJSONArray("results");
            for(int i = 0; i < results.length(); i++){
                JSONObject oneNews = results.getJSONObject(i);
                String sectionId = oneNews.getString("sectionId");
                String sectionName = oneNews.getString("sectionName");
                String publicationDate = oneNews.getString("webPublicationDate");
                String webTitle = oneNews.getString("webTitle");
                String webURL = oneNews.getString("webUrl");
                newses.add(new News(sectionId, sectionName, publicationDate, webTitle, webURL));
            }
        }
        catch (final JSONException e){
            Log.e("JSON_Response", "JSON parsing error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newses;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
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
    private static String readFromStream(InputStream inputStream) throws IOException {
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
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            return null;
        }
        return url;
    }
}