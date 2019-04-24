package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a60010743.bakingpro.Adapters.RecepieIngAdapter;
import com.example.a60010743.bakingpro.Adapters.RecepieStepsAdapter;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RecepieStepsActivity extends AppCompatActivity {

    private final String TAG = "RecepieStepsActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mStepsLayoutManager;
    private RecepieViewModel mRecepieViewModel;

    private RecyclerView mRecIngView;
    private RecyclerView.Adapter mIngAdapter;
    private RecyclerView.LayoutManager mIngLayoutManager;
    private List<RecepieStepDetails> mRecepieStepDetails;
    private Button favButton;
    private boolean favBtnPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_steps);
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        final RecyclerView resIngRecyclerview = (RecyclerView) findViewById(R.id.IngRecyclerView);
        final RecyclerView resStpRecyclerView = (RecyclerView) findViewById(R.id.recepie_steps_recycler_view);
        favButton = (Button) findViewById(R.id.fav_button);
       // resStpRecyclerView.setHasFixedSize(true);



        Intent intent = getIntent();
        final String recepieItem = intent.getStringExtra("recepieItem");
        Log.d("RecepieStepsActivity", "recepieItem"+recepieItem);

        mStepsLayoutManager = new LinearLayoutManager(this);
        resStpRecyclerView.setLayoutManager(mStepsLayoutManager);

        mIngLayoutManager = new LinearLayoutManager(this);
        resIngRecyclerview.setLayoutManager(mIngLayoutManager);

        mRecepieViewModel.getIngredients(recepieItem).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                List<RecepieIngredients> recepieIngredients;
                try {
                    recepieIngredients = JsonParseUtils.parseIngData(s);
                    mIngAdapter = new RecepieIngAdapter(RecepieStepsActivity.this,
                                                    recepieIngredients);
                   // List<String> ingData = convertToStringList(recepieIngredients);

                    //displayIngredients();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resIngRecyclerview.setAdapter(mIngAdapter);
            }
        });

        mRecepieViewModel.getRecepieSteps(recepieItem).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    mRecepieStepDetails = JsonParseUtils.parseRecSteps(s);
                    mAdapter = new RecepieStepsAdapter(RecepieStepsActivity.this, mRecepieStepDetails,
                                                        recepieItem);
                    resStpRecyclerView.setAdapter(mAdapter);
                    //displayIngredients();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBtnPressed == false) {
                    mRecepieViewModel.updateFavouriteRecItem(recepieItem, true);
                    Log.d(TAG, recepieItem + true + "Favourite data written to DB");
                    favButton.setText("Marked Fav");
                    favButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    favBtnPressed = true;
                } else {
                    mRecepieViewModel.updateFavouriteRecItem(recepieItem, false);
                    Log.d(TAG, recepieItem + false + "UnFavourite data written to DB");
                    favButton.setText("Mark as Fav");
                    favButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    favBtnPressed = false;
                }
            }
        });



    }

//    public String displayIngredients() {
//        String ingDisplay = "";
//        for(RecepieIngredients rec: mRecepieIngredients) {
//            ingDisplay = ingDisplay + rec.getIngredients()+": "+rec.getQuantity()+" "+rec.getMeasure()+"\n";
////            Log.d(TAG, "Ing"+ rec.getIngredients());
////            Log.d(TAG, "Quantity"+ rec.getQuantity());
////            Log.d(TAG, "Measure"+ rec.getMeasure());
//        }
//        Log.d(TAG, "Final String "+ ingDisplay);
//        return ingDisplay;
//    }

    public List<String> convertToStringList(List<RecepieIngredients> recepieIngredients) {
        List<String> ingData = new ArrayList<>();
        for(RecepieIngredients rec: recepieIngredients) {
            String ingredient = rec.getIngredients()+":  "+rec.getQuantity()+" "+rec.getMeasure();
            ingData.add(ingredient);
//            Log.d(TAG, "Ing"+ rec.getIngredients());
//            Log.d(TAG, "Quantity"+ rec.getQuantity());
//            Log.d(TAG, "Measure"+ rec.getMeasure());
        }

        return ingData;

    }
    public String[] recepieSteps = {
            "Recepie-1", "Recepie-2", "Recepie-3",
            "Recepie-4", "Recepie-5", "Recepie-6",
            "Recepie-7", "Recepie-8", "Recepie-9",
            "Recepie-10", "Recepie-11", "Recepie-12"
    };

}
