package com.example.a60010743.bakingpro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;
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
    private String mRecepieItem;
    private boolean favBtnPressed = false;
    private RecepieStepsFragments mRecepieStepsFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_steps);
        mRecepieStepsFragments = new RecepieStepsFragments();
        Intent intent = getIntent();

        mRecepieItem = intent.getStringExtra("recepieItem");
        mRecepieStepsFragments.setRecepieItem(mRecepieItem);

        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
        mRecepieStepsFragments.setRecepieViewModel(mRecepieViewModel);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recepie_steps_fragment_container, mRecepieStepsFragments)
                .commit();
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




}
