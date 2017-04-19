package com.example.speedstream.theguardian;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by speedstream on 18/04/17.
 */

public class ExtractFromJson {
    /**
     * Class to extract news. Is used to retrive sections and news form a section.
     * Key "whichFeed" specifies what screen is going to ve retrived: main (all sections) or a section (news from a section).
     * */
    public ArrayList<News> extractFeature(String whichFeed, String newsJson){
        try {
            Log.e("extractFeature", "Url -> " + newsJson);
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject responseObj = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = responseObj.getJSONArray("results");

            ArrayList<News> sectionsList = new ArrayList<>();

            if(whichFeed=="main") {
                /**Shows all sections*/
                Log.i("ExtractNews", "-> main");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject section = resultsArray.getJSONObject(i);
                    //String sectionName = section.getString("webTitle");
                    //String apiUrl = section.getString("apiUrl");
                    sectionsList.add(new News(section.getString("webTitle"), section.getString("apiUrl")));
                }
            }else if (whichFeed=="section"){
                /**Shows news from a section*/
                Log.i("ExtractNews", "-> section");
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
            Log.e("JSON_ERROR", "Problem parsing the JSON results", e);
        }
        return null;
    }
}
