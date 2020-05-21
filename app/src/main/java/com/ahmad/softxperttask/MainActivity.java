package com.ahmad.softxperttask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.ahmad.softxperttask.utils.ApiCall;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class fetchDataTask extends AsyncTask<URL, Void, String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String callResult = null;

            try{
                callResult = ApiCall.getResponseFromHttpUrl(searchURL);
            }catch (IOException e){
                e.printStackTrace();
            }

            return callResult;
        }
    }
}
