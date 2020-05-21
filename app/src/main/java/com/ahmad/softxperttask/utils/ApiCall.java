package com.ahmad.softxperttask.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ahmad.softxperttask.model.Car;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiCall {

    private static String TAG = "ApiCall";
    public static String API_URL = "http://demo1286023.mockable.io/api/v1/cars";

    public static URL buildUrl(int pageNumber) {
        Uri builtUri = Uri.parse(API_URL)
                .buildUpon()
                .appendQueryParameter("page", String.valueOf(pageNumber))
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<Car> extractCarsFromJson(String carsJson){
        if(TextUtils.isEmpty(carsJson))
            return null;
        ArrayList<Car> extractedCarsList = new ArrayList<>();

        try{
            JSONObject mJSONObject = new JSONObject(carsJson);
            JSONArray mCars = mJSONObject.getJSONArray("data");
            for(int i = 0; i < mCars.length();i++){
                JSONObject carJson = mCars.getJSONObject(i);
                JsonParser jsonParser = new JsonParser();
                JsonObject gsonObject = (JsonObject)jsonParser.parse(carJson.toString());
                Gson mapper = new Gson();
                Car car = mapper.fromJson(gsonObject, Car.class);
                extractedCarsList.add(car);
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the Place JSON results", e);
        }
        return extractedCarsList;
    }

}