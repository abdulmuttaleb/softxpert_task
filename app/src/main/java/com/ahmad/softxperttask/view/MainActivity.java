package com.ahmad.softxperttask.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ahmad.softxperttask.R;
import com.ahmad.softxperttask.adapter.CarsRecyclerAdapter;
import com.ahmad.softxperttask.model.Car;
import com.ahmad.softxperttask.utils.ApiCall;
import com.ahmad.softxperttask.utils.PaginationScrollListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    private RecyclerView carsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CarsRecyclerAdapter carsAdapter = new CarsRecyclerAdapter();
    private int currentPage = 1;
    private int lastPage = 4;
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivityUI();
        fetchData();
    }

    private void initActivityUI(){
        carsRecyclerView = findViewById(R.id.rv_cars);
        mSwipeRefreshLayout = findViewById(R.id.srl_main);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        carsRecyclerView.setLayoutManager(linearLayoutManager);
        carsRecyclerView.setAdapter(carsAdapter);

        //adding scroll listener
        carsRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public boolean isLastPage() {
                return currentPage == lastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public void loadMoreItems() {
                Log.e(TAG, "loadMoreItems: page"+currentPage);
                isLoading = true;
                currentPage++;
                fetchData();
            }
        });
    }

    private void fetchData(){
        URL buildUrl = ApiCall.buildUrl(currentPage);
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
                ArrayList<Car> fetchedCars = new ArrayList(ApiCall.extractCarsFromJson(s));
                Log.e(TAG, "fetchedCars:"+ s);
                Log.e(TAG, "onPostExecute: fetchedCards" + fetchedCars );
                for (Car car: fetchedCars) {
                    carsAdapter.addToCars(car);
                }
                isLoading = false;
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

    @Override
    public void onRefresh() {
        carsAdapter.clearCars();
        currentPage = 1;
        fetchData();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
