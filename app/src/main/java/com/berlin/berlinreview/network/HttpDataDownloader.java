package com.berlin.berlinreview.network;

import android.content.Context;
import android.util.Log;

import com.berlin.berlinreview.database.DatabaseHandler;
import com.berlin.berlinreview.database.DbReviewObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prime on 5/20/17.
 */

public class HttpDataDownloader {

    private static String TAG = HttpDataDownloader.class.getCanonicalName();
    private Context context;

    HttpDataDownloader(Context context){
        this.context = context;
    }


    public int getHttpData(String urlString) {

        Log.d(TAG, "Hello from http " + urlString);
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        int result = 0;
        try {

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                Log.d(TAG,"Status OK");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);
                Log.d(TAG,"Response from service: "+result);
                parseResult(response);
                result = 1; // Successful
            }
            Log.d(TAG, statusCode + "stat");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result; //"Failed to fetch data!";
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }


    private void parseResult(String result) {
        try {

            Log.d(TAG,"Parse Result");
            JSONObject response = new JSONObject(result);
            JSONArray data = response.getJSONArray("data");
            Log.d(TAG,response.toString()+result);



            //Add to DB
            if (data.length() > 0)
                for (int i = 0; i < data.length(); i++) {
                    JSONObject oneReviewObj = data.getJSONObject(i);
                    DbReviewObject reviewObject = new DbReviewObject();
                    reviewObject.review_id = (oneReviewObj.optString("review_id"));
                    reviewObject.rating = (oneReviewObj.optString("rating"));
                    reviewObject.title = (oneReviewObj.optString("title"));
                    reviewObject.message = (oneReviewObj.optString("message"));
                    reviewObject.author = (oneReviewObj.optString("author"));
                    reviewObject.foreignLanguage = (oneReviewObj.optString("foreignLanguage"));
                    reviewObject.date = (oneReviewObj.optString("date"));
                    reviewObject.date_unformatted=(oneReviewObj.optString("date_unformatted"));
                    reviewObject.languageCode = (oneReviewObj.optString("languageCode"));
                    reviewObject.traveler_type = (oneReviewObj.optString("traveler_type"));
                    reviewObject.reviewerName = (oneReviewObj.optString("reviewerName"));
                    reviewObject.reviewerCountry=(oneReviewObj.optString("reviewerCountry"));


                    //INSERT INTO DB
                    DatabaseHandler.getInstance(context).putDbReviewObject(reviewObject);

                }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
