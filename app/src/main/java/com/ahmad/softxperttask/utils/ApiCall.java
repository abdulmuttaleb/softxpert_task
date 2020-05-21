package com.ahmad.softxperttask.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

}