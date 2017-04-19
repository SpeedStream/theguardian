package com.example.speedstream.theguardian;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.example.speedstream.theguardian.MainActivity.LOG_TAG;

/**
 * Created by speedstream on 14/04/17.
 */

public class SomeConnections {
    private static final String TAG = SomeConnections.class.getSimpleName();


    /**
    *   Convert the {@link InputStream} into a String which contains the
    *   whole JSON response from the server.
    * */
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
     * Make an HTTP request to the given URL and return a String as the response.
     */

    public String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
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
            Log.d(LOG_TAG, "Wut?");
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
     * Returns new URL object from the given string URL.
     * */
    public URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("createURL", "Error with creating URL", exception);
            return null;
        }
        return url;
    }
}
