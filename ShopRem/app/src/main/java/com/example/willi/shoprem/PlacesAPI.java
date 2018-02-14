package com.example.willi.shoprem;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by willi on 2/13/2018.
 */

public class PlacesAPI {

    private static final String TAG = PlacesAPI.class.getSimpleName();
    private static final String PLACES_API_BASE_URI = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyA5CCsm_Fbp6BEzi4XXUzDEcdoKCfpZF9E";

    public ArrayList<String> autoComplete(String input) throws MalformedURLException {

        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonBuilder = new StringBuilder();

        try {

            StringBuilder sb = new StringBuilder(PLACES_API_BASE_URI + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&types=(cities)");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            Log.e(TAG, sb.toString()+"  URI");

            URL url = new URL(sb.toString());

            conn = (HttpURLConnection) url.openConnection();

            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonBuilder.append(buff, 0, read);
            }
            Log.e(TAG, "Response  "+jsonBuilder);


        } catch (IOException e) {
            Log.e(TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try{

            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("predictions"));

            resultList = new ArrayList<String>(jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                resultList.add(jsonArray.getJSONObject(i).getString("description"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
