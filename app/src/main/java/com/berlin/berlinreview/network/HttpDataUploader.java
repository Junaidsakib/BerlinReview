package com.berlin.berlinreview.network;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by prime on 5/20/17.
 */

public class HttpDataUploader {

    private static String TAG = HttpDataUploader.class.getCanonicalName();
    private String payload;


    public int sendReview(String urlString, String payload){

        Log.d(TAG, "Hello from http " + urlString + payload);
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        Integer result = 0;
        try {

            StringBuffer response;
            URL url = new URL(urlString);
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000);
            urlConnection.setConnectTimeout(30000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(payload);
            writer.close();
            out.close();
            int statusCode = urlConnection.getResponseCode();

            System.out.println(String.valueOf(statusCode));
            if(statusCode == 200) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                result =1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return result; //"Failed to fetch data!";


    }


}