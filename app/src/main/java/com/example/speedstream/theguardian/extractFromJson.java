package com.example.speedstream.theguardian;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by speedstream on 18/04/17.
 */

public class extractFromJson {
    //public static final String SECTIONS_URL = "http://content.guardianapis.com/sections?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    private static String API_KEY = "?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    public ArrayList<News> extractFeature(String whichFeed, String newsJson){
        newsJson = newsJson + API_KEY;
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject responseObj = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = responseObj.getJSONArray("results");

            ArrayList<News> sectionsList = new ArrayList<>();

            if(whichFeed=="main") {
                System.out.println("extractFromJSON -> main");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject section = resultsArray.getJSONObject(i);
                    //String sectionName = section.getString("webTitle");
                    //String apiUrl = section.getString("apiUrl");
                    sectionsList.add(new News(section.getString("webTitle"), section.getString("apiUrl")));
                }
            }else if (whichFeed=="section"){
                System.out.println("extractFromJSON -> section");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject section = resultsArray.getJSONObject(i);

                    String sectionName = section.getString("sectionName");
                    String publicationDate = section.getString("webPublicationDate");
                    String title = section.getString("webTitle");
                    String webUrl = section.getString("webUrl");
                    sectionsList.add(new News(sectionName, title, publicationDate,webUrl));
                }
            }
            else{
                Log.e("JSON_ERROR", "extractFromJSON -> Error");
            }
            return sectionsList;
        } catch (JSONException e) {
            Log.e("JSON_ERROR", "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }
}
