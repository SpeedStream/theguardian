package com.example.speedstream.theguardian;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by speedstream on 14/04/17.
 */
/*
public final class extractNews {

    public static ArrayList<News> getNewsFrom(String url){
        ArrayList<News> news = new ArrayList<>();
        HttpHandler urlHandler = new HttpHandler();
        String jsonString = urlHandler.makeServiceCall(url);
        Log.e("JSON_Response", "Tenemos respuesta de la URL: "+ jsonString);
        if(jsonString != null) {
            try{
                JSONObject jsonObj = new JSONObject(jsonString);
                JSONObject root= jsonObj.getJSONObject("response");
                JSONArray results = root.getJSONArray("results");
                for(int i = 0; i < results.length(); i++){
                    JSONObject oneNews = results.getJSONObject(i);
                    String sectionId = oneNews.getString("sectionId");
                    String sectionName = oneNews.getString("sectionName");
                    String publicationDate = oneNews.getString("webPublicationDate");
                    String webTitle = oneNews.getString("webTitle");
                    String webURL = oneNews.getString("webUrl");
                    news.add(new News(sectionId, sectionName, publicationDate, webTitle, webURL));
                }
            }
            catch (final JSONException e){
                Log.e("JSON_Response", "JSON parsing error: " + e.getMessage());
            }
        }
        else{
            Log.e("JSON_NULL", "JSONString null");
        }
        return news;
    }
}
*/