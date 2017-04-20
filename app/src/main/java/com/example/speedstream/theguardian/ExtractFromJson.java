package com.example.speedstream.theguardian;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
                    sectionsList.add(new News(section.getString("webTitle"), section.getString("apiUrl")));
                }
            }else if (whichFeed=="section"){
                /**Shows news from a section*/
                Log.i("ExtractNews", "-> section");
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject section = resultsArray.getJSONObject(i);

                    String sectionName = section.getString("sectionName");
                    String date = getCustomDate(section.getString(("webPublicationDate")));
                    String title = section.getString("webTitle");
                    String webUrl = section.getString("webUrl");
                    sectionsList.add(new News(sectionName, title, date,webUrl));
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

    private String getCustomDate(String webDate){
        /**
         * The webPublicationDate came as the following format -> 2017-04-18T15:27:59Z
         * So, in this function, we use split(key) to divide the original string at key position.
         * "date" divides the original string into "2017-04-18" and "15:27:59Z". We only focus on date[0], "2017-04-18"
         * Next, split the string date[0] at "-" to obtain
         *      year    (date[0])
         *      month   (date[1])
         *      day     (date[2])
         * and reorganize them in finalDate as "day/month/year".
         * Finally, return finalDate as the customDate
         * */
        String[] date = webDate.split("T");
        String[] dateParts = date[0].split("-");
        String finalDate = dateParts[2]+"/"+dateParts[1]+"/"+dateParts[0];
        return finalDate;
    }
}
