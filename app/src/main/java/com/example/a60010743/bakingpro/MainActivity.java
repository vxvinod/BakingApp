package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private final static String TAG_NAME = "MainActivity";
    private static RecepieViewModel mRecepieViewModel;
    private List<String> mRecepieNames = new ArrayList<String>();

    @BindView(R.id.baking_grid_view) GridView mRecepieGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final boolean twoPane;
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        final RecepieAdapter adapter = new RecepieAdapter(this, null);
        mRecepieGridView.setAdapter(adapter);

        // Fetch Data from URL - NEED TO HANDLE ??
        new fetchData().execute();

        // Handle view of Tab and mobile phone
        twoPane = (findViewById(R.id.tab_layout_container) != null) ? true : false;



        getAllRecepies(adapter);

        // Handle Recepie Item Click to start Recepie Step Activity
        mRecepieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(MainActivity.this, RecepieStepsActivity.class);
                intent.putExtra(getString(R.string.twoPane), twoPane);
                intent.putExtra(getString(R.string.recepieItem), mRecepieNames.get(position).toString());
                startActivity(intent);
            }
        });
    }

    // Fetch Recepie Item from Database and Display in MainActivity
    private void getAllRecepies(final RecepieAdapter adapter) {
        mRecepieViewModel.getmAllRecepieItems().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                mRecepieNames = strings;
                adapter.setRecepieItems(strings);
            }
        });
    }

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
                                                    res.getRecepieIng(), res.getRecepieSteps());
                Log.d("CHECKTT", recepieDetails.getRecepieItem());
                mRecepieViewModel.insert(recepieDetails);
            }
            super.onPostExecute(s);
        }
    }
}
