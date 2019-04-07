package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.example.a60010743.bakingpro.Adapters.RecepieAdapter;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.Utilities.NetworkUtils;
import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private final static String TAG_NAME = "MainActivity";
    private static RecepieViewModel mRecepieViewModel;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView recepieGridView = (GridView) findViewById(R.id.baking_grid_view);
        recepieGridView.setAdapter(new RecepieAdapter(this, recepieNames));

        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        // Build Url

        // Fetch Data from URL
        new fetchData().execute();
        // Store fetched data in DB

        mRecepieViewModel.getAllRecepies().observe(this, new Observer<List<RecepieDetails>>() {
            @Override
            public void onChanged(@Nullable List<RecepieDetails> recepieDetails) {
                //recepieGridView.setAdapter();
            }
        });


        //Intent intent = new Intent(this, RecepieStepsActivity.class);
       // startActivity(intent);
   //     BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
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
            Log.d(TAG_NAME, "Data----" + recepieCollections);
            for(RecepieDetails res:recepieCollections) {
                String recItem = res.getRecepieItem().toString();
                String resIng = res.getRecepieIng().toString();
                String resSteps = res.getRecepieSteps().toString();
                RecepieDetails recepieDetails = new RecepieDetails(recItem, resIng, resSteps);
                mRecepieViewModel.insert(recepieDetails);
            }
            super.onPostExecute(s);
        }
    }




    public String[] recepieNames = {
            "Recepie-1", "Recepie-2", "Recepie-3",
            "Recepie-4", "Recepie-5", "Recepie-6",
            "Recepie-7", "Recepie-8", "Recepie-9",
            "Recepie-10", "Recepie-11", "Recepie-12"
    };

}
