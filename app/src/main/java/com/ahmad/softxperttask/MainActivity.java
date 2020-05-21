package com.ahmad.softxperttask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ahmad.softxperttask.model.Car;
import com.ahmad.softxperttask.utils.ApiCall;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL buildUrl = ApiCall.buildUrl(1);
        new FetchDataTask().execute(buildUrl);
    }

    public class FetchDataTask extends AsyncTask<URL, Void, String>{
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null && !s.equals("")) {
                //TODO parse the data and add it recycler view
                ArrayList<Car> fetchedCars = new ArrayList(ApiCall.extractCarsFromJson(s));
                Log.e(TAG, "fetchedCars:"+ s);
                Log.e(TAG, "onPostExecute: fetchedCards" + fetchedCars );
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "An Error occured while fetching data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
