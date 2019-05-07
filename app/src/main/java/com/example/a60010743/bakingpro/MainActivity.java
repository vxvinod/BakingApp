package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.a60010743.bakingpro.Adapters.RecepieAdapter;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.Utilities.NetworkUtils;
import com.example.a60010743.bakingpro.model.RecepieDBApi;
import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.converter.gson.GsonConverterFactory.create;

public class MainActivity extends AppCompatActivity {


    private final static String TAG_NAME = "MainActivity";
    private static RecepieViewModel mRecepieViewModel;
    private List<String> mRecepieNames = new ArrayList<String>();
    private static final String RECEPIEAPIURL = "https://d17h27t6h515a5.cloudfront.net/";
    @BindView(R.id.recepieItemRecyclerview) RecyclerView mRecepieRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final boolean twoPane;
        twoPane = (findViewById(R.id.tab_layout_container) != null) ? true : false;
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        LinearLayoutManager itemLayoutManager = new LinearLayoutManager(this);
        mRecepieRV.setLayoutManager(itemLayoutManager);
        final RecepieAdapter adapter = new RecepieAdapter(this, null, twoPane);
        mRecepieRV.setAdapter(adapter);
        mRecepieRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        // Fetch Data from URL - NEED TO HANDLE ??
        new fetchData().execute();
        //fetchData();

        // Handle view of Tab and mobile phone




        getAllRecepies(adapter);


    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(RECEPIEAPIURL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        final RecepieDBApi recepieDBApi = retrofit.create(RecepieDBApi.class);
        Call<List<RecepieDetails>> call = recepieDBApi.getRecepieDetails();
        call.enqueue(new Callback<List<RecepieDetails>>() {
            @Override
            public void onResponse(Call<List<RecepieDetails>> call, Response<List<RecepieDetails>> response) {
                if(!response.isSuccessful()){
                    Log.d("RESPONSE NULL", String.valueOf(response.code()));
                    return;
                }
                Log.d(TAG_NAME, "Reposnse Success"+ response.body());
                List<RecepieDetails> recepieDetails = response.body();
                Log.d(TAG_NAME, "Reposnse Success"+ response.body());
                for(RecepieDetails rec: recepieDetails) {
                    mRecepieViewModel.insert(rec);
                }
            }

            @Override
            public void onFailure(Call<List<RecepieDetails>> call, Throwable t) {
                Log.d(TAG_NAME, "Reposnse Failed"+ t.getMessage());
            }
        });
    }

    // Fetch Recepie Item from Database and Display in MainActivity
    private void getAllRecepies(final RecepieAdapter adapter) {
        mRecepieViewModel.getAllRecepies().observe(this, new Observer<List<RecepieDetails>>() {
            @Override
            public void onChanged(@Nullable List<RecepieDetails> recepieDetails) {
               // mRecepieNames = strings;
                adapter.setRecepieItems(recepieDetails);
            }
        });
    }

//    private void getAllRecepies(final RecepieAdapter adapter) {
//        mRecepieViewModel.getmAllRecepieItems().observe(this, new Observer<List<String>>() {
//            @Override
//            public void onChanged(@Nullable List<String> strings) {
//                mRecepieNames = strings;
//                adapter.setRecepieItems(strings);
//            }
//        });
//    }



    // Async task to fetch details from API
    public static class fetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Build Url
            URL recepieDataUrl = NetworkUtils.buildUrl();
            String responseData = null;

            // Fetch Data from URL
            try {
                responseData = NetworkUtils.fetchData(recepieDataUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseData;
        }

        @Override
        protected void onPostExecute(String s) {
            List<RecepieDetails> recepieCollections = new ArrayList<>();
            try {
                recepieCollections = JsonParseUtils.parseRecepieData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(RecepieDetails res:recepieCollections) {
                RecepieDetails recepieDetails = new RecepieDetails(res.getRecepieItem(),
                                                    res.getRecepieIng(), res.getRecepieSteps(),
                                                    res.getServings(), res.getImage());
                Log.d("CHECKTT", recepieDetails.getRecepieItem());
                mRecepieViewModel.insert(recepieDetails);
            }
            super.onPostExecute(s);
        }
    }
}
